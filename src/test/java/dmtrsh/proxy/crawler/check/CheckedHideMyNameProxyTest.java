package dmtrsh.proxy.crawler.check;

import dmtrsh.proxy.crawler.proxy.HideMyNameProxy;
import org.junit.Assert;
import org.junit.Test;

public class CheckedHideMyNameProxyTest {
    @Test
    public void setAlive_isAlive(){
        String host = "8.8.8.8";
        int port = 8080;
        String country = "United States";
        int speed = 100;
        HideMyNameProxy hideMyNameProxy = new HideMyNameProxy(host, port, country, speed);
        int responseCode = 200;
        String responseBody = "8.8.8.8";
        ProxyResponse proxyResponse = new ProxyResponse(responseCode, responseBody);
        CheckedHideMyNameProxy checkedHideMyNameProxy = new CheckedHideMyNameProxy(hideMyNameProxy);
        checkedHideMyNameProxy.setProxyResponse(proxyResponse);
        Assert.assertTrue(checkedHideMyNameProxy.isAlive());
    }

    @Test
    public void setAlive_isNotAlive_BadCode(){
        String host = "255.255.255.255";
        int port = 8080;
        String country = "United States";
        int speed = 200;
        HideMyNameProxy hideMyNameProxy = new HideMyNameProxy(host, port, country, speed);
        int responseCode = 201;
        String responseBody = "255.255.255.255";
        ProxyResponse proxyResponse = new ProxyResponse(responseCode, responseBody);
        CheckedHideMyNameProxy checkedHideMyNameProxy = new CheckedHideMyNameProxy(hideMyNameProxy);
        checkedHideMyNameProxy.setProxyResponse(proxyResponse);
        Assert.assertFalse(checkedHideMyNameProxy.isAlive());
    }

    @Test
    public void setAlive_isNotAlive_BadResponse(){
        String host = "255.255.255.255";
        int port = 8080;
        String country = "United States";
        int speed = 200;
        HideMyNameProxy hideMyNameProxy = new HideMyNameProxy(host, port, country, speed);
        int responseCode = 200;
        String responseBody = "127.0.0.1";
        ProxyResponse proxyResponse = new ProxyResponse(responseCode, responseBody);
        CheckedHideMyNameProxy checkedHideMyNameProxy = new CheckedHideMyNameProxy(hideMyNameProxy);
        checkedHideMyNameProxy.setProxyResponse(proxyResponse);
        Assert.assertFalse(checkedHideMyNameProxy.isAlive());
    }

    @Test
    public void constructor_isAlive(){
        String host = "8.8.8.8";
        int port = 8080;
        String country = "United States";
        int speed = 100;
        HideMyNameProxy hideMyNameProxy = new HideMyNameProxy(host, port, country, speed);
        int responseCode = 200;
        String responseBody = "8.8.8.8";
        ProxyResponse proxyResponse = new ProxyResponse(responseCode, responseBody);
        CheckedHideMyNameProxy checkedHideMyNameProxy = new CheckedHideMyNameProxy(hideMyNameProxy, proxyResponse);
        Assert.assertTrue(checkedHideMyNameProxy.isAlive());
    }

    @Test
    public void constructor_isNotAlive_BadCode(){
        String host = "255.255.255.255";
        int port = 8080;
        String country = "United States";
        int speed = 200;
        HideMyNameProxy hideMyNameProxy = new HideMyNameProxy(host, port, country, speed);
        int responseCode = 201;
        String responseBody = "255.255.255.255";
        ProxyResponse proxyResponse = new ProxyResponse(responseCode, responseBody);
        CheckedHideMyNameProxy checkedHideMyNameProxy = new CheckedHideMyNameProxy(hideMyNameProxy, proxyResponse);
        Assert.assertFalse(checkedHideMyNameProxy.isAlive());
    }

    @Test
    public void constructor_isNotAlive_BadResponse(){
        String host = "255.255.255.255";
        int port = 8080;
        String country = "United States";
        int speed = 200;
        HideMyNameProxy hideMyNameProxy = new HideMyNameProxy(host, port, country, speed);
        int responseCode = 200;
        String responseBody = "127.0.0.1";
        ProxyResponse proxyResponse = new ProxyResponse(responseCode, responseBody);
        CheckedHideMyNameProxy checkedHideMyNameProxy = new CheckedHideMyNameProxy(hideMyNameProxy, proxyResponse);
        Assert.assertFalse(checkedHideMyNameProxy.isAlive());
    }

    @Test
    public void toString_isAlive(){
        String host = "8.8.8.8";
        int port = 8080;
        String country = "United States";
        int speed = 100;
        String expected = "Proxy: 8.8.8.8:8080 from United States, speed: 100. Is alive.";
        HideMyNameProxy hideMyNameProxy = new HideMyNameProxy(host, port, country, speed);
        int responseCode = 200;
        String responseBody = "8.8.8.8";
        ProxyResponse proxyResponse = new ProxyResponse(responseCode, responseBody);
        CheckedHideMyNameProxy checkedHideMyNameProxy = new CheckedHideMyNameProxy(hideMyNameProxy);
        checkedHideMyNameProxy.setProxyResponse(proxyResponse);
        Assert.assertEquals(expected, checkedHideMyNameProxy.toString());
    }

    @Test
    public void toString_isNoAlive(){
        String host = "8.8.8.8";
        int port = 8080;
        String country = "United States";
        int speed = 100;
        String expected = "Proxy: 8.8.8.8:8080 from United States, speed: 100. Is not alive.";
        HideMyNameProxy hideMyNameProxy = new HideMyNameProxy(host, port, country, speed);
        int responseCode = 300;
        String responseBody = "8.8.8.8";
        ProxyResponse proxyResponse = new ProxyResponse(responseCode, responseBody);
        CheckedHideMyNameProxy checkedHideMyNameProxy = new CheckedHideMyNameProxy(hideMyNameProxy);
        checkedHideMyNameProxy.setProxyResponse(proxyResponse);
        Assert.assertEquals(expected, checkedHideMyNameProxy.toString());
    }

}
