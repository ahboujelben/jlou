package com.ab.lou;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

public class LouTest {

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideSourceFiles")
    void testLouSourceFiles(String input) throws URISyntaxException, IOException {
        // get Logger
        Logger testLogger = (Logger) LoggerFactory.getLogger(LouTest.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        testLogger.addAppender(listAppender);

        // create a Lou instance
        Lou lou = new Lou(testLogger);

        // run Lou on the test source file
        lou.run(new String(Files.readAllBytes(getResourceFolder().toPath().resolve(input))));

        // collect logger output
        String output = StreamSupport
                .stream(listAppender.list.spliterator(), false)
                .map(ILoggingEvent::getFormattedMessage)
                .collect(Collectors.joining("\n"));

        // expected output
        String expected = new String(
                Files.readAllBytes(getResourceFolder().toPath().resolve(input + ".out")));

        assertEquals(expected, output);
    }

    private static Stream<String> provideSourceFiles() throws URISyntaxException {
        return Stream.of(getResourceFolder().listFiles())
                .filter(file -> file.getName().endsWith(".lou"))
                .map(File::getName);
    }

    private static File getResourceFolder() throws URISyntaxException {
        return new File(LouTest.class.getClassLoader().getResource("sample-code").toURI());
    }
}
