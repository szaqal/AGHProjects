package ewpp.business.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import ewpp.entity.AbstractEwppEntity;

/**
 * Klasa bazowa dla notatek/plikow.
 * @author malczyk.pawel@gmail.com
 *
 */
@MappedSuperclass
public class ProjectItemContent implements Serializable, AbstractEwppEntity {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 6681639179416769122L;

	/** Identyfikator. */
	private int uniqueId;

	/** Project Item z ktorym jest zwiazany. */
	private ProjectItem projectItem;

	/** Tytul. */
	private String title;
	
	/** Czy ogolnie dostepne. */
	private Boolean isPublic;

	/** Konstruktor. */
	public ProjectItemContent() { 
		isPublic = false;
	};


	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}


	/**
	 * Getter.
	 * @return uniqueId identyfikator
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PR_ITEM_CONTENT_SEQUENCE")
	public int getUniqueId() {
		return uniqueId;
	}

	/**
	 * @param uniqueId the uniqueId to set
	 */
	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}



	/**
	 * Getter.
	 * @return the projectItem
	 */
	@ManyToOne
	public ProjectItem getProjectItem() {
		return projectItem;
	}

	/**
	 * Setter.
	 * @param projectItem the projectItem to set
	 */
	public void setProjectItem(ProjectItem projectItem) {
		this.projectItem = projectItem;
	}


	/** {@inheritDoc} */
	@Override
	@Transient
	public String[] getJsonData() {
		List<String> data = new ArrayList<String>();
		data.add(title);
		return data.toArray(new String[data.size()]);
	}


	/**
	 * Getter.
	 * @return the isPublic
	 */
	public Boolean getIsPublic() {
		return isPublic;
	}


	/**
	 * Setter.
	 * @param isPublic the isPublic to set
	 */
	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}








}
