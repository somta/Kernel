package com.matecoder.common.io;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilTest {

    @TempDir
    Path tempDir;

    @Test
    void extractFilePath() {
        assertEquals("d:\\path\\", FileUtil.extractFilePath("d:\\path\\file.txt"));
        assertEquals("/home/user/", FileUtil.extractFilePath("/home/user/file.txt"));
    }

    @Test
    void extractFilePathNoSeparator() {
        assertEquals("", FileUtil.extractFilePath("file.txt"));
    }

    @Test
    void extractFileName() {
        assertEquals("file.txt", FileUtil.extractFileName("d:\\path\\file.txt"));
        // On Windows, extractFileName uses File.separator (\\) by default,
        // so forward-slash paths won't be split correctly
        assertEquals("file.txt", FileUtil.extractFileName("file.txt"));
    }

    @Test
    void extractFileNameNoPath() {
        assertEquals("file.txt", FileUtil.extractFileName("file.txt"));
    }

    @Test
    void extractFileNameWithCustomSeparator() {
        // When separator is provided, it uses lastIndexOf with that separator
        assertEquals("file.txt", FileUtil.extractFileName("d:/path/file.txt", "/"));
        assertEquals("file.txt", FileUtil.extractFileName("d:\\path\\file.txt", "\\"));
    }

    @Test
    void extractFileNameWithNullSeparator() {
        assertEquals("file.txt", FileUtil.extractFileName("d:\\path\\file.txt", null));
    }

    @Test
    void getFileExtension() {
        assertEquals(".txt", FileUtil.getFileExtension("file.txt"));
        assertEquals(".java", FileUtil.getFileExtension("Test.java"));
        assertEquals("", FileUtil.getFileExtension("Makefile"));
    }

    @Test
    void getFileNameSuffix() {
        assertEquals("txt", FileUtil.getFileNameSuffix("file.txt"));
        assertEquals("java", FileUtil.getFileNameSuffix("Test.java"));
        assertNull(FileUtil.getFileNameSuffix("Makefile"));
    }

    @Test
    void fileExists() throws IOException {
        File testFile = tempDir.resolve("test.txt").toFile();
        assertFalse(FileUtil.fileExists(testFile.getAbsolutePath()));

        testFile.createNewFile();
        assertTrue(FileUtil.fileExists(testFile.getAbsolutePath()));
    }

    @Test
    void makeDir() {
        String dir = tempDir.resolve("newDir").toString();
        assertTrue(FileUtil.makeDir(dir, false));
        assertTrue(new File(dir).exists());
    }

    @Test
    void makeDirWithParent() {
        String dir = tempDir.resolve("parent").resolve("child").toString();
        assertTrue(FileUtil.makeDir(dir, true));
        assertTrue(new File(dir).exists());
    }

    @Test
    void readFile() throws Exception {
        File testFile = tempDir.resolve("readTest.txt").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Hello, World!");
        }

        String content = FileUtil.readFile(testFile.getAbsolutePath(), "UTF-8");
        assertEquals("Hello, World!", content);
    }

    @Test
    void readFileEmpty() throws Exception {
        File testFile = tempDir.resolve("empty.txt").toFile();
        testFile.createNewFile();

        String content = FileUtil.readFile(testFile.getAbsolutePath(), "UTF-8");
        assertEquals("", content);
    }

    @Test
    void readFileNotFound() {
        assertThrows(Exception.class, () -> {
            FileUtil.readFile("nonexistent.txt", "UTF-8");
        });
    }
}
