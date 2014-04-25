package computation.worker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import computation.model.ComputationTask;

/**
 * Util helper class for ComputationWorker.
 * @author malczyk.pawel@gmail.com
 *
 */
public class ComputationPerformerUtil {
	
	/**
	 * Default constructor.
	 */
	public ComputationPerformerUtil() {
	}
	
	/**
	 * Finds start task.
	 * @param computationTasks collection of all tasks in process.
	 * @return fist task.
	 */
	ComputationTask findFirst(Collection<ComputationTask> computationTasks) {

		for(ComputationTask task : computationTasks) {
			if(task.getStart()) {
				return task;
			}
		}
		return null;
	}
	
	/**
	 * Serializes result map to be stored in db.
	 * @param result this will be serialized
	 * @return array of raw bytes of serialized result object.
	 */
	byte [] serialize(Map<String, Serializable> result) {
		ByteArrayOutputStream byteStream = null;
		ObjectOutputStream ous = null;
		
		try {
			byteStream = new ByteArrayOutputStream();
			ous = new ObjectOutputStream(byteStream);
			ous.writeObject(result);
			byte [] serialized = byteStream.toByteArray();
			return serialized;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(byteStream != null) {
				try {
					byteStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(ous != null) {
				try {
					ous.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
