package delayqueue;

import java.util.concurrent.ThreadPoolExecutor;

public enum Priority {
	LOW {
		@Override
		boolean isReady(ThreadPoolExecutor executor) {
			return executor.getActiveCount() == 0;
		}
	},
	MEDIUM {
		@Override
		boolean isReady(ThreadPoolExecutor executor) {
			return executor.getActiveCount() <= 1;
		}
	},
	HIGH {
		@Override
		boolean isReady(ThreadPoolExecutor executor) {
			return true;
		}
	};

	abstract boolean isReady(ThreadPoolExecutor executor);
}
