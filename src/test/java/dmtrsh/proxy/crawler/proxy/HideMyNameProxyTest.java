package dmtrsh.proxy.crawler.proxy;

import org.junit.Assert;
import org.junit.Test;

public class HideMyNameProxyTest {

    @Test
    public void toStringTest(){
        String host = "8.8.8.8";
        int port = 8080;
        String country = "United States";
        int speed = 100;
        String expected = "Proxy: 8.8.8.8:8080 from United States, speed: 100.";
        HideMyNameProxy hideMyNameProxy = new HideMyNameProxy(host, port, country, speed);
        Assert.assertEquals(expected, hideMyNameProxy.toString());
    }
}
