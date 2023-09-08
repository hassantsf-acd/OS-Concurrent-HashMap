import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    private static final String[] FILE_NAMES = { "src/dictionary_1.txt", "src/dictionary_2.txt" };
    private static final ReentrantLock[] FILE_LOCKS = { new ReentrantLock(), new ReentrantLock() };
    private static final int FILE_NUMBER = 8_000;

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        System.out.print("Enter Number of Workers = ");
        int numWorkers = scanner.nextInt();
        var hashmap = new ConcurrentHashmap();

        var firstFileWorkers = new Thread[numWorkers];
        var secondFileWorkers = new Thread[numWorkers];

        for (int i = 0; i < numWorkers; i++) {
            var length = FILE_NUMBER / numWorkers;
            var startLine = i * (length);
            var endLine = startLine + length;

            firstFileWorkers[i] = new Thread(new Worker(i, FILE_NAMES[0], startLine, endLine, hashmap, FILE_LOCKS[0]));
            secondFileWorkers[i] = new Thread(new Worker(i, FILE_NAMES[1], startLine, endLine, hashmap, FILE_LOCKS[1]));
            firstFileWorkers[i].start();
            secondFileWorkers[i].start();
        }

        var workers = new Thread[firstFileWorkers.length + secondFileWorkers.length];
        System.arraycopy(firstFileWorkers, 0, workers, 0, firstFileWorkers.length);
        System.arraycopy(secondFileWorkers, 0, workers, firstFileWorkers.length, secondFileWorkers.length);

        try {
            for (Thread worker : workers) {
                worker.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("File reading completed.");

        var reader = new BufferedReader(new InputStreamReader(System.in));
        String input;

        try {
            while (true) {
                System.out.print("Enter a word (or 'exit' to quit) = ");
                input = reader.readLine().trim();

                if (input.equalsIgnoreCase("exit")) {
                    break;
                }

                var meaning = hashmap.get(input.toLowerCase());
                if (meaning != null) {
                    System.out.println("Meaning(s): " + meaning);
                } else {
                    System.out.println("Word not found.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
