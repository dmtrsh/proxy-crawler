package dmtrsh.proxy.crawler.check;

import dmtrsh.proxy.crawler.proxy.HideMyNameProxy;

public class CheckedHideMyNameProxy {
    private HideMyNameProxy hideMyNameProxy;
    private ProxyResponse proxyResponse;
    private boolean isAlive;

    public CheckedHideMyNameProxy(HideMyNameProxy hideMyNameProxy, ProxyResponse proxyResponse){
        this.hideMyNameProxy = hideMyNameProxy;
        this.proxyResponse = proxyResponse;
        setAlive();
    }

    public CheckedHideMyNameProxy(HideMyNameProxy hideMyNameProxy) {
        this.hideMyNameProxy = hideMyNameProxy;
    }

    public HideMyNameProxy getHideMyNameProxy() {
        return hideMyNameProxy;
    }

    public void setHideMyNameProxy(HideMyNameProxy hideMyNameProxy) {
        this.hideMyNameProxy = hideMyNameProxy;
    }

    public ProxyResponse getProxyResponse() {
        return proxyResponse;
    }

    public void setProxyResponse(ProxyResponse proxyResponse) {
        this.proxyResponse = proxyResponse;
        setAlive();
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive() {
        this.isAlive = proxyResponse.getResponseCode() == 200
                && proxyResponse.getResponseBody().equals(hideMyNameProxy.getHost());
    }

    @Override
    public String toString() {
        return hideMyNameProxy.toString() + (isAlive ? " Is alive." : ". Is not alive.");
    }
}
