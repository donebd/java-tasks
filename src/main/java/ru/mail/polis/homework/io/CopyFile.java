package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;

public class CopyFile {

    public final static String ERROR = "Error";
    public final static String COMPLETED = "Done";
    private final static int BUFFER_SIZE = 1024;

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * 3 балла
     */
    public static String copyFiles(String pathFrom, String pathTo) {
        Path from = Paths.get(pathFrom);
        if (!Files.exists(from)) {
            return ERROR;
        }
        Path to = Paths.get(pathTo);
        try {
            if (Files.isRegularFile(from)) {
                createPathIfNotExist(to.getParent());
            } else {
                createPathIfNotExist(to);
            }
            Files.walkFileTree(from, createCopyVisitor(from, to, new byte[BUFFER_SIZE]));
        } catch (IOException e) {
            e.printStackTrace();
            return ERROR;
        }
        return COMPLETED;
    }

    private static void createPathIfNotExist(Path dir) throws IOException {
        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
        }
    }

    private static FileVisitor<Path> createCopyVisitor(Path from, Path to, byte[] buffer) {
        return new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                try (InputStream input = Files.newInputStream(file);
                     OutputStream output = Files.newOutputStream(to.resolve(from.relativize(file)))) {
                    int readSize;
                    while ((readSize = input.read(buffer)) > 0) {
                        output.write(buffer, 0, readSize);
                    }
                }
                return CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path current = to.resolve(from.relativize(dir));
                if (Files.notExists(current)) {
                        Files.createDirectory(current);
                }
                return CONTINUE;
            }
        };
    }

}