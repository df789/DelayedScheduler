package delayqueue;

import java.util.concurrent.TimeUnit;

public class Tester {

	private static long t0 = System.currentTimeMillis();

	public static void main(String[] args) throws InterruptedException {
		DelayedScheduler scheduler = new DelayedScheduler();
		scheduler.schedule(task("Low 1"), 1, TimeUnit.SECONDS, Priority.LOW);
		scheduler.schedule(task("Low 2"), 2, TimeUnit.SECONDS, Priority.LOW);
		scheduler.schedule(task("Low 3"), 3, TimeUnit.SECONDS, Priority.LOW);
		scheduler.schedule(task("Medium 1"), 1, TimeUnit.SECONDS,
				Priority.MEDIUM);
		scheduler.schedule(task("Medium 2"), 2, TimeUnit.SECONDS,
				Priority.MEDIUM);
		scheduler.schedule(task("Medium 3"), 3, TimeUnit.SECONDS,
				Priority.MEDIUM);
		scheduler.schedule(task("High 1"), 1, TimeUnit.SECONDS, Priority.HIGH);
		scheduler.schedule(task("High 2"), 2, TimeUnit.SECONDS, Priority.HIGH);
		scheduler.schedule(task("High 3"), 3, TimeUnit.SECONDS, Priority.HIGH);

		scheduler.start();
		scheduler.stop();
	}

	private static Runnable task(String name) {
		return () -> {
			long start = System.currentTimeMillis() - t0;
			System.out.println(name + " started at " + start + "ms");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			long end = System.currentTimeMillis() - t0;
			System.out.println("  " + name + " ended at " + end + "ms");
		};
	}

}
