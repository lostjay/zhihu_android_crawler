package xyz.lostjay.spider.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileManagerTest {

    @TempDir
    Path tempDir;
    private FileManager fileManager;
    private Path testFile;

    @BeforeEach
    void setUp() throws IOException {
        testFile = tempDir.resolve("test.txt");
        Files.writeString(testFile, "test content");
        fileManager = new FileManager(testFile.toString());
    }

    @Test
    void testGetFilePath() {
        assertEquals(testFile.toString(), fileManager.getFilePath());
    }

    @Test
    void testGetFileName() {
        assertEquals("test.txt", fileManager.getFileName());
    }

    @Test
    void testGetDirName() {
        assertEquals(tempDir.toString(), fileManager.getDirName());
    }

    @Test
    void testReadString() throws IOException {
        assertEquals("test content", fileManager.readString());
    }

    @Test
    void testReadBytes() throws IOException {
        assertArrayEquals("test content".getBytes(), fileManager.readBytes());
    }

    @Test
    void testWriteString() throws IOException {
        String newContent = "new content";
        fileManager.writeString(newContent);
        assertEquals(newContent, Files.readString(testFile));
    }

    @Test
    void testWriteBytes() throws IOException {
        byte[] newContent = "new content".getBytes();
        fileManager.writeBytes(newContent);
        assertArrayEquals(newContent, Files.readAllBytes(testFile));
    }

    @Test
    void testReadJson() throws IOException {
        // Create a JSON file
        Path jsonFile = tempDir.resolve("test.json");
        Files.writeString(jsonFile, "{\"key\": \"value\"}");
        FileManager jsonManager = new FileManager(jsonFile.toString());

        Map<String, Object> result = jsonManager.readJson();
        assertEquals("value", result.get("key"));
    }
} 