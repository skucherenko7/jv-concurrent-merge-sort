package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {

    private static final int THRESHOLD = 2;

    private final int[] array;
    private final int left;
    private final int right;

    public MergeSortAction(int[] array) {
        this(array, 0, array.length);
    }

    public MergeSortAction(int[] array, int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;
    }

    @Override
    protected void compute() {
        if (right - left <= THRESHOLD) {
            sortSequentially();
            return;
        }

        int mid = (left + right) / 2;

        MergeSortAction leftTask = new MergeSortAction(array, left, mid);
        MergeSortAction rightTask = new MergeSortAction(array, mid, right);

        invokeAll(leftTask, rightTask);

        merge(mid);
    }

    private void sortSequentially() {
        for (int i = left; i < right - 1; i++) {
            for (int j = i + 1; j < right; j++) {
                if (array[i] > array[j]) {
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
    }

    private void merge(int mid) {
        int[] temp = new int[right - left];

        int i = left;
        int j = mid;
        int k = 0;

        while (i < mid && j < right) {
            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }

        while (i < mid) {
            temp[k++] = array[i++];
        }

        while (j < right) {
            temp[k++] = array[j++];
        }

        System.arraycopy(temp, 0, array, left, temp.length);
    }
}
