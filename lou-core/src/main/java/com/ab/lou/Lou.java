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
    private final Reporter reporter;
    private final Interpreter interpreter;

    Lou(Logger logger) {
        reporter = new Reporter(logger);
        interpreter = new Interpreter(reporter);
    }

    public static void main(String[] args) throws IOException {
        Lou lou = new Lou(LoggerFactory.getLogger("client"));

        if (args.length > 1) {
            lou.reporter.error("Usage: lou [script]");
            System.exit(64);
        }

        if (args.length == 1) {
            lou.runFile(args[0]);
            return;
        }

        if (args.length == 0) {
            lou.runPrompt();
        }
    }

    private void runFile(String sourcePath) throws IOException {
        Path path = Paths.get(sourcePath);

        if (!path.toFile().exists()) {
            reporter.error("Location " + sourcePath + " doesn't exist.");
            System.exit(64);
        }

        byte[] bytes = Files.readAllBytes(path);
        run(new String(bytes, Charset.defaultCharset()));

        if (reporter.hadError) {
            System.exit(65);
        }

        if (reporter.hadRuntimeError) {
            System.exit(70);
        }
    }

    private void runPrompt() throws IOException {
        reporter.info("Lou Interpreter");

        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader bufferReader = new BufferedReader(input);

        while (true) {
            String line = bufferReader.readLine();

            if (line == null) {
                break;
            }

            run(line);
            reporter.hadError = false;
        }
    }

    void run(String line) {
        // Scan source code tokens
        Scanner scanner = new Scanner(line, reporter);
        List<Token> tokens = scanner.scanTokens();

        // Stop if there was a lexical error.
        if (reporter.hadError)
            return;

        // Parse tokens
        List<Stmt> statements = new Parser(tokens, reporter).parse();

        // Stop if there was a syntax error.
        if (reporter.hadError) {
            return;
        }

        // Resolve syntax
        Resolver resolver = new Resolver(interpreter, reporter);
        resolver.resolve(statements);

        // Stop if the semantic analysis fails
        if (reporter.hadError) {
            return;
        }

        // Interpret
        interpreter.interpret(statements);
    }
}
