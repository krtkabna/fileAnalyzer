package com.wasp.io.fileAnalyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FileAnalyzer {
    private static final String SENTENCE_REGEX = "[^.!?]*[.!?]";
    private static final Pattern SENTENCE_PATTERN = Pattern.compile(SENTENCE_REGEX);
    private static final String END_PUNCTUATION = ".?!";
    private List<byte[]> buffers = new ArrayList<>();

    //fixme remove
    static void copyBytes(File from, File to) {
        try (InputStream in = new FileInputStream(from);
             OutputStream out = new FileOutputStream(to)) {
            int toWrite;
            while ((toWrite = in.read()) != -1) {
                if (isPunctuationMark((char) toWrite)) {
                    System.out.println("toWrite = " + (char) toWrite);
                }
                out.write(toWrite);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isPunctuationMark(char c) {
        return c == '.' || c == '?' || c == '!';
    }


    public static void main(String[] args) throws IOException {
//        String fileName = args[0];
//        String word = args[1];

        File from = new File("src/main/java/com/wasp/io/fileAnalyzer/xanadu.txt");
        File to = new File("src/main/java/com/wasp/io/fileAnalyzer/test.txt");
        copyBytes(from, to);

    }
}

