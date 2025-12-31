import java.util.Random;

public class Sort
{
    private static final Random rand = new Random();

    public static void generateRandom(int[] arr, int min, int max) {
        for (int i = 0; i < arr.length; i = i + 1) {
            arr[i] = rand.nextInt(min, max);
        }
    }
    public static void generateMostlySorted(int[] arr, int min, int max, int movement) {
        generateRandom(arr, min, max);
        quickSort(arr);
        for (int i = 0; i < arr.length; i = i + 1) {
            arr[i] = arr[i] + rand.nextInt(0, movement);
        }

    }

    public static void quickSort(int[] arr) {
        qsort(arr, 0, arr.length - 1);
    }

    private static void qsort(int[] arr, int start, int end) {
        if (end <= start) return;
        int pivot = end;
        int leftRightmostIndex = start;
        int temp = -1;
        for (int j = start; j < end; j = j + 1) {
            if (arr[j] < arr[pivot]) { //here is where the compare function is done
                temp = arr[j];
                arr[j] = arr[leftRightmostIndex];
                arr[leftRightmostIndex] = temp;
                leftRightmostIndex += 1;
            }
        }
        temp = arr[leftRightmostIndex];
        arr[leftRightmostIndex] = arr[pivot];
        arr[pivot] = temp;
        qsort(arr, start, leftRightmostIndex - 1);
        qsort(arr, leftRightmostIndex + 1, end);
    }

    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i = i + 1) {
            if (arr[i] < arr[i - 1]) { //here is the compare function
                int insertionIndex = 0;
                int insertionValue = arr[i];
                for (int j = i - 1; j >= 0; j = j - 1) {
                    if (arr[i] >= arr[j]) { //here as well, but inverted
                        insertionIndex = j + 1;
                        break;
                    }
                }
                for (int j = i; j > insertionIndex; j = j - 1) {
                    arr[j] = arr[j - 1];
                }
                arr[insertionIndex] = insertionValue;
            }
        }
    }

    public static void bubbleSort(int[] arr) {
        for (int i = 1; i < arr.length; i = i + 1) {
            for (int j = 1; j < arr.length; j = j + 1) {
                if (arr[j] < arr[j - 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                }
            }
        }
    }


}
