package delayqueue;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BlockingDelayQueue extends AbstractQueue<Runnable>
		implements BlockingQueue<Runnable> {

	private final DelayQueue<DelayedRunnable> delayQueue;

	public BlockingDelayQueue(DelayQueue<DelayedRunnable> delayQueue) {
		this.delayQueue = delayQueue;
	}

	@Override
	public boolean isEmpty() {
		return delayQueue.isEmpty();
	}

	@Override
	public int size() {
		return delayQueue.size();
	}

	@Override
	public Runnable poll() {
		throw unsupported();
	}

	@Override
	public Runnable poll(long timeout, TimeUnit unit)
			throws InterruptedException {
		DelayedRunnable delayedRunnable = delayQueue.poll(timeout, unit);

		if (delayedRunnable == null)
			return null;
		return delayedRunnable.getCommand();
	}

	@Override
	public Runnable take() throws InterruptedException {
		return delayQueue.take().getCommand();
	}

	@Override
	public Runnable peek() {
		throw unsupported();
	}

	@Override
	public boolean offer(Runnable runnable) {
		// Attempt to add a runnable directly to the ThreadPoolExecutor
		throw new UnsupportedOperationException("Please use " +
				DelayedScheduler.class.getSimpleName() + ".schedule()");
	}

	@Override
	public void put(Runnable runnable) throws InterruptedException {
		throw unsupported();
	}

	@Override
	public boolean offer(Runnable runnable, long timeout, TimeUnit unit)
			throws InterruptedException {
		throw unsupported();
	}

	@Override
	public int remainingCapacity() {
		throw unsupported();
	}

	@Override
	public int drainTo(Collection<? super Runnable> c) {
		throw unsupported();
	}

	@Override
	public int drainTo(Collection<? super Runnable> c, int maxElements) {
		throw unsupported();
	}

	@Override
	public Iterator<Runnable> iterator() {
		throw unsupported();
	}

	private UnsupportedOperationException unsupported() {
		throw new UnsupportedOperationException(
				"Not needed for use in a " +
						ThreadPoolExecutor.class.getSimpleName());
	}
}
