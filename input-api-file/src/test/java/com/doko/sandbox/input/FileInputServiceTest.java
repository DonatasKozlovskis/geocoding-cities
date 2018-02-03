package com.doko.sandbox.input;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Stream;

public class FileInputServiceTest {
    private static final String FILE_NAME = "in.csv";
    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void getLocations_fileDoesNotExist_emptyList() {

        FileInputService fileInputService = new FileInputService();

        List<String> strings = fileInputService.getLocations(FILE_NAME);

        Assert.assertEquals(0, strings.size());
    }

    @Test
    public void getLocations_fileExists_allButFirstLinesReturned() throws Exception {
        //arrange
        File file = folder.newFile(FILE_NAME);
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
            Stream.of("First", "Second", "Third").forEach(writer::println);
        }

        FileInputService fileInputService = new FileInputService();
        //act
        List<String> strings = fileInputService.getLocations(file.getAbsolutePath());
        //Assert
        Assert.assertEquals(2, strings.size());
        Assert.assertEquals("Second", strings.get(0));
        Assert.assertEquals("Third", strings.get(1));
        Assert.assertFalse(strings.contains("First"));
    }
}