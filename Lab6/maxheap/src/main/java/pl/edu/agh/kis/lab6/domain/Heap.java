package pl.edu.agh.kis.lab6.domain;

import java.util.ArrayList;
import java.util.Collections;

public class Heap {

    private int heapSize;
    private ArrayList<Double> tab;

    public Heap(){
        tab = new ArrayList<Double>();
        heapSize = 0;
    }

    public Heap(ArrayList<Double> list){
        tab = new ArrayList<Double>(list);
        heapSize = tab.size();
        buildMaxHeap(tab);
    }

    public void insert(Double value) {
        int currentIndex = heapSize;
        int parentIndex = parentIndex(currentIndex);
        tab.add(value);
        while( isChildGreaterThanParent(currentIndex, parentIndex) ) {
            Collections.swap(tab,currentIndex, parentIndex);
            currentIndex = parentIndex;
            parentIndex = parentIndex(currentIndex);
        }
        heapSize++;
    }

    public boolean isChildGreaterThanParent(int currentIndex, int parentIndex) {
        return tab.get(currentIndex) > tab.get(parentIndex);
    }

    public int parentIndex(int currentIndex) {
        return (currentIndex-1)/2;
    } //currentIndex-1 bo np 2 jest dzieckiem 0, a jak podzielimy to dostaniemy 1, a o to nam nie chodzilo :)


    public int leftChild(int parentIndex){
        return 2*parentIndex+1;
    }

    public int rightChild(int parentIndex){
        return 2*parentIndex+2;
    }

    public Double extractMax(){
        if (size() == 0) {
            throw new IllegalStateException("The heap is empty");
        }
        Double ret = tab.get(0);
        deleteMax();
        return ret;
    }

    private void heapify(ArrayList<Double> list, int i) {
        int largest = i;
        int left = leftChild(i);
        int right = rightChild(i);
        if((left <= list.size() - 1) && (list.get(i) < list.get(left)))
            largest = left;
        if((right <= list.size() - 1) && (list.get(largest) < list.get(right)))
            largest = right;
        if(largest != i){
            Collections.swap(list,largest,i);
            heapify(list, largest);
        }
    }

    public void deleteMax(){
        if (size() == 0) {
            throw new IllegalStateException("The heap is empty");
        }
        tab.set(0,tab.remove(heapSize-1));
        heapSize--;
        heapify(tab,0);
    }

    public void buildMaxHeap(ArrayList<Double> list){
        for(int i=(list.size()-1)/2;i>=0;i--) {
            heapify(list, i);
        }
    }

    public static Heap merge(Heap first, Heap second){
        ArrayList<Double> mergedTab = new ArrayList<Double>(first.tab);
        mergedTab.addAll(second.tab);
        Heap newHeap = new Heap(mergedTab);
        return newHeap;
    }

    public void meld(Heap second){
        tab.addAll(second.tab);
        heapSize += second.size();
        buildMaxHeap(tab);
    }

    public void replace(Double replacing){
        tab.set(0,replacing);
        heapify(tab, 0);
    }

    public int size() {
        return heapSize ;
    }

    public double top() {
        return tab.get(0);
    }

}
