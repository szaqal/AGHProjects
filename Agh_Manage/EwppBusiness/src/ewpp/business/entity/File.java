package ewpp.business.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import ewpp.auth.entity.User;

/**
 * Plik.
 * @author malczyk.pawel@gmail.com
 *
 */
@Entity
@Table(name="files")
@SequenceGenerator(name="PR_ITEM_CONTENT_SEQUENCE", sequenceName="PR_ITEM_CONTENT_SEQ")
@Inheritance(strategy=InheritanceType.JOINED)
public class File extends ProjectItemContent {

	/** Serial. */
	private static final long serialVersionUID = -3764163247628627528L;

	/** Wielkosc pliku. */
	private static final int FILE_CONTENT_SIZE = 1000000;

	/** Nazwa pliku. */
	private String fileName;

	/** Content Type.*/
	private String contentType;

	/** Zawartosc pliku.*/
	private byte [] content;

	/** wlasciciel notatki. */
	private User owner;
	
	/** Suma kontrolna MD5. */
	private String md5Hash;


	/** Konstruktor. */
	public File() { };

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}
	
	/**
	 * Zwraca contentType pod ikony tzn zamienione "/" na "-" .
	 * @return contentType
	 */
	@Transient
	public String getContentTypeForImage() {
		return contentType.replace("/", "-");
	}


	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the content
	 */
	@Lob
	@Column(length=FILE_CONTENT_SIZE)
	public byte[] getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(byte[] content) {
		this.content = content;
	}

	/**
	 * @return the owner
	 */
	public User getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}

	/**
	 * Zwraca sum� md5.
	 * 
	 * @return obliczona suma md5
	 */
	public String getMd5Hash() {
		return md5Hash;
	}

	/**
	 * UStawia suma md5.
	 * 
	 * @param md5Hash
	 *            suma md5
	 */
	public void setMd5Hash(String md5Hash) {
		this.md5Hash = md5Hash;
	}
	
	/** {@inheritDoc} */
	@Override
	@Transient
	public String[] getJsonData() {
		List<String> data = new ArrayList<String>();
		data.add(getTitle());
		data.add(getContentType());
		return data.toArray(new String[data.size()]);
	}



}
