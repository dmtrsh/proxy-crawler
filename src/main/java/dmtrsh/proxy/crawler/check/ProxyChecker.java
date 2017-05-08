package dmtrsh.proxy.crawler.check;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ProxyServer;

import com.ning.http.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dmtrsh.proxy.crawler.proxy.HideMyNameProxy;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static dmtrsh.proxy.crawler.ProxyCrawler.properties;

public class ProxyChecker {
    private final static Logger logger = LoggerFactory.getLogger(ProxyChecker.class);
    private static AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    private String checkerLink = properties.getProperty("check.ip.service");

    public CheckedHideMyNameProxy checkProxy(HideMyNameProxy hideMyNameProxy){
        String host = hideMyNameProxy.getHost();
        int port = hideMyNameProxy.getPort();
        Future<Response> f = asyncHttpClient
                .prepareGet(checkerLink)
                .setProxyServer(new ProxyServer(host, port))
                .setRequestTimeout(15000)
                .execute();

        CheckedHideMyNameProxy checkedHideMyNameProxy
                = new CheckedHideMyNameProxy(hideMyNameProxy, new ProxyResponse(0, ""));
        try {
            checkedHideMyNameProxy = new CheckedHideMyNameProxy(hideMyNameProxy
                                        , new ProxyResponse(f.get().getStatusCode(), f.get().getResponseBody()));
        } catch (InterruptedException | ExecutionException | IOException e) {
            logger.debug(e.getMessage(), e);
        }
        if(checkedHideMyNameProxy.isAlive())
            logger.info(checkedHideMyNameProxy.toString());
        if(!checkedHideMyNameProxy.isAlive())
            logger.debug(checkedHideMyNameProxy.toString());
        return checkedHideMyNameProxy;
    }

}
