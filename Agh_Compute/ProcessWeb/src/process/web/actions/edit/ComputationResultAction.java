package process.web.actions.edit;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import process.web.actions.AbstractEditAction;
import process.web.actions.ActionHandler;

import computation.model.ComputationResult;
import computation.worker.ComputationResultWorker;

import core.model.XslTransform;
import core.utils.StringUtils;
import core.workers.PdfWorker;
import core.workers.TransformWorker;

/**
 * Computation result action.
 * @author  malczyk.pawel@gmail.com
 */
public class ComputationResultAction extends AbstractEditAction<ComputationResult> {

	/**
	 * String literal.
	 */
	private static final String DOWNLOAD = "download";

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 7233254406557023278L;
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ComputationResult.class);
	
	/**
	 * Handler map.
	 * @uml.property  name="handlers"
	 */
	private Map<String, ActionHandler> handlers;
	
	/**
	 * Result id.
	 * @uml.property  name="resultId"
	 */
	private String resultId;
	
	/**
	 * Filename.
	 * @uml.property  name="fileName"
	 */
	private String fileName;
	
	/**
	 * Content type.
	 * @uml.property  name="contentType"
	 */
	private String contentType;
	
	/**
	 * Computation result.
	 * @uml.property  name="result"
	 * @uml.associationEnd  
	 */
	private ComputationResult result;
	
	/**
	 * Transformations.
	 * @uml.property  name="transforms"
	 */
	private List<XslTransform> transforms;
	
	/**
	 * Choosed transform id.
	 * @uml.property  name="transformId"
	 */
	private String transformId;
	
	/**
	 * File being downloaded.
	 * @uml.property  name="inputStream"
	 */
	private InputStream inputStream;
	
	/**
	 * Constructor.
	 */
	public ComputationResultAction() {
		super();
		handlers = new HashMap<String, ActionHandler>();
		handlers.put(LOAD, new LoadHandler());
		handlers.put(DOWNLOAD, new DownloadHandler());
	}

	/** {@inheritDoc} */
	@Override
	protected String defaultOperation() {
		return LOAD;
	}

	/** {@inheritDoc} */
	@Override
	public String doEdit() {
		result = load(Integer.valueOf(resultId));
		transforms = locate(TransformWorker.class).retrieveTransforms(null);
		LOG.info("Loding computation result {}", resultId);
		return EDIT;
	}
	
	/**
	 * Handles download.
	 * @return downloaded.
	 */
	public String doDownload() {
		ComputationResultWorker computationResult = locate(ComputationResultWorker.class);
		byte [] data = computationResult.retrieveAsXml(result.getUniqueId());
		
		if(StringUtils.isEmpty(transformId) || "0".equals(transformId)) {
			fileName = "result.xml";
			contentType = StringUtils.TEXT_HTML;
			inputStream = new ByteArrayInputStream(data);
		} else {
			TransformWorker transformWorker = locate(TransformWorker.class);
			XslTransform transform = transformWorker.retrieveById(Integer.valueOf(transformId));
			contentType = transform.getResultMime();
			if("application/pdf".equals(contentType)) {
				byte [] pdfContent = locate(PdfWorker.class).getPdf(Integer.valueOf(transformId), data);
				inputStream =  new ByteArrayInputStream(pdfContent);
			} else {
				inputStream = new ByteArrayInputStream(transformWorker.doTransformation(data, Integer.valueOf(transformId)));
			}
			fileName = "result."+transform.getResultExtension();
			
		}
		return DOWNLOAD;
	}

	/** {@inheritDoc} */
	@Override
	public String doSave() {
		return null;
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
	public ComputationResult load(int uniqueId) {
		return locate(ComputationResultWorker.class).retrieveComputationResult(uniqueId);
	}

	/**
	 * Getter.
	 * @return  the resultId
	 * @uml.property  name="resultId"
	 */
	public String getResultId() {
		return resultId;
	}

	/**
	 * Setter.
	 * @param resultId  the resultId to set
	 * @uml.property  name="resultId"
	 */
	public void setResultId(String resultId) {
		this.resultId = resultId;
	}
	
	/**
	 * Handler load result event.
	 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
	 *
	 */
	private class LoadHandler implements ActionHandler {
		
		/**
		 * 
		 * Constructor.
		 */
		public LoadHandler() { }

		/** {@inheritDoc} */
		@Override
		public String handle() {
			return doEdit();
		}
	}
	
	/**
	 * Download handler.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	private class DownloadHandler implements ActionHandler {

		/**
		 * Constructor.
		 */
		public DownloadHandler() {
		}
		
		/** {@inheritDoc} */
		@Override
		public String handle() {
			return doDownload();
		}
		
	}

	/**
	 * Getter.
	 * @return  the result
	 * @uml.property  name="result"
	 */
	public ComputationResult getResult() {
		return result;
	}

	/**
	 * Setter.
	 * @param result  the result to set
	 * @uml.property  name="result"
	 */
	public void setResult(ComputationResult result) {
		this.result = result;
	}

	/**
	 * Getter.
	 * @return  the fileName
	 * @uml.property  name="fileName"
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Setter.
	 * @param fileName  the fileName to set
	 * @uml.property  name="fileName"
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Getter.
	 * @return  the contentType
	 * @uml.property  name="contentType"
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * Setter.
	 * @param contentType  the contentType to set
	 * @uml.property  name="contentType"
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
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
	 * @return  the inputStream
	 * @uml.property  name="inputStream"
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * Setter.
	 * @param inputStream  the inputStream to set
	 * @uml.property  name="inputStream"
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * Getter.
	 * @return  the transforms
	 * @uml.property  name="transforms"
	 */
	public List<XslTransform> getTransforms() {
		return transforms;
	}

	/**
	 * Setter.
	 * @param transforms  the transforms to set
	 * @uml.property  name="transforms"
	 */
	public void setTransforms(List<XslTransform> transforms) {
		this.transforms = transforms;
	}

	/**
	 * Getter.
	 * @return  the transformId
	 * @uml.property  name="transformId"
	 */
	public String getTransformId() {
		return transformId;
	}

	/**
	 * Setter.
	 * @param transformId  the transformId to set
	 * @uml.property  name="transformId"
	 */
	public void setTransformId(String transformId) {
		this.transformId = transformId;
	}

}
