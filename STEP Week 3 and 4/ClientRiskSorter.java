class Client {
    String name;
    int riskScore;
    double accountBalance;
    Client(String name, int riskScore, double accountBalance) {
        this.name = name;
        this.riskScore = riskScore;
        this.accountBalance = accountBalance;
    }
    public String toString() {
        return name + ":" + riskScore + " (Bal:" + accountBalance + ")";
    }
}
public class ClientRiskSorter {
    static void bubbleSortAsc(Client[] arr) {
        int n = arr.length;
        int swaps = 0;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].riskScore > arr[j + 1].riskScore) {
                    Client temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swaps++;
                    swapped = true;
                    System.out.println("Swap: " + arr[j].name + " <-> " + arr[j + 1].name);
                }
            }
            if (!swapped) break;
        }
        System.out.println("Total swaps: " + swaps);
    }
    static void insertionSortDesc(Client[] arr) {
        int shifts = 0;
        for (int i = 1; i < arr.length; i++) {
            Client key = arr[i];
            int j = i - 1;
            while (j >= 0 && compare(arr[j], key) < 0) {
                arr[j + 1] = arr[j];
                j--;
                shifts++;
            }
            arr[j + 1] = key;
        }
        System.out.println("Total shifts: " + shifts);
    }
    static int compare(Client c1, Client c2) {
        if (c1.riskScore != c2.riskScore) {
            return Integer.compare(c1.riskScore, c2.riskScore);
        }
        return Double.compare(c1.accountBalance, c2.accountBalance);
    }
    static void printTopRisks(Client[] arr, int topN) {
        System.out.println("\nTop " + topN + " highest risk clients:");
        for (int i = 0; i < topN && i < arr.length; i++) {
            System.out.println(arr[i].name + " (" + arr[i].riskScore + ")");
        }
    }
    public static void main(String[] args) {
        Client[] clients = {
            new Client("clientC", 80, 5000),
            new Client("clientA", 20, 8000),
            new Client("clientB", 50, 3000)
        };
        System.out.println("Bubble Sort (Ascending Risk):");
        bubbleSortAsc(clients);
        System.out.println("\nSorted (ASC):");
        for (Client c : clients) {
            System.out.println(c.name + ":" + c.riskScore);
        }
        System.out.println("\nInsertion Sort (Descending Risk + Balance):");
        insertionSortDesc(clients);
        System.out.println("\nSorted (DESC):");
        for (Client c : clients) {
            System.out.println(c.name + ":" + c.riskScore);
        }
        printTopRisks(clients, 10);
    }
}