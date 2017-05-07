package dmtrsh.proxy.crawler.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dmtrsh.proxy.crawler.proxy.HideMyNameProxy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HideMyNameProxyParser implements PageFofProxyParser {
    private final static Logger logger = LoggerFactory.getLogger(HideMyNameProxyParser.class);
    private int maxCheckTime = Integer.MAX_VALUE;
    private String url = "https://hidemy.name/en/proxy-list/" +
            "?${country}${maxTime}&start=0&end=" + Integer.MAX_VALUE;

    public HideMyNameProxyParser(String country, int maxTime, int maxCheckTime) {
        this.url = url
                .replace("${country}", "country=" + country)
                .replace("${maxTime}", "&maxtime=" + maxTime);
        this.maxCheckTime = maxCheckTime;
    }

    public HideMyNameProxyParser() {
        this.url = url
                .replace("${country}", "")
                .replace("${maxTime}", "");
    }

    public HideMyNameProxyParser(int maxCheckTime) {
        this();
        this.maxCheckTime = maxCheckTime;
    }

    @Override
    public List<HideMyNameProxy> parse() {
        List<HideMyNameProxy> hideMyNameProxies = new ArrayList<>();
        try {
            Document d = Jsoup.connect(url).get();
            Elements rows = d.select("table.proxy__t > tbody > tr");
            rows.stream().map(row -> {
                String host = parseHost(row);
                int port = parsePort(row);
                String country = parseCountry(row);
                int speed = parseSpeed(row);
                int maxCheckTime = parseMaxCheckTime(row);
                HideMyNameProxy hideMyNameProxy = new HideMyNameProxy(host, port, country, speed);
                hideMyNameProxy.setLastCheck(maxCheckTime);
                return hideMyNameProxy;
            })
                    .filter(hideMyNameProxy -> hideMyNameProxy.getLastCheck() <= maxCheckTime)
                    .forEach(hideMyNameProxies::add);
            logger.info("Amount of available proxies: " + hideMyNameProxies.size());
        } catch (IOException e){
            logger.error(e.getMessage(), e);
        }

        return hideMyNameProxies;
    }

    private String parseHost(Element row){
        return row.select("td").get(0).text();
    }

    private int parsePort(Element row){
        String rawPort = row.select("td").get(1).text();
        return Integer.valueOf(rawPort);
    }

    private String parseCountry(Element row){
        String rawCountry = row.select("td > div").get(0).text();
        boolean isFirstALetter = Character.isLetter(rawCountry.charAt(0));
        return isFirstALetter
                ? rawCountry
                : rawCountry.substring(1);
    }

    private int parseSpeed(Element row){
        String rawSpeed = row.select("tr > td > div > div > p").get(0).text();
        return Integer.parseInt(rawSpeed.replace(" ms", ""));
    }

    private int parseMaxCheckTime(Element row){
        String rawMaxCheckTime = row.select("td").get(6).text();
        if (rawMaxCheckTime.contains(" seconds")){
            return Integer.parseInt(rawMaxCheckTime
                    .replace(" seconds", "")) * 1000;
        }else if (rawMaxCheckTime.contains(" minutes")){
            return Integer.parseInt(rawMaxCheckTime
                    .replace(" minutes", "")) * 60 * 1000;
        }else if (rawMaxCheckTime.contains(" h.")){
            String[] values = rawMaxCheckTime.split(" ");
            int hours = Integer.parseInt(values[0]) * 3600 * 1000;
            int minutes = Integer.parseInt(values[2]) * 60 * 1000;
            return hours + minutes;
        }
        throw new IllegalArgumentException("'Last dmtrsh.proxy.crawler.check' column has inappropriate value: " + rawMaxCheckTime);
    }
}
