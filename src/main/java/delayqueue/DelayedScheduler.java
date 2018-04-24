package delayqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DelayedScheduler {
	private final ThreadPoolExecutor executor;

	private final DelayQueue<DelayedRunnable> delayQueue = new DelayQueue<>();

	public DelayedScheduler() {
		BlockingQueue<Runnable> blockingQueue = new BlockingDelayQueue(
				delayQueue);
		this.executor = new ThreadPoolExecutor(0, 3, 0, TimeUnit.MILLISECONDS,
				blockingQueue);
	}

	public void schedule(Runnable command, long delay, TimeUnit unit,
			Priority priority) {
		long time = System.currentTimeMillis() + unit.toMillis(delay);
		DelayedRunnable delayedRunnable = new DelayedRunnable(command, time, priority, executor);
		delayQueue.offer(delayedRunnable);
	}

	public void start() {
		executor.setCorePoolSize(3);
	}

	public void stop() {
		executor.shutdown();
		//executor.awaitTermination(2000, TimeUnit.MILLISECONDS);
	}
}
