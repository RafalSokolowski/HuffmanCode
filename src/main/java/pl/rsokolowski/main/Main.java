package pl.rsokolowski.main;

import com.sun.glass.ui.Application;
import pl.rsokolowski.coder.HuffmanCoder;
import pl.rsokolowski.coder.HuffmanTree;
import pl.rsokolowski.decoder.HuffmanDecoder;
import pl.rsokolowski.support.Support;

import javafx.scene.*;
import java.util.Map;


public class Main {

//    private final static String PATH_ANSI = "\\src\\main\\resources\\test_ANSI.txt";
//    private final static String PATH_UTF8 = "\\src\\main\\resources\\test_UTF8.txt";
//    private final static String PATH_UTF16 = "\\src\\main\\resources\\test_UTF16.txt";



    public static void main(String[] args) {
//        String toBeCoded = "This is my test of Huffman code";
        String toBeCoded = "Piotr is the best Java teacher ever ! :)";
//        String toBeCoded = "";
//        String toBeCoded = null;

        System.out.println();

        HuffmanCoder huffmanCoder = new HuffmanCoder(toBeCoded);
//        Support.printCodingTableNicely(huffmanCoder.getCodingTable());

        System.out.println();
        String codedString = huffmanCoder.getCodedString();
        Map<String,String> codingTable = huffmanCoder.getCodingTable();
        HuffmanDecoder huffmanDecoder = new HuffmanDecoder(codedString,codingTable);
        huffmanDecoder.decodeString();
//        System.out.println("decoded string: " + huffmanDecoder.getDecodedString());


    }


}
