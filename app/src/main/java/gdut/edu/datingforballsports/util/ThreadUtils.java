package gdut.edu.datingforballsports.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.widget.Toast;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import gdut.edu.datingforballsports.view.activity.LoginActivity;

public class ThreadUtils {
    private final int CPU_COUNT = Runtime.getRuntime().availableProcessors(); //cup内核数
    private final int DEAFULT_THREAD_COUNT = CPU_COUNT + 3; //默认核心线程数
    private final int KEEP_ALIVE = 3; //空线程alive时间
    private ExecutorService mThreadPool; //线程池
    private ThreadFactory mBackgroundThreadFactory = new PriorityThreadFactory(Process.THREAD_PRIORITY_BACKGROUND);
    private InternalHandler mHandler;
    private static ThreadUtils instance;

    public static ThreadUtils getInstance() {
        if (instance == null) {
            synchronized (ThreadUtils.class) {
                if (instance == null) {
                    instance = new ThreadUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 构造函数
     */
    private ThreadUtils() {
        // 创建线程池
        mThreadPool = new ThreadPoolExecutor(DEAFULT_THREAD_COUNT, DEAFULT_THREAD_COUNT, KEEP_ALIVE,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10000),
                mBackgroundThreadFactory, new ThreadPoolExecutor.DiscardOldestPolicy());

    }

    /**
     * 异步线程执行任务
     *
     * @param runnable
     */
    public static void execute(Runnable runnable) {
        ThreadUtils.getInstance().executeRunnable(runnable);
    }
    private void executeRunnable(Runnable runnable) {
        /*if (Looper.myLooper() == Looper.getMainLooper()) {
            mThreadPool.execute(runnable);
        } else {
            Looper.prepare();
            getHandler().post(runnable);
            Looper.loop();
        }*/
        getHandler().post(new Runnable() {
            @Override
            public void run() {
                mThreadPool.execute(runnable);
            }
        });
    }
    /**
     * 异步线程执行延迟任务
     *
     * @param runnable
     * @param delayedTime
     */
    public static void executeDelayed(Runnable runnable, long delayedTime) {
        ThreadUtils.getInstance().executeRunnableDelayed(runnable, delayedTime);
    }
    private void executeRunnableDelayed(final Runnable runnable, long delayedTime) {
        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mThreadPool.execute(runnable);
            }
        }, delayedTime);
    }

    public static void executeDelayedToUI(Runnable runnable, long delayedTime) {
        ThreadUtils.getInstance().executeRunnableDelayedToUI(runnable, delayedTime);
    }
    private void executeRunnableDelayedToUI(final Runnable runnable, long delayedTime) {
        getHandler().postDelayed(runnable, delayedTime);
    }

    private Handler getHandler() {
        synchronized (this) {
            if (mHandler == null) {
                mHandler = new InternalHandler();
            }
            return mHandler;
        }
    }

    private static class InternalHandler extends Handler {
        public InternalHandler() {
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(Message msg) {

        }
    }



    public static class PriorityThreadFactory implements ThreadFactory {
        private final AtomicInteger mCount = new AtomicInteger(1);
        private final int mThreadPriority;

        public PriorityThreadFactory(int threadPriority) {
            mThreadPriority = threadPriority;
        }

        @Override
        public Thread newThread(final Runnable runnable) {
            Runnable priorityRunnable = new Runnable() {
                @Override
                public void run() {
                    Process.setThreadPriority(mThreadPriority);
                    runnable.run();
                }
            };
            return new Thread(priorityRunnable, "AsyncThreadTask #" + mCount.getAndIncrement());
        }



    }
}
