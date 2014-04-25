package core.workers;

import java.util.List;

import org.quartz.JobExecutionContext;

/**
 * Scheduler worker.
 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 *
 */
public interface SchedulerWorker {
	
	/**
	 * Returns job execution contexts.
	 * @return job execution contexts
	 */
	List<JobExecutionContext> getExecutionContexts();
	
	/**
	 * Local Interface.
	 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
	 *
	 */
	public interface SchedulerWorkerLocal extends SchedulerWorker { }
	
	/**
	 * Remote Interface.
	 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
	 *
	 */
	public interface SchedulerWorkerRemote extends SchedulerWorker { }
}
