package pl.rsokolowski.decoder;

import pl.rsokolowski.support.HuffmanException;
import pl.rsokolowski.support.Support;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HuffmanDecoder {

    private final String codedString;
    private final Map<String, String> codingTable;
    private final String decodedString;

    public HuffmanDecoder(String codedString, Map<String, String> codingTable) {

        if (Support.nullOrEmpty(codedString)) {
            this.codedString = "";
            Support.logger.error("Nothing to be decoded... provided data to be decoding is empty :(");
            this.codingTable = new HashMap<>();
        } else {
            this.codingTable = reverseCodingTable(codingTable);
            this.codedString = codedString;
        }
        this.decodedString = decodeString();
        Support.logger.info("text was decoded by Huffman: " + "\"" + decodedString + "\"");
    }

    private Map<String, String> reverseCodingTable(Map<String, String> codingTable) {
        return codingTable.entrySet().stream().collect(Collectors.toMap(entry -> entry.getValue(), entry -> entry.getKey()));
    }

    public String decodeString() {
        if (codingTable.isEmpty()) return "";
        StringBuilder decoded = new StringBuilder();
        String substring;
        int counter = 0;
        for (int i = 1; i <= codedString.length(); i++) {
            substring = codedString.substring(counter, i);
            if (codingTable.containsKey(substring)) {
                decoded.append(codingTable.get(codedString.substring(counter, i)));
                counter = i;
            }
        }

        return decoded.toString();
    }

    public String getDecodedString() {
        return decodedString;
    }
}
