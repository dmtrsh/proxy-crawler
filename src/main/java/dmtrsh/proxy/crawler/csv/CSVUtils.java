package dmtrsh.proxy.crawler.csv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class CSVUtils {
    private final static Logger logger = LoggerFactory.getLogger(CSVUtils.class);
    private static final char DEFAULT_SEPARATOR = ',';

    public static void writeLine(Writer w, List<String> values) {
        writeLine(w, values, DEFAULT_SEPARATOR, ' ');
    }

    public static void writeLine(Writer w, List<String> values, char separators) {
        writeLine(w, values, separators, ' ');
    }

    private static String followCsvFormat(String value) {
        String result = value;
        if (result.contains("\"")) {
            result = result.replace("\"", "\"\"");
        }
        return result;
    }

    public static void writeLine(Writer w, List<String> values, char separators, char customQuote)  {
        boolean first = true;
        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }
        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            if (!first) {
                sb.append(separators);
            }
            if (customQuote == ' ') {
                sb.append(followCsvFormat(value));
            } else {
                sb.append(customQuote).append(followCsvFormat(value)).append(customQuote);
            }
            first = false;
        }
        sb.append("\n");
        try {
            w.append(sb.toString());
        } catch (IOException e) {
           logger.error(e.getMessage(), e);
        }
    }

}