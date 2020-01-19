package pl.rsokolowski.coder;

import pl.rsokolowski.support.Support;

import java.util.*;

public class TreesFromHistogram {

    private final List<Sign> histogram;
    private final List<Tree> trees;

    public TreesFromHistogram(List<Sign> histogram) {
        this.histogram = histogram;
        this.trees = new ArrayList<>();
    }

    private void addInkToCodingTab(Map<String, String> treeCodingTable, String ink, String zeroOrOne) {
        if (!Support.isZeroOrOne(zeroOrOne)) {
            throw new IllegalArgumentException("Error from addToCodingTable: \"zeroOrOne\" argument needs to have value equal 0 or 1");
        }
        if (treeCodingTable.containsKey(ink)) {
            treeCodingTable.put(ink, zeroOrOne + treeCodingTable.get(ink));
        } else {
            treeCodingTable.put(ink, zeroOrOne);
        }
    }

    private int lastTwoOfHistogramAndSum(Map<String, String> treeCodingTable) {
        if (histogram.size() < 2) {
            throw new IllegalArgumentException("\"lastTwoOfHistogram\" used without checking minimum size (needs to be 2 at least)... invalid function's call"); // czy tu mogę korzystać z Optional<> i zwrócić Optional.empty(), czy raczej rzucić wyjątek (?) ... co lepiej / które prawidłowe / best practice ...
        }

        int lastIndex = histogram.size() - 1;
        Sign last = histogram.get(lastIndex);
        Sign penultimate = histogram.get(lastIndex - 1);

        int lastIndexesSum = last.getQuantity() + penultimate.getQuantity();

        addInkToCodingTab(treeCodingTable, last.getInk(), "1");
        addInkToCodingTab(treeCodingTable, penultimate.getInk(), "0");

        histogram.remove(lastIndex);
        histogram.remove(lastIndex - 1);
        return lastIndexesSum;
    }

    public Tree generateTree() {
        if (histogram.isEmpty()) {
            throw new IllegalArgumentException("Error from generateTree: histogram needs to be created before the tree generation");
        }

        Map<String, String> treeCodingTable = new LinkedHashMap<>();
        int treeSum;

        if (histogram.size() == 1) {                                                       // file or String z jednym znakiem kodowym
            addInkToCodingTab(treeCodingTable, histogram.get(0).getInk(), "0");
            treeSum = histogram.stream().mapToInt(s -> s.getQuantity()).sum();
            return new Tree(treeSum, treeCodingTable);                                                        // czy tak mogę, pracuje na polu klasy i ja też zwracam ?

        }

        treeSum = lastTwoOfHistogramAndSum(treeCodingTable);

        while (histogram.size() > 0) {

            int lastIndex = histogram.size() - 1;
            Sign last = histogram.get(lastIndex);

            if (treeSum <= last.getQuantity()) {
                treeSum += last.getQuantity();
//                codingTable.entrySet().stream().collect(Collectors.toMap(k -> k.getKey(), v -> "1" + v.getValue()));
//                codingTable.entrySet().stream().collect(Collectors.toMap(k -> k.getKey(), v -> "1" + v.getValue()));
//                codingTable.entrySet().stream().map(v -> "1" + v.getValue()).forEach(System.out::println);
//                codingTable.entrySet().stream().map(v -> "1" + v.getValue()).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,LinkedHashMap::new,p->p));
//                codingTable.entrySet().stream().map(v -> "1" + v.getValue()).collect(Collectors.toMap(LinkedHashMap::new, k -> k);
//                codingTable.entrySet().stream().forEach(e -> e.setValue("1" + e.getValue()));
                treeCodingTable.entrySet().forEach(e -> e.setValue("1" + e.getValue()));
                treeCodingTable.put(last.getInk(), "0");
                histogram.remove(lastIndex);

//                System.out.println();
//                System.out.println("1st phase (currentSum = " + currentSum + ", histogram.size = " + histogram.size() + "):");
//                subCodingTable.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(System.out::println);

            } else if (histogram.size() >= 2 && treeSum < histogram.get(lastIndex - 1).getQuantity()) {    //currentSum > last.getQuantity() &&
                treeSum += last.getQuantity();
                treeCodingTable.entrySet().forEach(e -> e.setValue("0" + e.getValue()));
                treeCodingTable.put(last.getInk(), "1");
                histogram.remove(lastIndex);

//                System.out.println();
//                System.out.println("2st phase (currentSum = " + currentSum + ", histogram.size = " + histogram.size() + "):");
//                subCodingTable.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(System.out::println);

            } else if (histogram.size() >= 2 && treeSum > histogram.get(lastIndex - 1).getQuantity()) {    //currentSum > last.getQuantity() &&
//                System.out.println();
//                System.out.println("3rd phase (currentSum = " + currentSum + ", histogram.size = " + histogram.size() + "):");
//                subCodingTable.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(System.out::println);
                break;


            } else if (treeSum > last.getQuantity() && histogram.size() == 1) {
                treeSum += last.getQuantity();
                treeCodingTable.entrySet().forEach(e -> e.setValue("0" + e.getValue()));
                treeCodingTable.put(last.getInk(), "1");
                histogram.remove(lastIndex);
            } else {
//                System.out.println();
//                System.out.println("4th phase (currentSum = " + currentSum + ", histogram.size = " + histogram.size() + "):");
                break;
            }

        }
        return new Tree(treeSum, treeCodingTable);
    }

    public List<Tree> generateTrees() {
        while (histogram.size() > 0) {
            trees.add(generateTree());
        }
        return trees;
    }


    public List<Sign> getHistogram() {
        return histogram;
    }

    public List<Tree> getTrees() {
        return trees;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        trees.forEach(entry -> sb.append(entry).append("\n"));
        return sb.toString();
    }
}
