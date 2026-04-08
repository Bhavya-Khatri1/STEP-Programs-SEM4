import java.util.ArrayList;

class Transaction {
    String id;
    double fee;
    String timestamp;
    Transaction(String id, double fee, String timestamp) {
        this.id = id;
        this.fee = fee;
        this.timestamp = timestamp;
    }
    public String toString() {
        return id + ":" + fee + "@" + timestamp;
    }
}
public class TransactionSorter {
    static void bubbleSortByFee(ArrayList<Transaction> list) {
        int n = list.size();
        boolean swapped;
        int passes = 0, swaps = 0;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            passes++;
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j).fee > list.get(j + 1).fee) {
                    Transaction temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                    swapped = true;
                    swaps++;
                }
            }
            if (!swapped) break;
        }
        System.out.println("Bubble Sort Completed → Passes: " + passes + ", Swaps: " + swaps);
    }
    static void insertionSortByFeeAndTime(ArrayList<Transaction> list) {
        int shifts = 0;
        for (int i = 1; i < list.size(); i++) {
            Transaction key = list.get(i);
            int j = i - 1;
            while (j >= 0 && compare(list.get(j), key) > 0) {
                list.set(j + 1, list.get(j));
                j--;
                shifts++;
            }
            list.set(j + 1, key);
        }
        System.out.println("Insertion Sort Completed → Shifts: " + shifts);
    }
    static int compare(Transaction t1, Transaction t2) {
        if (t1.fee != t2.fee) {
            return Double.compare(t1.fee, t2.fee);
        }
        return t1.timestamp.compareTo(t2.timestamp);
    }
    static void flagHighFees(ArrayList<Transaction> list) {
        System.out.println("\nHigh-fee outliers (>50):");
        boolean found = false;
        for (Transaction t : list) {
            if (t.fee > 50) {
                System.out.println(t);
                found = true;
            }
        }
        if (!found) {
            System.out.println("None");
        }
    }
    static void sortTransactions(ArrayList<Transaction> list) {
        int size = list.size();
        if (size <= 100) {
            System.out.println("\nUsing Bubble Sort...");
            bubbleSortByFee(list);
        } else if (size <= 1000) {
            System.out.println("\nUsing Insertion Sort...");
            insertionSortByFeeAndTime(list);
        } else {
            System.out.println("\nDataset too large → Use MergeSort/QuickSort");
        }
    }
    public static void main(String[] args) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("id1", 10.5, "10:00"));
        transactions.add(new Transaction("id2", 25.0, "09:30"));
        transactions.add(new Transaction("id3", 5.0, "10:15"));
        transactions.add(new Transaction("id4", 60.0, "11:00"));
        sortTransactions(transactions);
        System.out.println("\nSorted Transactions:");
        for (Transaction t : transactions) {
            System.out.println(t);
        }
        flagHighFees(transactions);
    }
}