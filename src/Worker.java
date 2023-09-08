import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

class Worker implements Runnable {
    private final String fileName;
    private final int startLine;
    private final int endLine;
    private final ConcurrentHashmap hashmap;
    private final ReentrantLock fileLock;
    private final int id;

    public Worker(int id, String fileName, int startLine, int endLine, ConcurrentHashmap hashmap, ReentrantLock fileLock) {
        this.id = id;
        this.fileName = fileName;
        this.startLine = startLine;
        this.endLine = endLine;
        this.hashmap = hashmap;
        this.fileLock = fileLock;
    }

    @Override
    public void run() {
        try {
            fileLock.lock();
            try (var reader = new Scanner(new FileReader(fileName))) {
                for (int line = 0; line < startLine; line++)
                {
                    reader.nextLine();
                }

                for (int line = startLine; line < endLine; line++) {
                    var content = reader.nextLine();
                    var tokens = content.split(":");

                    if (tokens.length == 2) {
                        var word = tokens[0].toLowerCase();
                        var meaning = tokens[1];
                        hashmap.add(word, meaning);
                    }
                }
            } catch (IOException e) {
                System.out.println(fileName + " doesn't exist!");
            }
        } finally {
            fileLock.unlock();
        }
    }
}