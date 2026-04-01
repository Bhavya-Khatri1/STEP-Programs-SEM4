import java.util.*;
class Transaction {
    int id;
    int amount;
    String merchant;
    String account;
    long time;
    public Transaction(int id, int amount, String merchant, String account, long time) {
        this.id = id;
        this.amount = amount;
        this.merchant = merchant;
        this.account = account;
        this.time = time;
    }
}
public class TransactionAnalyzer {
    public static List<int[]> twoSum(List<Transaction> list, int target) {
        HashMap<Integer, Transaction> map = new HashMap<>();
        List<int[]> result = new ArrayList<>();
        for (Transaction t : list) {
            int complement = target - t.amount;
            if (map.containsKey(complement)) {
                result.add(new int[]{map.get(complement).id, t.id});
            }
            map.put(t.amount, t);
        }
        return result;
    }
    public static List<int[]> twoSumWithTime(List<Transaction> list, int target) {
        HashMap<Integer, List<Transaction>> map = new HashMap<>();
        List<int[]> result = new ArrayList<>();
        for (Transaction t : list) {
            int complement = target - t.amount;
            if (map.containsKey(complement)) {
                for (Transaction prev : map.get(complement)) {
                    if (Math.abs(t.time - prev.time) <= 3600000) {
                        result.add(new int[]{prev.id, t.id});
                    }
                }
            }
            map.putIfAbsent(t.amount, new ArrayList<>());
            map.get(t.amount).add(t);
        }
        return result;
    }
    public static List<List<Integer>> kSum(int[] nums, int target, int k, int start) {
        List<List<Integer>> res = new ArrayList<>();
        if (k == 2) {
            HashSet<Integer> set = new HashSet<>();
            for (int i = start; i < nums.length; i++) {
                int complement = target - nums[i];
                if (set.contains(complement)) {
                    res.add(Arrays.asList(nums[i], complement));
                }
                set.add(nums[i]);
            }
            return res;
        }
        for (int i = start; i < nums.length; i++) {
            for (List<Integer> subset : kSum(nums, target - nums[i], k - 1, i + 1)) {
                List<Integer> temp = new ArrayList<>();
                temp.add(nums[i]);
                temp.addAll(subset);
                res.add(temp);
            }
        }
        return res;
    }
    public static List<String> detectDuplicates(List<Transaction> list) {
        HashMap<String, Set<String>> map = new HashMap<>();
        List<String> result = new ArrayList<>();
        for (Transaction t : list) {
            String key = t.amount + "-" + t.merchant;
            map.putIfAbsent(key, new HashSet<>());
            map.get(key).add(t.account);
            if (map.get(key).size() > 1) {
                result.add("Duplicate found: " + key);
            }
        }
        return result;
    }
    public static void main(String[] args) {
        List<Transaction> transactions = new ArrayList<>();
        long now = System.currentTimeMillis();
        transactions.add(new Transaction(1, 500, "StoreA", "acc1", now));
        transactions.add(new Transaction(2, 300, "StoreB", "acc2", now + 1000));
        transactions.add(new Transaction(3, 200, "StoreC", "acc3", now + 2000));
        transactions.add(new Transaction(4, 500, "StoreA", "acc4", now + 3000));
        System.out.println("Two Sum:");
        for (int[] pair : twoSum(transactions, 500)) {
            System.out.println(pair[0] + " , " + pair[1]);
        }
        System.out.println("\nTwo Sum (Time Window):");
        for (int[] pair : twoSumWithTime(transactions, 500)) {
            System.out.println(pair[0] + " , " + pair[1]);
        }
        System.out.println("\nK Sum:");
        int[] nums = {500, 300, 200};
        System.out.println(kSum(nums, 1000, 3, 0));
        System.out.println("\nDuplicates:");
        for (String s : detectDuplicates(transactions)) {
            System.out.println(s);
        }
    }
}