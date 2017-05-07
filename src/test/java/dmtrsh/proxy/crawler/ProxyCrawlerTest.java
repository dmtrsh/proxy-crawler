package dmtrsh.proxy.crawler;

import org.junit.Test;

public class ProxyCrawlerTest {
    @Test(expected = IllegalArgumentException.class)
    public void main_BadArguments(){
        ProxyCrawler.main(new String[]{"UA", "", ""});
    }
}
