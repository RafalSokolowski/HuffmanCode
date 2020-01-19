package pl.rsokolowski.coder;

import pl.rsokolowski.support.HuffmanException;
import pl.rsokolowski.support.Support;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HuffmanCoder {

    private final String toBeCoded;
    private final Map<String, String> codingTable;
    private final String codedString;

    public HuffmanCoder(String toBeCoded) {
        if (Support.nullOrEmpty(toBeCoded)) {
            Support.logger.error("Nothing to be coded, provide some data for coding... NULL or empty string is not what the algorithm would like to work on:)");
            this.toBeCoded = "";
            this.codingTable = new HashMap<>();
            this.codedString = "";
        } else {
            this.toBeCoded = toBeCoded;
            this.codingTable = generateCodingTable();
            this.codedString = generateCodedString();
            Support.logger.info("text was coded by HuffmanCode: " + codedString );
        }
    }

    public Map<String, String> generateCodingTable() {
        Histogram histogram = new Histogram(toBeCoded);
        HuffmanTree huffmanTree = new HuffmanTree(histogram.getHistogram());
        if (huffmanTree.generateFinalCodingTable().isPresent()) {
            return huffmanTree.generateFinalCodingTable().get();
        } else {
            throw new HuffmanException("coding table is empty... thus the coding con not be proceed further (double check the input parameters, such as the string / file which is supposed to be coded)");
        }
    }

    public String generateCodedString() {
        String[] toBeCodedTab = toBeCoded.split("");
        return Arrays.stream(toBeCodedTab).map(s -> codingTable.get(s)).collect(Collectors.joining());
    }

    public Map<String, String> getCodingTable() {
        return new HashMap<>(codingTable);
//        return codingTable;
    }

    public String getCodedString() {
        return codedString;
    }
}
