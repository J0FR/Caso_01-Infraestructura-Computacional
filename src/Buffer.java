import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private final int n;
    private ArrayList<Boolean> buff;
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public Buffer(int n) {
        this.n = n;
        this.buff = new ArrayList<>(n);
    }

    public void almacenar(boolean value) {
        lock.lock();
        try {
            while (buff.size() == n) {
                notFull.await();
            }
            buff.add(value);
            notEmpty.signal();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public Boolean retirar() {
        lock.lock();
        try {
            while (buff.size() == 0) {
                notEmpty.await();
            }
            Boolean value = buff.remove(0);
            notFull.signal();
            return value;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } finally {
            lock.unlock();
        }
    }

    public int getN() {
        return n;
    }
}
