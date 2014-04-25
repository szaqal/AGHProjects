package computation.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import core.model.AbstractEntity;
import core.model.FileContent;

/**
 * Stores single uploaded ComputationPackage.
 * @author  <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 */
@Entity
public class ComputationPackage extends AbstractEntity implements Serializable {
	
	/**
	 * File name that is used to store computation configuration.
	 * It is stored in package file.
	 */
	public static final String CONFIG_FILENAME = "computation.xml";
	
	
	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -6446846480669045249L;

	
	/**
	 * Identifier.
	 * @uml.property  name="uniqueId"
	 */
	private int uniqueId;
	
	/**
	 * Contains Jar file in byte representation.
	 * @uml.property  name="content"
	 * @uml.associationEnd  
	 */
	private FileContent content;
	
	/**
	 * Contains date of adding.
	 * @uml.property  name="addDate"
	 */
	private Calendar addDate;
	
	/**
	 * Title.
	 * @uml.property  name="title"
	 */
	private String title;

	
	/**
	 * Validated.
	 * @uml.property  name="validated"
	 */
	private boolean validated;
	
	/**
	 * Constructor.
	 */
	public ComputationPackage() { }

	/** {@inheritDoc} */
	@Override
	@Transient
	public String[] getJsonData() {
		List<String> data = new ArrayList<String>();
		data.add(String.valueOf(validated));
		data.add(title);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		data.add(format.format(addDate.getTime()));
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
	 * {@inheritDoc} 
	 * @uml.property  name="uniqueId"
	 */
	@Override
	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;		
	}


	/**
	 * Getter.
	 * @return  the addDate
	 * @uml.property  name="addDate"
	 */
	public Calendar getAddDate() {
		return addDate;
	}

	/**
	 * Setter.
	 * @param addDate  the addDate to set
	 * @uml.property  name="addDate"
	 */
	public void setAddDate(Calendar addDate) {
		this.addDate = addDate;
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
	 * @return  the content
	 * @uml.property  name="content"
	 */
	@OneToOne(cascade={CascadeType.ALL})
	public FileContent getContent() {
		return content;
	}
	
	/**
	 * Returns identifier of actual filecontent.
	 * @return identifier.
	 */
	@Transient
	public int getFileContentId() {
		return content.getUniqueId();
	}

	/**
	 * Setter.
	 * @param content  the content to set
	 * @uml.property  name="content"
	 */
	public void setContent(FileContent content) {
		this.content = content;
	}


	/**
	 * Getter.
	 * @return  the validated
	 * @uml.property  name="validated"
	 */
	public boolean isValidated() {
		return validated;
	}

	/**
	 * Setter.
	 * @param validated  the validated to set
	 * @uml.property  name="validated"
	 */
	public void setValidated(boolean validated) {
		this.validated = validated;
	}



}
