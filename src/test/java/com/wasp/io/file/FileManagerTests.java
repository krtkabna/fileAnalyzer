package com.wasp.io.file;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class FileManagerTests {
    private static final String DS_STORE = ".DS_Store";
    private static final String RESOURCES = "src/test/resources";
    private static final String TEST_DIR_ROOT = RESOURCES + "/testDir";
    private static final String DEEPEST_DIR_SRC = TEST_DIR_ROOT + "/1/11";
    private static final String DEEPEST_FILE_SRC = TEST_DIR_ROOT + "/1/11/deepest.txt";
    private static final String DEEPEST_DIR_DEST = TEST_DIR_ROOT + "/2/21";
    private static final String DEEPEST_FILE_DEST = TEST_DIR_ROOT + "/2/21/deepest.txt";

    @BeforeEach
    void deleteDS_Store() {
        List<File> files = FileManager.getSubFiles(new File(TEST_DIR_ROOT));
        files.stream().filter(file -> DS_STORE.equals(file.getName())).forEach(File::delete);
    }

    @AfterEach
    void cleanup() {
        File source = new File(DEEPEST_FILE_SRC);
        File destination = new File(DEEPEST_FILE_DEST);
        if (destination.exists()) {
            destination.delete();
            new File(destination.getParent()).delete();
        }
        if (!source.exists()) {
            FileManager.createFile(source);
        }
    }

    @Test
    void countFiles() {
        assertEquals(3, FileManager.countFiles(TEST_DIR_ROOT));
    }

    @Test
    void countOneFile() {
        assertEquals(1, FileManager.countFiles(DEEPEST_FILE_SRC));
    }

    @Test
    void countDirs() {
        assertEquals(3, FileManager.countDirs(TEST_DIR_ROOT));
    }

    @Test
    void copyFile() {
        File source = new File(DEEPEST_FILE_SRC);
        File destination = new File(DEEPEST_FILE_DEST);
        assertTrue(source.exists());
        assertTrue(source.isFile());
        assertFalse(destination.exists());

        FileManager.copy(DEEPEST_FILE_SRC, DEEPEST_FILE_DEST);
        assertTrue(source.exists());
        assertTrue(destination.exists());
    }

    @Test
    void moveFile() {
        File source = new File(DEEPEST_FILE_SRC);
        File destination = new File(DEEPEST_FILE_DEST);

        assertTrue(source.exists());
        assertTrue(source.isFile());
        assertFalse(destination.exists());

        FileManager.move(DEEPEST_FILE_SRC, DEEPEST_FILE_DEST);
        assertFalse(source.exists());
        assertTrue(destination.exists());
    }

    @Test //doesn't work
    void moveDirectory() {
        File source = new File(DEEPEST_DIR_SRC);
        File destination = new File(DEEPEST_DIR_DEST);

        assertTrue(source.exists());
        assertTrue(source.isDirectory());
        assertFalse(destination.exists());

        FileManager.move(DEEPEST_DIR_SRC, DEEPEST_DIR_DEST);
        assertFalse(source.exists());
        assertTrue(destination.exists());
    }
}