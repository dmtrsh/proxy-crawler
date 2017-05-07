package dmtrsh.proxy.crawler.csv;

import dmtrsh.proxy.crawler.proxy.ProxyHandler;
import dmtrsh.proxy.crawler.proxy.HideMyNameProxy;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CSVLogger {

    public void logProxy(ProxyHandler proxyHandler) throws IOException {
        List<HideMyNameProxy> hideMyNameProxies = proxyHandler.getHideMyNameProxies();
        String csvFile = "proxies.dmtrsh.proxy.crawler.csv";
        FileWriter writer = new FileWriter(csvFile);

        CSVUtils.writeLine(writer, Arrays.asList("Host", "Port", "Latency", "Last dmtrsh.proxy.crawler.check"));

        hideMyNameProxies.forEach(hideMyNameProxy -> {
            String host = hideMyNameProxy.getHost();
            String port = String.valueOf(hideMyNameProxy.getPort());
            String latency = String.valueOf(hideMyNameProxy.getSpeed());
            String lastCheck = String.valueOf(hideMyNameProxy.getLastCheck());
            CSVUtils.writeLine(writer, Arrays.asList(host, port, latency, lastCheck), ',', '"');
        });

        writer.flush();
        writer.close();
    }
}
