package process.web.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import auth.RegUserWorker;

/**
 * Complete registration action.
 * @author  malczyk.pawel@gmail.com
 */
public class CompleteRegistrationAction extends AbstractProcessAction {

	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(CompleteRegistrationAction.class);
	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -4676123180099283721L;
	
	/**
	 * RegistrationId.
	 * @uml.property  name="registrationId"
	 */
	private String registrationId;
	
	/**
	 * Constructor.
	 */
	public CompleteRegistrationAction() {
		super();
	}
	
	/** {@inheritDoc} */
	@Override
	public String execute() throws Exception {
		boolean success = false;
		try {
			Integer valueOf = Integer.valueOf(registrationId);
			success = locate(RegUserWorker.class).completeRegistration(valueOf);
		} catch (NumberFormatException e) {
			LOG.info("Invalid registration id provided {}", registrationId);
		}
		return success ? "completed" : "notcompleted";
	}

	/**
	 * Getter.
	 * @return  the registrationId
	 * @uml.property  name="registrationId"
	 */
	public String getRegistrationId() {
		return registrationId;
	}

	/**
	 * Setter.
	 * @param registrationId  the registrationId to set
	 * @uml.property  name="registrationId"
	 */
	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

}
