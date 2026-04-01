import java.util.*;
class Video {
    String id;
    String content;
    public Video(String id, String content) {
        this.id = id;
        this.content = content;
    }
}
public class MultiLevelCache { 
    private LinkedHashMap<String, Video> L1 =
        new LinkedHashMap<String, Video>(10000, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<String, Video> eldest) {
                return size() > 10000;
            }
        };
    private LinkedHashMap<String, Video> L2 =
        new LinkedHashMap<String, Video>(100000, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<String, Video> eldest) {
                return size() > 100000;
            }
        };
    private HashMap<String, Video> database = new HashMap<>();
    private HashMap<String, Integer> accessCount = new HashMap<>();
    private int L1_hits = 0, L2_hits = 0, L3_hits = 0;
    public MultiLevelCache() {
        database.put("video_123", new Video("video_123", "Movie A"));
        database.put("video_999", new Video("video_999", "Movie B"));
    }
    public Video getVideo(String videoId) {
        if (L1.containsKey(videoId)) {
            L1_hits++;
            return L1.get(videoId);
        }
        if (L2.containsKey(videoId)) {
            L2_hits++;
            Video v = L2.get(videoId);
            promoteToL1(videoId, v);
            return v;
        }
        if (database.containsKey(videoId)) {
            L3_hits++;
            Video v = database.get(videoId);
            L2.put(videoId, v);
            return v;
        }
        return null;
    }
    private void promoteToL1(String videoId, Video v) {
        accessCount.put(videoId, accessCount.getOrDefault(videoId, 0) + 1);
        if (accessCount.get(videoId) >= 2) {
            L1.put(videoId, v);
        }
    }
    public void invalidate(String videoId) {
        L1.remove(videoId);
        L2.remove(videoId);
    }
    public void getStats() {
        int total = L1_hits + L2_hits + L3_hits;
        System.out.println("L1 Hit Rate: " + percent(L1_hits, total));
        System.out.println("L2 Hit Rate: " + percent(L2_hits, total));
        System.out.println("L3 Hit Rate: " + percent(L3_hits, total));
    }
    private double percent(int x, int total) {
        return total == 0 ? 0 : (x * 100.0) / total;
    }
    public static void main(String[] args) {
        MultiLevelCache cache = new MultiLevelCache();
        System.out.println(cache.getVideo("video_123"));
        System.out.println(cache.getVideo("video_123"));
        System.out.println(cache.getVideo("video_123"));
        System.out.println(cache.getVideo("video_999"));
        cache.getStats();
    }
}