package process.web.actions.edit;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import process.web.actions.AbstractEditAction;
import process.web.actions.ActionHandler;

import computation.model.ComputationConfiguration;
import computation.model.ComputingNode;
import computation.worker.ComputationConfgurationWorker;
import computation.worker.NodesWorker;

import core.utils.StringUtils;
import core.workers.SftpWorker;

/**
 * Edit process action.
 * @author  malczyk.pawel@gmail.com
 */
public class EditProcessConfiguration extends AbstractEditAction<ComputationConfiguration> {
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(EditProcessConfiguration.class);

	
	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 28103623882653332L;
	
	/**
	 * Handler map.
	 * @uml.property  name="handlers"
	 */
	private Map<String, ActionHandler> handlers;
	

	/**
	 * File.
	 * @uml.property  name="file"
	 */
	private File file;
	
	/**
	 * Content type.
	 * @uml.property  name="fileContentType"
	 */
	private String fileContentType;
	
	/**
	 * fileName.
	 * @uml.property  name="fileFileName"
	 */
	private String fileFileName;


	/**
	 * Constructor.
	 */
	public EditProcessConfiguration() {
		super();
		handlers = new HashMap<String, ActionHandler>();
		handlers.put(NEW, new NewHandler());
		handlers.put(SAVE, new SaveHandler());
	}
	
	/** {@inheritDoc} */
	@Override
	protected String defaultOperation() {
		return NEW;
	}

	/** {@inheritDoc} */
	@Override
	public String doEdit() {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String doSave() {
		try {
			LOG.debug("Uploading  {}", file + StringUtils.WHITESPACE + fileFileName + StringUtils.WHITESPACE + fileContentType );
			byte [] content = FileUtils.readFileToByteArray(file);
			locate(ComputationConfgurationWorker.class).store(content, fileFileName);
			
			Collection<ComputingNode> nodes = locate(NodesWorker.class).retrieveComputingNodes();
			SftpWorker sftpWoker = locate(SftpWorker.class);
			for(ComputingNode computingNode: nodes) {
				try {
					sftpWoker.uploadFile(content, computingNode.getInetAddr(), computingNode.getConfLocation(), 
							computingNode.getSshUser(), computingNode.getSshPasswd(), fileFileName);
				} catch (Exception e) {
					LOG.warn("Exception ", e);
				}
			}
		} catch (IOException e) {
			LOG.warn("Error uploading file", e);
		}
		return SAVE;
	}

	/**
	 * {@inheritDoc} 
	 * @uml.property  name="handlers"
	 */
	@Override
	protected Map<String, ActionHandler> getHandlers() {
		return handlers;
	}

	/** {@inheritDoc} */
	@Override
	public ComputationConfiguration load(int uniqueId) {
		return null;
	}
	
	
	/**
	 * Handler new group event.
	 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
	 *
	 */
	private class NewHandler implements ActionHandler {
		
		/**
		 * 
		 * Constructor.
		 */
		public NewHandler() { }

		/** {@inheritDoc} */
		@Override
		public String handle() {
			return doNew();
		}
		
	}
	
	
	/**
	 * Save handler.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	private class SaveHandler implements ActionHandler {
		
		/**
		 * 
		 * Constructor.
		 */
		public SaveHandler() {
			super();
		}

		/** {@inheritDoc} */
		@Override
		public String handle() {
			return doSave();
		}
	}


	/**
	 * Getter.
	 * @return  the file
	 * @uml.property  name="file"
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Setter.
	 * @param file  the file to set
	 * @uml.property  name="file"
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * Setter.
	 * @param handlers  the handlers to set
	 * @uml.property  name="handlers"
	 */
	public void setHandlers(Map<String, ActionHandler> handlers) {
		this.handlers = handlers;
	}

	/**
	 * Getter.
	 * @return  the fileContentType
	 * @uml.property  name="fileContentType"
	 */
	public String getFileContentType() {
		return fileContentType;
	}

	/**
	 * Setter.
	 * @param fileContentType  the fileContentType to set
	 * @uml.property  name="fileContentType"
	 */
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	/**
	 * Getter.
	 * @return  the fileFileName
	 * @uml.property  name="fileFileName"
	 */
	public String getFileFileName() {
		return fileFileName;
	}

	/**
	 * Setter.
	 * @param fileFileName  the fileFileName to set
	 * @uml.property  name="fileFileName"
	 */
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

}
