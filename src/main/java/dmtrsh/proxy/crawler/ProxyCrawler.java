package dmtrsh.proxy.crawler;

import dmtrsh.proxy.crawler.check.ProxyChecker;
import dmtrsh.proxy.crawler.csv.CSVLogger;
import dmtrsh.proxy.crawler.parser.HideMyNameProxyParser;
import dmtrsh.proxy.crawler.parser.PageFofProxyParser;
import dmtrsh.proxy.crawler.proxy.HideMyNameProxy;
import dmtrsh.proxy.crawler.proxy.ProxyHandler;


import java.io.*;
import java.util.List;
import java.util.stream.Collectors;


public class ProxyCrawler {
    public static void main(String[] args) throws IOException {
        if (args.length < 3)
            throw new IllegalArgumentException("Please specify parameters: " +
                    "String country, int maxTime, int maxCheckTime");
        String country = args[0];
        int maxLatency;
        int maxCheckTime;
        try {
            maxLatency = Integer.parseInt(args[1]);
            maxCheckTime = Integer.parseInt(args[2]);
        } catch (NumberFormatException e){
            throw new IllegalArgumentException("2nd & 3rd argument should be numeral but was: "
                    + args[1] + " and " + args[2]);
        }
        CSVLogger csvLogger = new CSVLogger();
        PageFofProxyParser parser = new HideMyNameProxyParser(country, maxLatency, maxCheckTime);
        List<HideMyNameProxy> hideMyNameProxies = parser.parse();
        ProxyHandler proxyHandler = new ProxyHandler();
        List<HideMyNameProxy> aliveHideMyNameProxies = hideMyNameProxies
                .parallelStream()
                .filter(hideMyNameProxy -> new ProxyChecker().checkProxyA(hideMyNameProxy).isAlive())
                .collect(Collectors.toList());
        proxyHandler.setHideMyNameProxies(aliveHideMyNameProxies);
        csvLogger.logProxy(proxyHandler);
        System.exit(0);
    }
}
