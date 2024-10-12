package model;

import java.util.Vector;

public class SortingTask extends Task {
    private SortingMethod sortingMethod;
    private Vector<Integer> numbersVector;

    public SortingTask(String id_, String description_, SortingMethod sortingMethod_, Vector<Integer> numbersVector_) {
        super(id_, description_);
        sortingMethod = sortingMethod_;
        numbersVector = numbersVector_;
    }

    private static int partition(Vector<Integer> vector, int low, int high) {
        int pivot = vector.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (vector.get(j).compareTo(pivot) > 0) {
                i++;
                int aux = vector.get(i);
                vector.set(i, vector.get(j));
                vector.set(j, aux);
            }
        }
        int aux = vector.get(i + 1);
        vector.set(i + 1, vector.get(high));
        vector.set(high, aux);
        return  i + 1;
    }

    private static void quickSort(Vector<Integer> vector, int low, int high) {
        if (low < high) {
            int pivot = partition(vector, low, high);
            quickSort(vector, low, pivot - 1);
            quickSort(vector, pivot + 1, high);
        }
    }

    private static void bubbleSort(Vector<Integer> vector) {
        boolean swap;
        for (int i = 0; i < vector.size() - 1; i++) {
            swap = false;
            for (int j = 0; j < vector.size() - 1 - i; j++)
                if (vector.get(j).compareTo(vector.get(j + 1)) < 0) {
                    var aux = vector.get(j);
                    vector.set(j, vector.get(j + 1));
                    vector.set(j + 1, aux);
                    swap = true;
                }
            if (!swap)
                break;
        }
    }

    @Override
    public void execute() {
        switch (this.sortingMethod) {
            case SortingMethod.BubbleSort -> {
                this.bubbleSort(this.numbersVector);
            }
            case SortingMethod.QuickSort -> {
                this.quickSort(this.numbersVector, 0, this.numbersVector.size() - 1);
            }
            default -> throw new IllegalStateException("Unexpected value: " + this.sortingMethod);
        }
    }

    public Vector<Integer> getNumbersVector() {
        return this.numbersVector;
    }

}
