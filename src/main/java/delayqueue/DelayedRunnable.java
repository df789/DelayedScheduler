package delayqueue;

import java.util.Comparator;
import java.util.concurrent.Delayed;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DelayedRunnable implements Delayed {
	private static final Comparator<DelayedRunnable> comparator;
	static {
		Comparator<DelayedRunnable> lowFirst = Comparator
			.comparing(d -> d.priority);
		comparator = lowFirst.reversed().thenComparing(d -> d.time);
	}

	private final Runnable command;

	private final long time;

	private final Priority priority;

	private final ThreadPoolExecutor executor;

	DelayedRunnable(Runnable command, long time, Priority priority,
			ThreadPoolExecutor executor) {
		this.command = command;
		this.time = time;
		this.priority = priority;
		this.executor = executor;
	}

	@Override
	public int compareTo(Delayed o) {
		DelayedRunnable other = (DelayedRunnable) o;
		// Ready to go wins over not ready to go
		boolean ready1 = this.getDelay(TimeUnit.MILLISECONDS) <= 0;
		boolean ready2 = other.getDelay(TimeUnit.MILLISECONDS) <= 0;
		if (ready1 != ready2)
			return ready1 ? -1 : 1;
		// Then by priority, then by scheduled time
		return comparator.compare(this, other);
	}

	@Override
	public long getDelay(TimeUnit unit) {
		if (!priority.isReady(executor))
			return 1000;
		return time - System.currentTimeMillis();
	}

	Runnable getCommand() {
		return command;
	}
}
