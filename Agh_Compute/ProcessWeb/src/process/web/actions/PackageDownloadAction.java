package process.web.actions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import computation.model.ComputationPackage;
import computation.worker.ComputationPackageWorker;

import core.workers.FilesContentWorker;


/**
 * Handles package download. 
 * @author  <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 */
public class PackageDownloadAction extends AbstractProcessAction {

	/** Serial. */
	private static final long serialVersionUID = 938665784321022313L;
	
	/**
	 * Computation package.
	 * @uml.property  name="packageId"
	 */
	private String packageId;
	
	/**
	 * File being downloaded.
	 * @uml.property  name="inputStream"
	 */
	private InputStream inputStream;
	
	/**
	 * Filename.
	 * @uml.property  name="fileName"
	 */
	private String fileName;
	
	/**
	 * Constructor.
	 */
	public PackageDownloadAction() {
		super();
	}
	
	/** {@inheritDoc} */
	@Override
	public String execute() throws Exception {
		ComputationPackageWorker computationPackageWorker = locate(ComputationPackageWorker.class);
		FilesContentWorker filesContentWorker = locate(FilesContentWorker.class);
		ComputationPackage computationPackage = computationPackageWorker.retrieveComputationPackage(Integer.valueOf(packageId));
		int fileContentId = computationPackage.getFileContentId();
		byte [] content = filesContentWorker.retrieveContent(fileContentId);
		fileName = "file";
		inputStream = new ByteArrayInputStream(content);
		return "download";
	}

	/**
	 * Getter.
	 * @return  the packageId
	 * @uml.property  name="packageId"
	 */
	public String getPackageId() {
		return packageId;
	}

	/**
	 * Setter.
	 * @param packageId  the packageId to set
	 * @uml.property  name="packageId"
	 */
	public void setPackageId(String packageId) {
		this.packageId = packageId;
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

}
