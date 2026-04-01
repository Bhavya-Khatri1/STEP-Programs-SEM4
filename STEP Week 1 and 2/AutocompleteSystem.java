import java.util.*;
class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    PriorityQueue<String> topQueries =new PriorityQueue<>((a, b) -> a.compareTo(b));
}
public class AutocompleteSystem {
    private TrieNode root = new TrieNode();
    private HashMap<String, Integer> freqMap = new HashMap<>();
    public void insert(String query) {
        freqMap.put(query, freqMap.getOrDefault(query, 0) + 1);
        TrieNode node = root;
        for (char c : query.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
            node.topQueries.add(query);
            if (node.topQueries.size() > 10) {
                node.topQueries.poll();
            }
        }
    }
    public List<String> search(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            if (!node.children.containsKey(c)) {
                return new ArrayList<>();
            }
            node = node.children.get(c);
        }
        return new ArrayList<>(node.topQueries);
    }
}