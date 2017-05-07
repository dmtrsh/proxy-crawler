package dmtrsh.proxy.crawler.check;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ProxyServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dmtrsh.proxy.crawler.proxy.HideMyNameProxy;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ProxyChecker {
    private final static Logger logger = LoggerFactory.getLogger(ProxyChecker.class);
    private static AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    public CheckedHideMyNameProxy checkProxyA(HideMyNameProxy hideMyNameProxy){
        String host = hideMyNameProxy.getHost();
        int port = hideMyNameProxy.getPort();
        Future<com.ning.http.client.Response> f =
        asyncHttpClient
                .prepareGet("http://api.ipify.org/?format=txt")
                .setProxyServer(new ProxyServer(host, port))
                .setRequestTimeout(hideMyNameProxy.getSpeed() + 5000)
                .execute();

        CheckedHideMyNameProxy checkedHideMyNameProxy
                = new CheckedHideMyNameProxy(hideMyNameProxy, new Response(0, ""));
        try {
            checkedHideMyNameProxy = new CheckedHideMyNameProxy(hideMyNameProxy
                                        , new Response(f.get().getStatusCode(), f.get().getResponseBody()));
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
