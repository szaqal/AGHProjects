package core.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 * Represents XML entity.
 * @author  malczyk.pawel@gmail.com
 */
@Entity
@NamedQueries({
	@NamedQuery(name="liveTransforms",
			query="SELECT xslt FROM XslTransform AS xslt"),
	@NamedQuery(name="existingGroupsCount", 
			query="SELECT COUNT(xslt) FROM XslTransform AS xslt")
})
public class XslTransform extends AbstractEntity {
	
	/**
	 * Named query name.
	 */
	public static final String LIVE = "liveTransforms";
	
	/**
	 * Named query name.
	 */
	public static final String LIVE_COUNT = "existingGroupsCount";
	
	/**
	 * Primary key.
	 * @uml.property  name="uniqueId"
	 */
	private int uniqueId;
	
	/**
	 * Title.
	 * @uml.property  name="title"
	 */
	private String title;
	
	/**
	 * Mime type for file after transformation.
	 * @uml.property  name="resultMime"
	 */
	private String resultMime;
	
	
	/**
	 * Result file name extension.
	 * @uml.property  name="resultExtension"
	 */
	private String resultExtension;
	
	/**
	 * File content.
	 * @uml.property  name="fileContent"
	 * @uml.associationEnd  
	 */
	private FileContent fileContent;
	
	/**
	 * Constructor.
	 */
	public XslTransform() {
		super();
	}

	/** {@inheritDoc} */
	@Transient
	@Override
	public String[] getJsonData() {
		List<String> data = new ArrayList<String>();
		data.add(title);
		return data.toArray(new String[data.size()]);
	}

	/**
	 * Getter.
	 * @return  the fileContent
	 * @uml.property  name="fileContent"
	 */
	@OneToOne(cascade={CascadeType.ALL})
	public FileContent getFileContent() {
		return fileContent;
	}

	/**
	 * Setter.
	 * @param fileContent  the fileContent to set
	 * @uml.property  name="fileContent"
	 */
	public void setFileContent(FileContent fileContent) {
		this.fileContent = fileContent;
	}


	/**
	 * Getter.
	 * @return  the uniqueId
	 * @uml.property  name="uniqueId"
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Override
	public int getUniqueId() {
		return uniqueId;
	}


	/**
	 * Setter.
	 * @param uniqueId  the uniqueId to set
	 * @uml.property  name="uniqueId"
	 */
	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
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
	 * @return  the resultExtension
	 * @uml.property  name="resultExtension"
	 */
	public String getResultExtension() {
		return resultExtension;
	}

	/**
	 * Setter.
	 * @param resultExtension  the resultExtension to set
	 * @uml.property  name="resultExtension"
	 */
	public void setResultExtension(String resultExtension) {
		this.resultExtension = resultExtension;
	}
}
