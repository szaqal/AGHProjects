package computation.model;

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

import core.model.AbstractEntity;
import core.model.FileContent;

/**
 * Validation file.
 * @author  malczyk.pawel@gmail.com
 */
@Entity
@NamedQueries({
	@NamedQuery(name="existingSchemas", query="SELECT vs FROM ValidationSchema AS vs")
})
public class ValidationSchema extends AbstractEntity {
	
	/**
	 * Existing schemas.
	 */
	public static final String EXISTING = "existingSchemas";
	
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
	 * Actual file.
	 * @uml.property  name="fileContent"
	 * @uml.associationEnd  
	 */
	private FileContent fileContent;
	
	/**
	 * Constructor.
	 */
	public ValidationSchema() {
		super();
	}

	/** {@inheritDoc} */
	@Transient
	@Override
	public String[] getJsonData() {
		List<String> data = new ArrayList<String>();
		data.add(title);
		data.add(String.valueOf(getFileContentId()));
		return data.toArray(new String[data.size()]);
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
	 * Returns file content id.
	 * @return file content primary key;
	 */
	@Transient
	public long getFileContentId() {
		return getFileContent().getUniqueId();
	}
	
	@Override
	public String toString() {
		return title;
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
	
}
