package pl.rsokolowski.coder;

import java.util.LinkedHashMap;
import java.util.Map;

public class Tree {

    private final int treeSum;
    private final Map<String, String> treeCodingTable;

    public Tree(int treeSum, Map<String, String> treeCodingTable) {
        this.treeSum = treeSum;
        this.treeCodingTable = treeCodingTable;
    }

    public int getTreeSum() {
        return treeSum;
    }

    public Map<String, String> getTreeCodingTable() {
        return new LinkedHashMap<>(treeCodingTable);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("treeSum = ").append(treeSum).append(", (");
        treeCodingTable.forEach((key, value) -> sb.append(" ").append(key).append("=").append(value).append(" "));
        sb.append((")"));

        return sb.toString();
    }
}
