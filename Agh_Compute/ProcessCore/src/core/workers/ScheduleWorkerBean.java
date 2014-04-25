package core.workers;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.utils.ListUtils;
import core.workers.SchedulerWorker.SchedulerWorkerLocal;
import core.workers.SchedulerWorker.SchedulerWorkerRemote;

/**
 * Worker Implementation.
 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 *
 */
@Stateless
@Local(SchedulerWorkerLocal.class)
@Remote(SchedulerWorkerRemote.class)
public class ScheduleWorkerBean implements SchedulerWorker {	
	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(ScheduleWorkerBean.class);
	
	/** Scheduler. */
	@Resource(mappedName="JobScheduler")
	private Scheduler jobScheduler;
	
	/** Scheduled jobs. */
	private List<JobExecutionContext> jobs;
	
	/**
	 * Constructor.
	 */
	public ScheduleWorkerBean() { 
		try {
			jobs = ListUtils.convertRawListToGenericList(JobExecutionContext.class, jobScheduler.getCurrentlyExecutingJobs());
			if (jobs != null && !jobs.isEmpty()) {
				for (JobExecutionContext job : jobs) {
					LOG.debug("Job : "+job.getJobDetail().getFullName());
				}
			} else {
				LOG.warn("No Jobs");
			}
		} catch (SchedulerException e) {
			LOG.warn("Scheduler exception {}", e.getMessage());
		}
		
	}

	/** {@inheritDoc} */
	@Override
	public List<JobExecutionContext> getExecutionContexts() {
		return jobs;
	}


}
