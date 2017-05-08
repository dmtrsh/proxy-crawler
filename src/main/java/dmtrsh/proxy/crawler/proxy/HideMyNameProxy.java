package dmtrsh.proxy.crawler.proxy;

public class HideMyNameProxy {
    private String host;
    private int port;
    private String country;
    private int speed;
    private String type;
    private String anonymity;
    private int lastCheck;

    public HideMyNameProxy(String host, int port, String country, int speed) {
        this.host = host;
        this.port = port;
        this.country = country;
        this.speed = speed;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAnonymity() {
        return anonymity;
    }

    public void setAnonymity(String anonymity) {
        this.anonymity = anonymity;
    }

    public int getLastCheck() {
        return lastCheck;
    }

    public void setLastCheck(int lastCheck) {
        this.lastCheck = lastCheck;
    }

    @Override
    public String toString() {
        return "Proxy: " + host + ":" + port + " from " + country + ", speed: " + speed + ".";
    }
}
