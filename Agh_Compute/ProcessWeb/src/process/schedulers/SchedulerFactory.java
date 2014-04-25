package process.schedulers;

import java.text.ParseException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A factory of schedulers.
 * 
 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 * 
 */
public class SchedulerFactory implements ObjectFactory {

	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(SchedulerFactory.class);
	
	/** Constant. */
	private static final String JOB_ID = "server_data_job";

	/** Scheduler. */
	private static Scheduler scheduler;
	
	/** Constructor. */
	public SchedulerFactory() { }

	/** {@inheritDoc} */
	@Override
	public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {
		return scheduler;
	}
	
	/**
	 * Returns instance.
	 * @return scheduler
	 */
	public static Scheduler getInstance() {
		return scheduler;
	}

	static {
		try {
			StdSchedulerFactory factory = new StdSchedulerFactory("quartz.properties");
			scheduler = factory.getScheduler();
			
			JobDetail jobDetail = new JobDetail(JOB_ID , Scheduler.DEFAULT_GROUP, ServersDataJob.class);
			CronTrigger trigger = new CronTrigger(JOB_ID , Scheduler.DEFAULT_GROUP, "0/20 * * * * ?" );
			scheduler.scheduleJob(jobDetail, trigger);
			
			scheduler.start();
			LOG.info("Scheduler started");
		} catch (SchedulerException se) {
			LOG.error("Cannot initialize job scheduler : {}\n", se.getMessage());
			LOG.error("Cannot initialize job scheduler", se);
			scheduler = null;
		} catch (ParseException e) {
			LOG.error("Parse Exception {}", e.getMessage());
		}
	}

}
