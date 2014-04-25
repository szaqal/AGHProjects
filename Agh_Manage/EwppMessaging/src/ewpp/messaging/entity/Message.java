package ewpp.messaging.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import ewpp.auth.entity.User;
import ewpp.entity.AbstractEwppEntity;

/**
 * Encja wiadomosci.
 * @author malczyk.pawel@gmail.com
 *
 */
@Entity
@SequenceGenerator(name="MESSAGE_SEQUENCE", sequenceName="MESSAGE_SEQ")
@Table(name="messages")
@NamedQueries({
	@NamedQuery(name="incomingMessages",
			query="SELECT msg FROM Message AS msg WHERE msg.recipient=:recipient AND msg.recipientDeleted=:deleted"),
	@NamedQuery(name="outgoingMessages", 
			query = "SELECT msg FROM Message AS msg WHERE msg.sender =:sender AND msg.senderDeleted=:deleted"),
	@NamedQuery(name="incomingMessagesCount",
			query="SELECT COUNT(msg) FROM Message AS msg WHERE msg.recipient=:recipient AND msg.recipientDeleted=:deleted"),	
	@NamedQuery(name="outgoingMessagesCount", 
			query = "SELECT COUNT(msg) FROM Message AS msg WHERE msg.sender =:sender AND msg.senderDeleted=:deleted")			
})
public class Message implements Serializable, AbstractEwppEntity {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -537578673222416014L;

	/** Identyfikator. */
	private int uniqueId;

	/** Naglowek.*/
	private String title;

	/** Tresc. */
	private String content;

	/** Data utworzenia. */
	private Date createDate;

	/** Nadawaca. */
	private User sender;

	/** Nadawaca. */
	private User recipient;

	/** Stan wiadomosci. */
	private MessageStatus messageStatus;

	/** Okresla czy nadawca usunal wiadomosc. */
	private boolean senderDeleted;

	/** Oklresla czy odbiorca usuna wiadomosc. */
	private boolean recipientDeleted;

	/** Konstrutktor ktory ustawia domyslne ustawienia poczatkowe encji. */
	public Message() {
		messageStatus = MessageStatus.NEW;
		recipientDeleted = false;
		senderDeleted = false;
		createDate = Calendar.getInstance().getTime();
	}

	/**
	 * Getter.
	 * @return the uniqueId
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MESSAGE_SEQUENCE")
	public int getUniqueId() {
		return uniqueId;
	}

	/**
	 * Setter.
	 * @param uniqueId the uniqueId to set
	 */
	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

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
	 * @return the sender
	 */
	@ManyToOne
	public User getSender() {
		return sender;
	}

	/**
	 * @param sender the sender to set
	 */
	public void setSender(User sender) {
		this.sender = sender;
	}

	/**
	 * @return the recipientId
	 */
	@ManyToOne
	public User getRecipient() {
		return recipient;
	}

	/**
	 * @param recipient the recipientId to set
	 */
	public void setRecipient(User recipient) {
		this.recipient = recipient;
	}

	/**
	 * @return the messageStatus
	 */
	@Enumerated(EnumType.STRING)
	public MessageStatus getMessageStatus() {
		return messageStatus;
	}

	/**
	 * @param messageStatus the messageStatus to set
	 */
	public void setMessageStatus(MessageStatus messageStatus) {
		this.messageStatus = messageStatus;
	}

	/**
	 * @return the senderDeleted
	 */
	public boolean isSenderDeleted() {
		return senderDeleted;
	}

	/**
	 * @param senderDeleted the senderDeleted to set
	 */
	public void setSenderDeleted(boolean senderDeleted) {
		this.senderDeleted = senderDeleted;
	}

	/**
	 * @return the recipientDeleted
	 */
	public boolean isRecipientDeleted() {
		return recipientDeleted;
	}

	/**
	 * @param recipientDeleted the recipientDeleted to set
	 */
	public void setRecipientDeleted(boolean recipientDeleted) {
		this.recipientDeleted = recipientDeleted;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/** {@inheritDoc}*/
	@Transient
	@Override
	public String[] getJsonData() {
		List<String> data = new ArrayList<String>();
		data.add(messageStatus.toString());
		data.add(title);
		data.add(sender.getLabel());
		data.add(recipient.getLabel());
		return data.toArray(new String[data.size()]);
	}
}
