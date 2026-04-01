import java.util.*;
class Event {
    String url;
    String userId;
    String source;
    public Event(String url, String userId, String source) {
        this.url = url;
        this.userId = userId;
        this.source = source;
    }
}
public class AnalyticsSystem {
    private HashMap<String, Integer> pageViews = new HashMap<>();
    private HashMap<String, Set<String>> uniqueUsers = new HashMap<>();
    private HashMap<String, Integer> sourceCount = new HashMap<>();
    public void processEvent(Event e) {
        pageViews.put(e.url, pageViews.getOrDefault(e.url, 0) + 1);
        uniqueUsers.putIfAbsent(e.url, new HashSet<>());
        uniqueUsers.get(e.url).add(e.userId);
        sourceCount.put(e.source, sourceCount.getOrDefault(e.source, 0) + 1);
    }
    public List<String> getTopPages() {
        PriorityQueue<Map.Entry<String, Integer>> pq =
                new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
        pq.addAll(pageViews.entrySet());
        List<String> topPages = new ArrayList<>();
        int count = 0;
        while (!pq.isEmpty() && count < 10) {
            Map.Entry<String, Integer> entry = pq.poll();
            String page = entry.getKey();
            int views = entry.getValue();
            int unique = uniqueUsers.get(page).size();
            topPages.add(page + " - " + views + " views (" + unique + " unique)");
            count++;
        }
        return topPages;
    }
    public void printSourceStats() {
        int total = 0;
        for (int count : sourceCount.values()) {
            total += count;
        }
        for (String source : sourceCount.keySet()) {
            int count = sourceCount.get(source);
            double percent = (count * 100.0) / total;
            System.out.println(source + ": " + percent + "%");
        }
    }
}