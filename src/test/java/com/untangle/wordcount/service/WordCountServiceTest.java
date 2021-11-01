package com.untangle.wordcount.service;

import com.untangle.wordcount.model.Tracker;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class WordCountServiceTest {

    @Test
    void analyzeFileFromClasspath_exampleOne_noSentenceCount() throws Exception {
        HashMap<String, Tracker> actual = WordCountService.analyzeFileFromClasspath(EXAMPLE_ONE_FILE);
        assertEquals(34, actual.keySet().size());
        assertEquals(1, actual.get("all").getCount() );
        assertEquals(3, actual.get("word").getCount());
    }

    public static final String EXAMPLE_ONE_FILE = "example1.txt";
}