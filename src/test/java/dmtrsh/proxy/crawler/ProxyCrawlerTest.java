package dmtrsh.proxy.crawler;

import org.junit.Test;

public class ProxyCrawlerTest {
    @Test(expected = IllegalArgumentException.class)
    public void main_BadArguments_2ndAnd3rd(){
        ProxyCrawler.main(new String[]{"UA", "", ""});
    }

    @Test(expected = IllegalArgumentException.class)
    public void main_BadArguments_1st(){
        ProxyCrawler.main(new String[]{"", "5000", "100000000"});
    }
    @Test(expected = IllegalArgumentException.class)
    public void main_BadArguments_null(){
        ProxyCrawler.main(new String[]{null, null, null});
    }

    @Test(expected = IllegalArgumentException.class)
    public void main_NotEnoughArguments(){
        ProxyCrawler.main(new String[]{"US", "3000"});
    }


}
