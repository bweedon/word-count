package com.untangle.wordcount.service;

import com.untangle.wordcount.model.Tracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

@Service
public class WordCountService {

    static Logger logger = LoggerFactory.getLogger(WordCountService.class);

    public static HashMap<String, Tracker> analyzeFileFromClasspath(String fileName) throws IOException {
        logger.trace("Entering with filename: {}", fileName);
        HashMap<String, Tracker> data = new HashMap<>();
        // Read the whole document into a string. Assuming whatever this is run on can handle this for now
        // Need the string to be able to use the BreakIterator to split by sentence.
        String fullDoc = WordCountService.readDocFromClasspath(fileName);
        // Get a list of sentences to loop over.
        ArrayList<String> sentences = WordCountService.getSentencesFromDoc(fullDoc);
        logger.info("Looping thorugh {} sentences..", sentences.size());
        for (int i = 0; i < sentences.size(); i++) {
            String sentence = sentences.get(i);
            // Process the sentence and add the info into data. i + 1 because we want the first sentence to be 1, not 0
            data = processSentence(sentence, i + 1, data);
            logger.debug("Processed sentence {}, {} keys in data", i, data.keySet().size());
        }
        logger.trace("Leaving, returning: {}", data);
        return data;
    }

    private static String readDocFromClasspath(String fileName) throws IOException {
        logger.trace("Entering with filename: {}", fileName);
        Class clazz = WordCountService.class;
        InputStream classStream = clazz.getResourceAsStream(String.format("/%s", fileName));
        StringBuilder fullDoc = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(classStream))) {
            String line;
            while((line = reader.readLine()) != null) {
                logger.debug("Appending line: {}", line);
                fullDoc.append(line);
                // Add a space instead of new line so that we don't have to replace it later.
                fullDoc.append(" ");
            }
        } catch(FileNotFoundException fnfe) {
            logger.error(String.format("The file %s wasn't found on the class path using /%s", fileName, fileName), fnfe);
            throw fnfe;
        } catch(IOException ioe) {
            logger.error(String.format("An io exception occured trying to process the file /%s", fileName), ioe);
            throw ioe;
        }
        String fullDocString = fullDoc.toString();
        logger.trace("Leaving, returning: {}", fullDocString);
        return fullDocString;
    }

    private static ArrayList<String> getSentencesFromDoc(String fullDoc) {
        logger.trace("Entering, with fullDoc: {}", fullDoc);
        ArrayList<String> sentences = new ArrayList<>();
        // Never new about this class until I worked on this assignment and started googling haha
        // Pass in English as the language, since that is the one assumption we can make.
        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.ENGLISH);
        iterator.setText(fullDoc);
        int beginning = iterator.first();
        int endOfSentenceIndex;
        while((endOfSentenceIndex = iterator.next()) != BreakIterator.DONE) {
            logger.debug("Creating index with start: {} and end: {}", beginning, endOfSentenceIndex);
            String sentence = fullDoc.substring(beginning, endOfSentenceIndex);
            logger.debug("Adding sentence {}", sentence);
            sentences.add(sentence);
            beginning = endOfSentenceIndex++;
        }
        logger.trace("Leaving, returning {} sentences ", sentences.size());
        return sentences;
    }

    private static HashMap<String, Tracker> processSentence(String sentence, int sentenceIndex, HashMap<String, Tracker> dataSoFar) {
        logger.trace("Entering, with sentence: {} and {} keys in dataSoFar", sentence, dataSoFar.keySet().size());
        BreakIterator wordIterator = BreakIterator.getWordInstance(Locale.ENGLISH);
        wordIterator.setText(sentence);
        int beginning = wordIterator.first();
        int end;
        while((end = wordIterator.next()) != BreakIterator.DONE) {
            String word = sentence.substring(beginning, end).trim().toLowerCase(Locale.ENGLISH);
            beginning = end;
            // Don't add the symbols that get parsed out by the word iterator.
            if(word.length() == 0 || (word.length() != 0 && !Character.isLetterOrDigit(word.charAt(0)))) {
                continue;
            }
            Tracker tracker = null;
            if(dataSoFar.containsKey(word)) {
                tracker = dataSoFar.get(word);
                tracker.setCount(tracker.getCount() + 1);
                tracker.getSentenceIndex().add(sentenceIndex);
            } else {
                ArrayList<Integer> sentenceIndexes = new ArrayList<>();
                sentenceIndexes.add(sentenceIndex);
                tracker = new Tracker(1, sentenceIndexes);
            }
            dataSoFar.put(word, tracker);
        }
        logger.trace("Leaving, returning dataSoFar with {} keys", dataSoFar.keySet().size());
        return dataSoFar;
    }
}
