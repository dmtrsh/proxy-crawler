package dmtrsh.proxy.crawler.check;

import dmtrsh.proxy.crawler.proxy.HideMyNameProxy;

public class CheckedHideMyNameProxy {
    private HideMyNameProxy hideMyNameProxy;
    private Response response;
    private boolean isAlive;

    public CheckedHideMyNameProxy(HideMyNameProxy hideMyNameProxy, Response response){
        this.hideMyNameProxy = hideMyNameProxy;
        this.response = response;
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

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
        setAlive();
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive() {
        this.isAlive = response.getResponseCode() == 200
                && response.getResponseBody().equals(hideMyNameProxy.getHost());
    }

    @Override
    public String toString() {
        return hideMyNameProxy.toString() + (isAlive ? " Is alive." : ". Is not alive.");
    }
}
