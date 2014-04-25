package process.web.actions.edit;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import process.web.actions.AbstractEditAction;
import process.web.actions.ActionHandler;
import core.model.XslTransform;
import core.utils.StringUtils;
import core.workers.TransformWorker;

/**
 * Edit transform action.
 * @author  malczyk.pawel@gmail.com
 */
public class EditTransformAction extends AbstractEditAction<XslTransform> {
	
	/** Serial. */
	private static final long serialVersionUID = -634950489573384216L;
	
	/**
	 * Handler map.
	 * @uml.property  name="handlers"
	 */
	private Map<String, ActionHandler> handlers;
	
	/**
	 * Title.
	 * @uml.property  name="title"
	 */
	private String title;
	
	/**
	 * File.
	 * @uml.property  name="file"
	 */
	private File file;
	
	/**
	 * Result mime type.
	 * @uml.property  name="resultMime"
	 */
	private String resultMime;
	
	/**
	 * Result extension.
	 * @uml.property  name="extension"
	 */
	private String extension;
	
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
	public EditTransformAction() {
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

	@Override
	public String doEdit() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/** {@inheritDoc} */
	@Override
	public void validate() {
		if(SAVE.equals(getOperation())) {
			
			String messageKey = "common.requiredField";
			if(StringUtils.isEmpty(title)) {
				addFieldError("title", getText(messageKey));
			}
			
			if(StringUtils.isEmpty(extension)) {
				addFieldError("extension", getText(messageKey));
			}
			
			if(StringUtils.isEmpty(resultMime)) {
				addFieldError("resultMime", getText(messageKey));
			}
			
			if(fileFileName==null || !"xsl".equals(fileFileName.substring(fileFileName.lastIndexOf(".")+1))){
				addFieldError("file", getText("transform.invaldfile"));
			}
			
		}
	}

	@Override
	public String doSave() {
		try {
			LOG.debug("Uploading  {}", file + StringUtils.WHITESPACE + fileFileName + StringUtils.WHITESPACE + fileContentType );
			byte [] content = FileUtils.readFileToByteArray(file);
			locate(TransformWorker.class).store(title, fileFileName, content, resultMime, extension);
		} catch (IOException e) {
			LOG.warn("Error uploading file", e);
		}
		return SAVE;
	}

	/**
	 * Returns map of action handlers.
	 * @uml.property  name="handlers"
	 * @return map of action handlers
	 */
	@Override
	protected Map<String, ActionHandler> getHandlers() {
		return handlers;
	}

	/** {@inheritDoc} */
	@Override
	public XslTransform load(int uniqueId) {
		// TODO Auto-generated method stub
		return null;
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

	/**
	 * Getter.
	 * @return  the title
	 * @uml.property  name="title"
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter.
	 * @param title  the title to set
	 * @uml.property  name="title"
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter.
	 * @return  the resultMime
	 * @uml.property  name="resultMime"
	 */
	public String getResultMime() {
		return resultMime;
	}

	/**
	 * Setter.
	 * @param resultMime  the resultMime to set
	 * @uml.property  name="resultMime"
	 */
	public void setResultMime(String resultMime) {
		this.resultMime = resultMime;
	}

	/**
	 * Getter.
	 * @return  the extension
	 * @uml.property  name="extension"
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * Setter.
	 * @param extension  the extension to set
	 * @uml.property  name="extension"
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}

}
