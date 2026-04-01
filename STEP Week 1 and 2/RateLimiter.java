import java.util.*;
class TokenBucket {
    int tokens;
    int maxTokens;
    double refillRate;
    long lastRefillTime;
    public TokenBucket(int maxTokens, double refillRate) {
        this.maxTokens = maxTokens;
        this.refillRate = refillRate;
        this.tokens = maxTokens;
        this.lastRefillTime = System.currentTimeMillis();
    }
    private void refill() {
        long now = System.currentTimeMillis();
        double seconds = (now - lastRefillTime) / 1000.0;
        int tokensToAdd = (int) (seconds * refillRate);
        if (tokensToAdd > 0) {
            tokens = Math.min(maxTokens, tokens + tokensToAdd);
            lastRefillTime = now;
        }
    }
    public synchronized boolean allowRequest() {
        refill();
        if (tokens > 0) {
            tokens--;
            return true;
        }
        return false;
    }
    public int getRemainingTokens() {
        refill();
        return tokens;
    }
}
public class RateLimiter {
    private HashMap<String, TokenBucket> clients = new HashMap<>();
    private int MAX_TOKENS = 1000;
    private double REFILL_RATE = 1000.0 / 3600;
    public String checkRateLimit(String clientId) {
        clients.putIfAbsent(clientId,new TokenBucket(MAX_TOKENS, REFILL_RATE));
        TokenBucket bucket = clients.get(clientId);
        if (bucket.allowRequest()) {
            return "Allowed (" + bucket.getRemainingTokens() + " remaining)";
        } else {
            return "Denied (0 remaining, try later)";
        }
    }
}