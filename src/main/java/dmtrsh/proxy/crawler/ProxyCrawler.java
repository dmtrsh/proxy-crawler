package dmtrsh.proxy.crawler;

import dmtrsh.proxy.crawler.check.ProxyChecker;
import dmtrsh.proxy.crawler.csv.CSVLogger;
import dmtrsh.proxy.crawler.parser.HideMyNameProxyParser;
import dmtrsh.proxy.crawler.parser.PageFofProxyParser;
import dmtrsh.proxy.crawler.proxy.HideMyNameProxy;
import dmtrsh.proxy.crawler.proxy.ProxyHandler;
import org.apache.log4j.chainsaw.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;


public class ProxyCrawler {
    private final static Logger logger = LoggerFactory.getLogger(ProxyCrawler.class);
    public static  Properties properties = loadProperties();

    public static void main(String[] args){
        if (args.length < 3)
            throw new IllegalArgumentException("Please specify parameters: " +
                    "String country, int maxTime, int maxCheckTime.");
        if (!checkCountryCode(args[0]))
            throw new IllegalArgumentException("Please specify parameters: " +
                    "Invalid first parameter. " + args[0] + "\n" +
                    "Available codes are: \n" + properties.getProperty("country.codes")
            );
        String country = args[0];
        int maxLatency;
        int maxCheckTime;
        try {
            maxLatency = Integer.parseInt(args[1]);
            maxCheckTime = Integer.parseInt(args[2]);
        } catch (NumberFormatException e){
            throw new IllegalArgumentException("2nd & 3rd argument should be numeral but was: '"
                    + args[1] + "' and '" + args[2] +"'.");
        }
        CSVLogger csvLogger = new CSVLogger();
        PageFofProxyParser parser = new HideMyNameProxyParser(country, maxLatency, maxCheckTime);
        List<HideMyNameProxy> hideMyNameProxies = parser.parse();
        ProxyHandler proxyHandler = new ProxyHandler();
        logger.info("Checking for aliveness:");
        List<HideMyNameProxy> aliveHideMyNameProxies = hideMyNameProxies
                .parallelStream()
                .filter(hideMyNameProxy -> new ProxyChecker().checkProxy(hideMyNameProxy).isAlive())
                .collect(Collectors.toList());
        logger.info("Amount of alive proxies: " + aliveHideMyNameProxies.size());
        proxyHandler.setHideMyNameProxies(aliveHideMyNameProxies);
        csvLogger.logProxy(proxyHandler);
        System.exit(0);
    }

    private static boolean checkCountryCode(String arg) {
        if (arg == null) return false;
        List<String> expectedCountryCodes = Arrays.asList(properties.getProperty("country.codes").split(","));
        List<String> actualCountryCodes =  Arrays.asList(arg.split("(?<=\\G.{2})"));
        return actualCountryCodes.stream().allMatch(expectedCountryCodes::contains);
    }

    private static Properties loadProperties(){
        Properties properties = new Properties();
        try(InputStream is = ProxyCrawler.class
                                .newInstance()
                                .getClass()
                                .getClassLoader()
                                .getResourceAsStream("application.properties")) {
            properties.load(is);
        } catch (IOException | InstantiationException | IllegalAccessException e) {
            logger.error("Cannot read application.properties file", e);
        }
        return properties;
    }
}


