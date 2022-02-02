package com.wasp.io.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class FileManager {

    /**
     * Method that counts files in a given directory and all subdirectories
     *
     * @param path path to directory
     * @return the total number of files found
     */
    public static int countFiles(String path) {
        File source = new File(path);
        checkSourceExists(source);
        if (source.isFile()) {
            return 1;
        } else {
            int count = 0;
            List<File> files = getSubFiles(source);
            for (File file : files) {
                if (file.isFile()) {
                    count++;
                }
            }
            return count;
        }
    }

    /**
     * Method that counts underlying directories in a given directory and all subdirectories
     *
     * @param path path to directory
     * @return the total number of directories found
     */
    public static int countDirs(String path) {
        File source = new File(path);
        checkSourceExists(source);
        int count = 0;
        List<File> files = getSubFiles(source);
        for (File file : files) {
            if (file.isFile()) count++;
        }
        return count;
    }


    /**
     * Method that copies files and directories.
     *
     * @param from source path
     * @param to   destination path
     */
    public static void copy(String from, String to) {
        File source = new File(from);
        File destination = new File(to);
        checkSourceExists(source);
        if (destination.exists()) {
            throw getRuntimeException("File already exists");
        }
        if (source.isDirectory()) {
            //fixme
            createDirectory(source, destination);
        } else if (source.isFile()) {
            createFile(destination);
        }
    }

    /**
     * Method that moves files and directories.
     *
     * @param from source path
     * @param to   destination path
     */
    public static void move(String from, String to) {
        copy(from, to);
        File source = new File(from);
        source.delete();
    }

    static boolean createDirectory(File source, File destination) {
        boolean result = false;
        List<File> sourceSubFiles = getSubFiles(source);
        if (sourceSubFiles.isEmpty()) {
            result = destination.mkdir();
        } else {
            //fixme go through destination, not source
            for (File file : sourceSubFiles) {
                if (file.isDirectory()) {
                    result = destination.mkdirs();
                } else if (file.isFile()) {
                    result = createFile(destination);
                }
            }
        }
        return result;
    }

    static boolean createFile(File destination) {
        File parent = new File(destination.getParent());
        boolean dirsCreated = parent.mkdirs();
        boolean fileCreated;
        try {
            fileCreated = destination.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return dirsCreated && fileCreated;
    }

    static List<File> getSubFiles(File source) {
        if (!source.isDirectory()) {
            throw getRuntimeException("Source should be a directory: " + source.getPath());
        }
        List<File> result = new ArrayList<>();
        List<File> deepFiles = Arrays.asList(source.listFiles());
        result.addAll(deepFiles);
        for (File file : deepFiles) {
            if (file.isDirectory()) {
                result.addAll(getSubFiles(file));
            }
        }
        return result;
    }

    private static void checkSourceExists(File source) {
        if (!source.exists()) {
            throw getRuntimeException("Nothing exists by this path: " + source.getPath());
        }
    }

    private static RuntimeException getRuntimeException(String msg) {
        return new RuntimeException(msg);
    }
}
