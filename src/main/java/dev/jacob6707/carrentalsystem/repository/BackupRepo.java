package dev.jacob6707.carrentalsystem.repository;

public class BackupRepo {
    public static final Path BACKUP_FILE = Path.of("data/backup.bin");
    private final List<ArrayListBackedRepository<?>> repositories;

    /**
     * Creates a new backup repository.
     *
     * @param repositories The repositories to backup.
     */
    public BackupRepository(List<ArrayListBackedRepository<?>> repositories) {
        this.repositories = repositories;
    }

    /**
     * Backs up all repositories to a file.
     */
    public void backup() {
        if (!Files.exists(BACKUP_FILE.getParent())) {
            try {
                Files.createDirectories(BACKUP_FILE.getParent());
            } catch (IOException exception) {
                throw new IllegalStateException("Failed to create directory: " + BACKUP_FILE.getParent(), exception);
            }
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(BACKUP_FILE.toFile()))) {
            for (ArrayListBackedRepository<?> repository : repositories) {
                List<?> entities = repository.findAll();
                out.writeObject(entities);
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to backup repositories to file: " + BACKUP_FILE, exception);
        }
    }

    // This is slightly unsafe but will not fail
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void restore() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(BACKUP_FILE.toFile()))) {
            for (ArrayListBackedRepository<?> repository : repositories) {
                repository.clear();
                List entities = (List) in.readObject();
                repository.addAll(entities);
            }
        } catch (ClassNotFoundException | IOException exception) {
            throw new IllegalStateException("Failed to restore repositories from file: " + BACKUP_FILE, exception);
        }
    }
}
