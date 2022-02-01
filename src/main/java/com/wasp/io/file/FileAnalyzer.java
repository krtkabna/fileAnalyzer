package com.wasp.io.file;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FileAnalyzer {
    private static final String WORD_REGEX_FORMAT = "\\b%s\\b";
    private static final String END_PUNCTUATION_REGEX = "[.?!]";
    private static final String STORY_PATH = "/Users/wasp/luxoft/upskilling/fileAnalyzer/src/test/resources/story.txt";
    private static final String SHORT_STORY_PATH = "/Users/wasp/luxoft/upskilling/fileAnalyzer/src/test/resources/story_short.txt";
    private static final int DEFAULT_BUFFER_SIZE = 1024;

    public static void main(String[] args) throws IOException {
        Pattern ogre = compileWord("ogre");
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(STORY_PATH))) {

            List<String> allSentences = readAllSentences(in);
            List<String> ogreSentences = filterSentences(allSentences, ogre);
            for (String ogreSentence : ogreSentences) {
                System.out.println(ogreSentence);
            }
            System.out.println("The word 'ogre' has occurred in a sentence " + countWordOccurrences(allSentences, ogre) + " times.");
        }
    }


    private static boolean isPunctuationMark(char c) {
        return c == '.' || c == '?' || c == '!';
    }

    private static String readFileToString(BufferedInputStream in) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : in.readAllBytes()) {
            stringBuilder.append((char) b);
        }
        return stringBuilder.toString();
    }

    private static List<String> readAllSentences(BufferedInputStream in) throws IOException {
        StringBuilder sentenceBuilder = new StringBuilder();
        List<String> sentences = new LinkedList<>();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        int count;
        while ((count = in.read(buffer)) != -1 && in.available() >= 0) {
            for (int i = 0; i < count; i++) {
                sentenceBuilder.append((char) buffer[i]);
                //if isPunctuationMark(b) list.add(sb)
                if (isPunctuationMark((char) buffer[i])) {
                    sentences.add(sentenceBuilder.toString());
                    sentenceBuilder = new StringBuilder();
                }
            }
        }
        return sentences;
    }

    private static List<String> filterSentences(List<String> sentences, String delimiterRegex, Pattern wordPattern) {
        return sentences.stream()
            .filter(wordPattern.asPredicate())
            .map(s -> s.replaceAll("\\s", " ").trim())
            .collect(Collectors.toList());
    }

    private static List<String> filterSentences(List<String> sentences, Pattern word) {
        return filterSentences(sentences, END_PUNCTUATION_REGEX, word);
    }

    private static List<String> filterSentences(BufferedInputStream in, Pattern word) throws IOException {
        return filterSentences(readAllSentences(in), END_PUNCTUATION_REGEX, word);
    }


    private static long countWordOccurrences(List<String> sentences, Pattern word) {
        long count = 0;
        String[] words;
        for (String sentence : sentences) {
            words = array(sentence);
            count += Arrays.stream(words)
                .filter(word.asPredicate())
                .count();
        }
        return count;
    }

    private static String[] array(String s) {
        return s.split("\\s");
    }

    private static Pattern compileWord(String word) {
        return Pattern.compile(String.format(WORD_REGEX_FORMAT, word), Pattern.CASE_INSENSITIVE);
    }
}

