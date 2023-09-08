import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentHashmap {
    private final Map<String, String> hashmap;
    private final ReentrantLock lock;

    public ConcurrentHashmap() {
        hashmap = new HashMap<>();
        lock = new ReentrantLock();
    }

    public void add(String key, String value) {
        lock.lock();
        try {
            hashmap.put(key, value);
        } finally {
            lock.unlock();
        }
    }

    public String get(String key) {
        lock.lock();
        try {
            return hashmap.get(key);
        } finally {
            lock.unlock();
        }
    }

    public int getSize() {
        lock.lock();
        try {
            return hashmap.size();
        } finally {
            lock.unlock();
        }
    }

    public void remove(String key) {
        lock.lock();
        try {
            hashmap.remove(key);
        } finally {
            lock.unlock();
        }
    }
}
