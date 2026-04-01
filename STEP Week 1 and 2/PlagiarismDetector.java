import java.util.*;
public class PlagiarismDetector {
    private HashMap<String, Set<String>> index = new HashMap<>();
    private int N = 3;
    private List<String> generateNGrams(String text) {
        String[] words = text.split(" ");
        List<String> grams = new ArrayList<>();
        for (int i = 0; i <= words.length - N; i++) {
            StringBuilder gram = new StringBuilder();
            for (int j = i; j < i + N; j++) {
                gram.append(words[j]).append(" ");
            }
            grams.add(gram.toString().trim());
        }
        return grams;
    }
    public void addDocument(String docId, String text) {
        List<String> grams = generateNGrams(text);
        for (String gram : grams) {
            index.putIfAbsent(gram, new HashSet<>());
            index.get(gram).add(docId);
        }
    }
    public void analyzeDocument(String docId, String text) {
        List<String> grams = generateNGrams(text);
        HashMap<String, Integer> matchCount = new HashMap<>();
        for (String gram : grams) {
            if (index.containsKey(gram)) {
                for (String otherDoc : index.get(gram)) {
                    if (!otherDoc.equals(docId)) {
                        matchCount.put(otherDoc,
                            matchCount.getOrDefault(otherDoc, 0) + 1);
                    }
                }
            }
        }
        for (String otherDoc : matchCount.keySet()) {
            int matches = matchCount.get(otherDoc);
            double similarity = (matches * 100.0) / grams.size();
            System.out.println("Compared with: " + otherDoc);
            System.out.println("Matches: " + matches);
            System.out.println("Similarity: " + similarity + "%");
            if (similarity > 50) {
                System.out.println("PLAGIARISM DETECTED!");
            }
            System.out.println();
        }
    }
}