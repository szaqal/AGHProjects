package core.messaging.workers;

import java.util.Date;

/**
 * Email worker interface.
 * @author malczyk.pawel@gmail.com
 *
 */
public interface MailerWorker {
	
	/**
	 * Message type.
	 */
	String COMPUTATION_COMPLETED = "computationCompleted";
	
	/**
	 * Message type.
	 */
	String COMPUTATION_FAILED="computationFailed";

	
	/**
	 * Message type.
	 */
	String COMPLETE_REGISTRATION = "completeRegistration";
	
	
	/**
	 * Constant.
	 */
	String MESSAGE_TYPE = "message_type";

	/**
	 * Sends message that computation completed.
	 * @param computationResultId computation result identifier used to create URLs.
	 * @param email where result link will be send
	 * @param startDate TODO
	 * @param endDate TODO
	 */
	void sendComputationCompleted(long computationResultId, String email, Date startDate, Date endDate);
	
	/**
	 * Sends message for the user the is being registered.
	 * @param registrationId registration entry id.
	 * @param email registering user email.
	 */
	void sendCompleteRegistration(long registrationId, String email);
	
	
	/**
	 * Sends email saying it was impossible to run computation properly.
	 * @param email user's email tak started computation.
	 * @param messageContent text message content
	 */
	void sendComputationFailed(String email, String messageContent);
	
	
	/**
	 * Loval inerface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface MailerWorkerLocal extends MailerWorker { }
	
	
	
	/**
	 * Remote interface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface MailerWorkerRemote extends MailerWorker { }
}