package pl.edu.agh.kis.lab6.domain;

import java.util.ArrayList;
import java.util.Collections;

public class Heap<T extends Comparable<T>> {

    private int heapSize;
    private ArrayList<T> tab;

    public Heap(){
        tab = new ArrayList<T>();
        heapSize = 0;
    }

    public Heap(ArrayList<T> list){
        tab = new ArrayList<T>(list);
        heapSize = tab.size();
        buildMaxHeap(tab);
    }

    public void insert(T value) {
        int currentIndex = heapSize;
        int parentIndex = parentIndex(currentIndex);
        tab.add(value);
        while( isChildGreaterThanParent(currentIndex, parentIndex) >0) {
            Collections.swap(tab,currentIndex, parentIndex);
            currentIndex = parentIndex;
            parentIndex = parentIndex(currentIndex);
        }
        heapSize++;
    }

    public int isChildGreaterThanParent(int currentIndex, int parentIndex) {
        return tab.get(currentIndex).compareTo(tab.get(parentIndex));
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

    public T extractMax(){
        if (size() == 0) {
            throw new IllegalStateException("The heap is empty");
        }
        T ret = tab.get(0);
        deleteMax();
        return ret;
    }

    private void heapify(ArrayList<T> list, int i) {
        int largest = i;
        int left = leftChild(i);
        int right = rightChild(i);
        if((left <= list.size() - 1) && (list.get(i).compareTo(list.get(left)) < 0))
            largest = left;
        if((right <= list.size() - 1) && (list.get(largest).compareTo(list.get(right)) < 0))
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

    public void buildMaxHeap(ArrayList<T> list){
        for(int i=(list.size()-1)/2;i>=0;i--) {
            heapify(list, i);
        }
    }

    public static<T extends Comparable<T>> Heap<T> merge(Heap<T> first, Heap<T> second){
        ArrayList<T> mergedTab = new ArrayList<T>(first.tab);
        mergedTab.addAll(second.tab);
        Heap<T> newHeap = new Heap<T>(mergedTab);
        return newHeap;
    }

    public void meld(Heap second){
        tab.addAll(second.tab);
        heapSize += second.size();
        buildMaxHeap(tab);
    }

    public void replace(T replacing){
        tab.set(0,replacing);
        heapify(tab, 0);
    }

    public int size() {
        return heapSize ;
    }

    public T top() {
        return tab.get(0);
    }

}
