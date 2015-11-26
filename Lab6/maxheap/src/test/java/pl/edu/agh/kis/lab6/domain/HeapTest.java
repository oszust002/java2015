package pl.edu.agh.kis.lab6.domain;

import org.junit.Test;

import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.Comparator;

public class HeapTest {

    @Test
    public void insert0intoNewHeap_topIs0() {

        Heap<Double> heap = new Heap<Double>();
        heap.insert(0.0);

        assertEquals("size should be 1",1,heap.size());
        assertEquals(0,heap.top(),0.001);
        assertEquals(1,heap.size());
    }

    @Test
    public void insert0AndThen2intoNewHeap_topIs2() {

        Heap<Double> heap = new Heap<Double>();
        heap.insert(0.0);
        heap.insert(2.0);

        assertEquals("size should be 2",2,heap.size());
        assertEquals(2,heap.top(),0.001);
    }

    @Test
    public void insert0And2And3And5And6intoNewHeap_topIs6() {

        Heap<Double> heap = new Heap<Double>();
        heap.insert(0.0);
        heap.insert(2.0);
        heap.insert(3.0);
        heap.insert(5.0);
        heap.insert(6.0);

        assertEquals(6,heap.top(),0.001);
    }

    @Test
    public void insert0And3And2And5And6_childrenIndexOf0Are1And2(){
        Heap<Double> heap = new Heap<Double>();
        heap.insert(0.0);
        heap.insert(3.0);
        heap.insert(2.0);
        heap.insert(5.0);
        heap.insert(6.0);

        assertEquals("Left child index should be 1", 1, heap.leftChild(0));
        assertEquals("Right child index should be 2",2,heap.rightChild(0));
    }

    @Test
    public void insert0And3And2And5And6_extractIs6_newTopIs5_sizeIs4(){
        Heap<Double> heap = new Heap<Double>();
        heap.insert(0.0);
        heap.insert(3.0);
        heap.insert(2.0);
        heap.insert(5.0);
        heap.insert(6.0);

        assertEquals("Max should be 6", 6, heap.extractMax(), 0.01);
        assertEquals("Top should be 5", 5, heap.top(), 0.01);
        assertEquals("Size should be 4", 4, heap.size(), 0.01);
    }

    @Test
    public void createArrayListAndBuildMaxHeap_topIs6(){
        Heap<Double> heap = new Heap<Double>();
        ArrayList<Double> list = new ArrayList<Double>();
        list.add(0.0);
        list.add(2.0);
        list.add(3.0);
        list.add(5.0);
        list.add(6.0);
        heap.buildMaxHeap(list);
        assertEquals("Top should be 6", 6, list.get(0), 0.01);

    }

    @Test
    public void createTwoHeapsAndMergeIntoNewHeap(){
        Heap<Double> first = new Heap<Double>();
        Heap<Double> second = new Heap<Double>();
        first.insert(0.0);
        first.insert(2.0);
        first.insert(3.0);
        first.insert(5.0);
        second.insert(6.0);
        second.insert(4.0);
        second.insert(7.0);
        Heap<Double> merged = Heap.merge(first, second);
        assertEquals("Size should be 7", 7, merged.size());
        assertEquals("Top is 7", 7, merged.top(), 0.01);
    }

    @Test
    public void createTwoHeapsAndMeldSecondToFirst(){
        Heap<Double> first = new Heap<Double>();
        Heap<Double> second = new Heap<Double>();
        first.insert(0.0);
        first.insert(2.0);
        first.insert(3.0);
        first.insert(5.0);
        second.insert(6.0);
        second.insert(4.0);
        second.insert(7.0);
        first.meld(second);
        assertEquals("Size should be 7", 7, first.size());
        assertEquals("Top is 7", 7, first.top(), 0.01);
    }

    @Test
    public void createHeapCheckTop_replaceTopWith1_checkTopAgain(){
        Heap<Double> heap = new Heap<Double>();
        heap.insert(0.0);
        heap.insert(3.0);
        heap.insert(2.0);
        heap.insert(5.0);
        heap.insert(6.0);

        assertEquals("Top should be 6", 6, heap.top(),0.01);
        heap.replace(1.0);
        assertEquals("Top should be 5", 5, heap.top(),0.01);

    }

    @Test
    public void checkGenericHeapForStrings_TopIsAmen(){
        Heap<String> heap = new Heap<String>();
        heap.insert("mama");
        heap.insert("tata");
        heap.insert("amen");

        assertEquals("Top should be tata","tata",heap.top());

    }

    @Test
    public void createDoubleHeapWithReversedComparator_topIs0(){
        Comparator<Double> comp = new Comparator<Double>() {
            @Override
            public int compare(Double o1, Double o2) {
                if(o1.compareTo(o2) > 0)
                    return -1;
                else if (o1.compareTo(o2) < 0)
                    return 1;
                else 
                    return 0;
            }
        };

        Heap<Double> heap = new Heap<Double>(comp);
        heap.insert(5.0);
        heap.insert(2.0);
        heap.insert(1.0);
        heap.insert(0.0);
        heap.insert(6.0);

        assertEquals("Top should be 0", 0, heap.top(), 0.01);
    }
}
