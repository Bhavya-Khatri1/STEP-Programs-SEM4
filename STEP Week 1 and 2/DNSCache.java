import java.util.*;
class DNSEntry {
    String ip;
    long expiryTime;
    public DNSEntry(String ip, long ttlMillis) {
        this.ip = ip;
        this.expiryTime = System.currentTimeMillis() + ttlMillis;
    }
    public boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}
public class DNSCache {
    private int capacity = 5;
    private LinkedHashMap<String, DNSEntry> cache =
        new LinkedHashMap<String, DNSEntry>(16, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<String, DNSEntry> eldest) {
                return size() > capacity;
            }
        };
    private int hits = 0;
    private int misses = 0;
    private String queryUpstream(String domain) {
        return "192.168." + new Random().nextInt(255);
    }
    public String resolve(String domain) {
        DNSEntry entry = cache.get(domain);
        if (entry != null && !entry.isExpired()) {
            hits++;
            return "Cache HIT → " + entry.ip;
        }
        misses++;
        String newIP = queryUpstream(domain);
        DNSEntry newEntry = new DNSEntry(newIP, 5000);
        cache.put(domain, newEntry);
        if (entry != null) {
            return "Cache EXPIRED → New IP: " + newIP;
        }
        return "Cache MISS → New IP: " + newIP;
    }
    public String getCacheStats() {
        int total = hits + misses;
        double hitRate = total == 0 ? 0 : (hits * 100.0) / total;
        return "Hit Rate: " + hitRate + "%";
    }
}