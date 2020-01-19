package pl.rsokolowski.coder;

import pl.rsokolowski.support.Support;

import java.util.*;
import java.util.stream.Collectors;

public class HuffmanTree {

    private int totalSum;
    private final Map<String, String> finalCodingTable;
    private final List<Tree> trees;

    public HuffmanTree(List<Sign> histogram) {
        this.totalSum = 0;
        this.finalCodingTable = new LinkedHashMap<>();
        this.trees = sortHuffmanTrees(generateListOfTrees(histogram));
        Support.logger.info("trees generated form histogram: " + trees);


    }

    public List<Tree> generateListOfTrees(List<Sign> histogram) {
        List<Tree> trees = new ArrayList<>();
        while (histogram.size() > 0) {
            TreesFromHistogram treeFromHistogram = new TreesFromHistogram(histogram);
            Tree tree = treeFromHistogram.generateTree();
            trees.add(tree);
            histogram = treeFromHistogram.getHistogram();
        }

        return trees;
    }

    public List<Tree> sortHuffmanTrees(List<Tree> unsortedTrees) {
        return unsortedTrees.stream().sorted(Comparator.comparing(Tree::getTreeSum).reversed()).collect(Collectors.toList());
    }

    private Map<String, String> addZeroOrOneToCodingTab(Tree tree, String zeroOrOne) {
        if (!Support.isZeroOrOne(zeroOrOne)) {
            throw new IllegalArgumentException("zeroOrOne argument in addZeroOrOne needs to be 0 or 1 :)");
        }
//        return tree.getTreeCodingTable().entrySet().stream().map(entry -> entry.setValue(zeroOrOne + entry.getValue())).collect(Collectors.toMap(k -> k, v -> v));
        return tree.getTreeCodingTable().entrySet().stream().collect(Collectors.toMap(k -> k.getKey(), v -> zeroOrOne + v.getValue()));
    }

    public Map<String, String> addTwoCodingTables(Map<String, String> firstCodingTable, Map<String, String> secondCodingTabled) {
        firstCodingTable.putAll(secondCodingTabled);
        return firstCodingTable;
    }

    public Tree addTwoTrees(Tree firstTree, Tree secondTree) {

        int addedTreeSum = firstTree.getTreeSum() + secondTree.getTreeSum();
        Map<String, String> firstCodingTable = addZeroOrOneToCodingTab(firstTree, "0");
        Map<String, String> secondCodingTable = addZeroOrOneToCodingTab(secondTree, "1");
        Map<String, String> addedCodingTables = addTwoCodingTables(firstCodingTable, secondCodingTable);

        return new Tree(addedTreeSum, addedCodingTables);
    }


    public Optional<Map<String, String>> generateFinalCodingTable() {
        if (trees.isEmpty()) {
            Support.logger.error("no data available, no trees, nothing to do:(");
            return Optional.empty();
        }

        if (trees.size() == 1) {
            return Optional.of(trees.get(0).getTreeCodingTable());
        }
        Tree firstTree;
        Tree secondTree;
        Tree twoTreesAdded;

        while (trees.size() >= 2) {
            firstTree = trees.get(trees.size() - 2);
            secondTree = trees.get(trees.size() - 1);

            twoTreesAdded = addTwoTrees(firstTree, secondTree);

            trees.remove(firstTree);
            trees.remove(secondTree);
            trees.add(twoTreesAdded);

        }

        if (trees.size() == 1) {
            Support.logger.info("codding table was created:      " + trees.get(0).getTreeCodingTable());
            return Optional.of(trees.get(0).getTreeCodingTable());
        } else {
            Support.logger.error("something went wrong... it supposed to be precisely one tree left, but we have here " + trees.size() + " tree(s)");
            return Optional.empty();
        }

    }

}
