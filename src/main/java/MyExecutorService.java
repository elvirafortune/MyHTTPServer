/**
 * Created by Elvira on 22.09.14.
 */

import java.util.LinkedList;

public class MyExecutorService {
    private final int nThreads;
    private final PoolThread[] threads;
    private final LinkedList list;

    public MyExecutorService(int nThreads) {
        this.nThreads = nThreads;
        list = new LinkedList<Runnable>();
        threads = new PoolThread[nThreads];

        for (int i=0; i<nThreads; i++) {
            threads[i] = new PoolThread();
            threads[i].start();
        }
    }

    public void execute(Runnable r) {
        synchronized(list) {
            list.addLast(r);
            list.notify();
        }
    }

    private class PoolThread extends Thread {
        public void run() {
            Runnable r;
            while (true) {
                synchronized(list) {
                    while (list.isEmpty()) {
                        try{
                            list.wait();
                        } catch (InterruptedException ignored){}
                    }

                    r = (Runnable) list.removeFirst();
                }

                try {
                    r.run();
                } catch (RuntimeException e) {
                    // You might want to log something here
                }
            }
        }
    }
}
