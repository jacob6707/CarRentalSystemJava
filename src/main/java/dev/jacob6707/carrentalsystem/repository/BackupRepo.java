package dev.jacob6707.carrentalsystem.repository;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class BackupRepo {
    public static final Path BACKUP_FILE = Path.of("data/backup.bin");
    private final List<JsonRepository<?>> repositories;

    public BackupRepo(List<JsonRepository<?>> repositories) {
        this.repositories = repositories;
    }

    public void backup() {
        if (!Files.exists(BACKUP_FILE.getParent())) {
            try {
                Files.createDirectories(BACKUP_FILE.getParent());
            } catch (IOException exception) {
                throw new IllegalStateException("Failed to create directory: " + BACKUP_FILE.getParent(), exception);
            }
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(BACKUP_FILE.toFile()))) {
            for (JsonRepository<?> repository : repositories) {
                List<?> entities = repository.findAll();
                out.writeObject(entities);
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to backup repositories to file: " + BACKUP_FILE, exception);
        }
    }

    public void restore() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(BACKUP_FILE.toFile()))) {
            for (JsonRepository<?> repository : repositories) {
                repository.clear();
                List entities = (List) in.readObject();
                repository.saveAll(entities);
            }
        } catch (ClassNotFoundException | IOException exception) {
            throw new IllegalStateException("Failed to restore repositories from file: " + BACKUP_FILE, exception);
        }
    }
}
