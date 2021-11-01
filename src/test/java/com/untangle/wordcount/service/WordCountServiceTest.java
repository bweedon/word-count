package com.untangle.wordcount.service;

import com.untangle.wordcount.model.Tracker;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordCountServiceTest {

    @Test
    void analyzeFileFromClasspath_exampleOne_noSentenceCount() throws Exception {
        HashMap<String, Tracker> actual = WordCountService.analyzeFileFromClasspath(EXAMPLE_ONE_FILE);
        assertEquals(34, actual.keySet().size());
        assertEquals(1, actual.get("all").getCount() );
        assertEquals(3, actual.get("word").getCount());
        assertEquals(1, actual.get("i.e").getCount());
    }

    @Test
    void analyzeFileFromClasspath_exampleOne_withSentenceCount() throws Exception {
        HashMap<String, Tracker> actual = WordCountService.analyzeFileFromClasspath(EXAMPLE_ONE_FILE);
        assertEquals(34, actual.keySet().size());
        assertEquals(new ArrayList<Integer>(List.of(1,2)), actual.get("in").getSentenceIndex());
        // This one is wrong in the assignment example output. Should be 2,2 for sentence index.
        assertEquals(new ArrayList<Integer>(List.of(2, 2)), actual.get("each").getSentenceIndex());
        assertEquals(new ArrayList<Integer>(List.of(1,1,2)), actual.get("word").getSentenceIndex());
    }

    public static final String EXAMPLE_ONE_FILE = "example1.txt";
}