import java.util.Arrays;

public class RiskThreshold {
    static int linearSearch(int[] arr, int target) {
        int comparisons = 0;
        for (int i = 0; i < arr.length; i++) {
            comparisons++;
            if (arr[i] == target) {
                System.out.println("Linear Comparisons: " + comparisons);
                return i;
            }
        }
        System.out.println("Linear Comparisons: " + comparisons);
        return -1;
    }
    static int binarySearchInsert(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        int comparisons = 0;
        while (low <= high) {
            int mid = (low + high) / 2;
            comparisons++;
            if (arr[mid] == target) {
                System.out.println("Binary Comparisons: " + comparisons);
                return mid;
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        System.out.println("Binary Comparisons: " + comparisons);
        return low;
    }
    static Integer floor(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        Integer result = null;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (arr[mid] <= target) {
                result = arr[mid];
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return result;
    }
    static Integer ceiling(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        Integer result = null;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (arr[mid] >= target) {
                result = arr[mid];
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return result;
    }
    public static void main(String[] args) {
        int[] risks = {50, 10, 100, 25};
        System.out.println("Original (Unsorted): " + Arrays.toString(risks));
        int lin = linearSearch(risks, 30);
        System.out.println("Linear Result Index: " + lin);
        Arrays.sort(risks);
        System.out.println("\nSorted Risks: " + Arrays.toString(risks));
        int index = binarySearchInsert(risks, 30);
        System.out.println("Binary Insert Position: " + index);
        Integer floorVal = floor(risks, 30);
        Integer ceilVal = ceiling(risks, 30);
        System.out.println("Floor (<=30): " + floorVal);
        System.out.println("Ceiling (>=30): " + ceilVal);
    }
}