package ewpp.business.entity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ewpp.auth.entity.User;

/**
 * Notatka.
 * @author malczyk.pawel@gmail.com
 *
 */
@Entity
@Table(name="notes")
@SequenceGenerator(name="PR_ITEM_CONTENT_SEQUENCE", sequenceName="PR_ITEM_CONTENT_SEQ")
@Inheritance(strategy=InheritanceType.JOINED)
public class Note extends ProjectItemContent {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 8128878456446603373L;


	/** wlasciciel notatki. */
	private User owner;

	/** Tresc. */
	private String content;


	/** Konstruktor domyslny. */
	public Note() {
	}

	/**
	 * Konstruktor.
	 * @param content zawartosc
	 * @param title tytul
	 */
	public Note(String content, String title) {
		this.content = content;
		setTitle(title);
	}



	/**
	 * Getter.
	 * @return the owner
	 */
	@ManyToOne
	public User getOwner() {
		return owner;
	}

	/**
	 * Setter.
	 * @param owner the owner to set
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}

	/**
	 * Getter.
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Setter.
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}





}
