package com.ab.lou;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lou main class
 */
public final class Lou {
    static final Logger logger = LoggerFactory.getLogger(Lou.class);
    static boolean hadError = false;

    private Lou() {
    }

    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.err.println("Usage: lou [script]");
            System.exit(64);
        }

        if (args.length == 1) {
            runFile(args[0]);
            return;
        }

        if (args.length == 0) {
            runPrompt();
        }
    }

    private static void runFile(String sourcePath) throws IOException {
        Path path = Paths.get(sourcePath);

        if (!path.toFile().exists()) {
            System.err.println("Location" + sourcePath + "doesn't exist.");
            System.exit(64);
        }

        byte[] bytes = Files.readAllBytes(path);
        run(new String(bytes, Charset.defaultCharset()));

        if (hadError) {
            System.exit(65);
        }
    }

    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader bufferReader = new BufferedReader(input);

        while (true) {
            String line = bufferReader.readLine();

            if (line == null) {
                break;
            }

            if (line.equals("exit")) {
                System.exit(0);
            }

            run(line);
            hadError = false;
        }
    }

    private static void run(String line) {
        Scanner scanner = new Scanner(line);
        List<Token> tokens = scanner.scanTokens();

        logger.debug("{}", tokens);
    }

    static void error(int line, String message) {
        System.err.println("[line " + line + "] Error: " + message);
        hadError = true;
    }
}
