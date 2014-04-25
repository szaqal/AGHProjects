package core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;


/**
 * Represents single file content can be  referenced from other entity types.
 */
@Entity
public class FileContent extends AbstractEntity {
	

	/** File size. */
	private static final int FILE_CONTENT_SIZE = 1000000;
	
	/**
	 * Identifier.
	 * @uml.property  name="uniqueId"
	 */
	private int uniqueId;
	
	/**
	 * Filename.
	 * @uml.property  name="fileName"
	 */
	private String fileName;
	
	/**
	 * MimeType.
	 * @uml.property  name="mimeType"
	 */
	private String mimeType;
	
	/**
	 * Actual content.
	 * @uml.property  name="content"
	 */
	private byte [] content;
	
	
	/**
	 * Constructor.
	 */
	public FileContent() { }
	
	/**
	 * Constructor.
	 * @param content actual content
	 */
	public FileContent(byte [] content) { 
		this.content = content;
	}

	/** {@inheritDoc} */
	@Transient
	@Override
	public String[] getJsonData() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc} 
	 * @uml.property  name="uniqueId"
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Override
	public int getUniqueId() {
		return uniqueId;
	}

	/**
	 * {@inheritDoc} 
	 * @uml.property  name="uniqueId"
	 */
	@Override
	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
		
	}

	/**
	 * Getter.
	 * @return  the content
	 * @uml.property  name="content"
	 */
	@Lob
	@Column(length=FILE_CONTENT_SIZE)
	public byte[] getContent() {
		return content;
	}

	/**
	 * Setter.
	 * @param content  the content to set
	 * @uml.property  name="content"
	 */
	public void setContent(byte[] content) {
		this.content = content;
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
	 * @return  the mimeType
	 * @uml.property  name="mimeType"
	 */
	public String getMimeType() {
		return mimeType;
	}

	/**
	 * Setter.
	 * @param mimeType  the mimeType to set
	 * @uml.property  name="mimeType"
	 */
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	
	
}

