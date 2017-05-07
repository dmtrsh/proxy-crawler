package dmtrsh.proxy.crawler.csv;

import dmtrsh.proxy.crawler.proxy.ProxyHandler;
import dmtrsh.proxy.crawler.proxy.HideMyNameProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CSVLogger {
    private final static Logger logger = LoggerFactory.getLogger(CSVLogger.class);
    public void logProxy(ProxyHandler proxyHandler){
        List<HideMyNameProxy> hideMyNameProxies = proxyHandler.getHideMyNameProxies();
        String csvFile = "crawler.csv";
        FileWriter writer = createFileWriter(csvFile);
        CSVUtils.writeLine(writer, Arrays.asList("Host", "Port", "Latency", "Country"));

        hideMyNameProxies.forEach(hideMyNameProxy -> {
            String host = hideMyNameProxy.getHost();
            String port = String.valueOf(hideMyNameProxy.getPort());
            String latency = String.valueOf(hideMyNameProxy.getSpeed());
            String country = hideMyNameProxy.getCountry();
            CSVUtils.writeLine(writer, Arrays.asList(host, port, latency, country), ',', '"');
        });

        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("Unable to save: " + csvFile);
        }
    }

    private FileWriter createFileWriter(String csvFile){
        FileWriter writer;
        try {
            writer = new FileWriter(csvFile);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("Unable to create FileWriter for: " + csvFile);
        }
        return writer;
    }
}
