package com.untangle.wordcount.model;

import java.util.ArrayList;

public class Tracker {
    private int count;
    private ArrayList<Integer> sentenceIndex;

    public Tracker() { }

    public Tracker(int count, ArrayList<Integer> sentenceIndex) {
        this.count = count;
        this.sentenceIndex = sentenceIndex;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<Integer> getSentenceIndex() {
        return sentenceIndex;
    }

    public void setSentenceIndex(ArrayList<Integer> sentenceIndex) {
        this.sentenceIndex = sentenceIndex;
    }
}
