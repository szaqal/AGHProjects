package process.web.actions.ajax;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import process.web.actions.AbstractProcessAction;

import computation.model.Computation;
import computation.model.ComputationTask;
import computation.worker.ComputationWorker;

import core.model.AbstractEntity;

/**
 * Details about computation.
 * @author  malczyk.pawel@gmail.com
 */
public class AjaxProcessDetailAction extends AbstractProcessAction {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -8795679038324098002L;
	
	/**
	 * {@link ComputationWorker}  .
	 * @uml.property  name="computationWorker"
	 * @uml.associationEnd  
	 */
	private ComputationWorker computationWorker;
	
	/**
	 * {@link Computation}  .
	 * @uml.property  name="computation"
	 * @uml.associationEnd  
	 */
	private ComputationDto computation;
	
	/**
	 * Id of computation which details are retrieved.
	 * @uml.property  name="computationId"
	 */
	private String computationId;
	
	
	/**
	 * Constructor.
	 */
	public AjaxProcessDetailAction() {
		super();
	}
	
	/**{@inheritDoc}*/
	@Override
	public String execute() throws Exception {
		computationWorker = locate(ComputationWorker.class);
		Computation computation = computationWorker.retrieveComputation(Integer.valueOf(computationId));
		this.computation = new ComputationDto(computation, computationWorker.retieveTasksForComputation(Integer.valueOf(computationId)));
		return SUCCESS;
	}

	/**
	 * Getter.
	 * @return  the computationId
	 * @uml.property  name="computationId"
	 */
	public String getComputationId() {
		return computationId;
	}

	/**
	 * Setter.
	 * @param computationId  the computationId to set
	 * @uml.property  name="computationId"
	 */
	public void setComputationId(String computationId) {
		this.computationId = computationId;
	}

	/**
	 * Getter.
	 * @return  the computation
	 * @uml.property  name="computation"
	 */
	public ComputationDto getComputation() {
		return computation;
	}

	/**
	 * Setter.
	 * @param computation  the computation to set
	 * @uml.property  name="computation"
	 */
	public void setComputation(ComputationDto computation) {
		this.computation = computation;
	}
	
	/**
	 * Computation data transfer object.
	 * @author  malczyk.pawel@gmail.com
	 */
	public class ComputationDto extends AbstractEntity {
		
		/**
		 * {@link Computation}  .
		 * @uml.property  name="computation"
		 * @uml.associationEnd  
		 */
		private Computation computation;
		
		/**
		 * Computation tasks.
		 */
		private Collection<ComputationTask> compTasks;
		
		/**
		 * Constructor.
		 * @param computation {@link Computation}
		 * @param compTasks collection of {@link ComputationTask}
		 */
		public ComputationDto(Computation computation, Collection<ComputationTask> compTasks) {
			this.computation = computation;
			this.compTasks = compTasks;
		}

		/** {@inheritDoc} */
		@Override
		public String[] getJsonData() {
			List<String> data = new ArrayList<String>();
			data.add(computation.getComputationId());
			data.add(computation.getDescription());
			for(ComputationTask task : compTasks) {
				data.add(task.getTaskName());
				data.add(task.getClassName());
			}
			return data.toArray(new String[data.size()]);
		}

		/** {@inheritDoc} */
		@Override
		public int getUniqueId() {
			return computation.getUniqueId();
		}

		/** {@inheritDoc} */
		@Override
		public void setUniqueId(int uniqueId) {
			computation.setUniqueId(uniqueId);
		}

		/**
		 * Getter.
		 * @return  the computation
		 * @uml.property  name="computation"
		 */
		public Computation getComputation() {
			return computation;
		}
		
	}

}
