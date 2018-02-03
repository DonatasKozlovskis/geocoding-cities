package com.doko.sandbox.input;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileInputService implements InputService {
    private static final Logger logger = LoggerFactory.getLogger(FileInputService.class);

    @Override
    public List<String> getLocations(String source) {
        Path path = Paths.get(source);
        try (Stream<String> stream = Files.lines(path)) {
            return stream.skip(1)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("Error occured reading {}, error: {}", source, e.toString());
        }
        return new ArrayList<>();
    }
}
