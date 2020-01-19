package pl.rsokolowski.support;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.rsokolowski.coder.Sign;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

public class Support {

    public static final Logger logger = LogManager.getLogger();

    public static Optional<String> convertFileToString (String filePath, Charset charset) { // !!!! file mast be saved in ANSI oor UTF-16 format, in UTF-8 I get strange sign at the very beginning
        File file = new File(filePath.trim());
        if (!file.exists()) {
            logger.error("file not found... double check the path");
            return Optional.empty();
        }

        StringBuilder stringBuilder = new StringBuilder();
        try {
            Scanner scanner = new Scanner(file, charset).useDelimiter("");
            String s;
            while (scanner.hasNext()) {
                s = scanner.next();
                stringBuilder.append(s);
            }
//            scanner.tokens().forEach(stringBuilder::append);
            scanner.close();
        } catch (IOException e) {
            logger.error("Reading error: " + e.getMessage());
        }

        return Optional.of(stringBuilder.toString());
    }

    public static Map<String, Integer> makeHistogram(String string) {
        Map<String, Integer> histogram = new HashMap<>();
        Arrays.stream(string.split("")).forEach(s -> {
            if (histogram.containsKey(s)) histogram.put(s, histogram.get(s) + 1);
            else histogram.put(s, 1);
        });
        return histogram;
    }

    public static Map<String, Integer> sortHistogram(Map<String, Integer> unsorted) {
        Map<String, Integer> sorted = new LinkedHashMap<>();
        unsorted.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(o -> sorted.put(o.getKey(), o.getValue()));
        return sorted;
    }

    public static List<Sign> createHistogramSigns(Map<String, Integer> sorted) {
        List<Sign> histogramSigns = new ArrayList<>();
        sorted.forEach((key, value) -> histogramSigns.add(new Sign(key, value)));
        return histogramSigns;
    }

    public static void printCodingTableNicely(Map<String, String> codingTable) {
//        codingTable.entrySet().forEach(entry -> System.out.println("'" + entry.getKey() + "' = " + entry.getValue()));
        codingTable.forEach((k, v) -> System.out.println("'" + k + "' = " + v));
    }

    public static boolean nullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }


    public static boolean isZeroOrOne(String zeroOrOne) {
        return ("1".equals(zeroOrOne) || "0".equals(zeroOrOne));
    }

}
