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
		// Because we don't call ThreadPoolExecutor.execute(), it
		//   is unaware of the first task waiting to launch
		// - the other way to start a task is to add some threads
		executor.setCorePoolSize(3);
	}

	public void stop() {
		executor.shutdown();
	}
}
