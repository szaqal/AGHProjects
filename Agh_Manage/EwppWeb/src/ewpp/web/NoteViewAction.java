package ewpp.web;

import ewpp.auth.entity.User;
import ewpp.business.entity.Note;
import ewpp.business.workers.NotesWorker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Podglad notatki.
 * @author malczyk.pawel@gmail.com
 *
 */
public class NoteViewAction extends AbstractEwppAction {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 8678925951720718076L;
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(NoteViewAction.class);
	
	/**
	 * Identyfikator notatki.
	 */
	private int noteId;
	
	/**
	 * Notatka.
	 */
	private Note note;
	
	/**
	 * Wlasciciel.
	 */
	private User owner;

	/** Konstruktor. */
	public NoteViewAction() { }


	/** {@inheritDoc} */
	@Override
	public String getDefaultOpertation() {
		return VIEW;
	}

	/** {@inheritDoc} */
	@Override
	protected String doView() {
		LOG.debug("NoteID {}", noteId);
		note = locate(NotesWorker.class).retrieveNote(noteId);
		owner = note.getOwner();
		LOG.debug("ss {}", owner);
		return VIEW;
	}

	/**
	 * Getter.
	 * @return identyfikator notatki
	 */
	public final int getNoteId() {
		return noteId;
	}

	/**
	 * Setter.
	 * @param noteId id notatki
	 */
	public final void setNoteId(int noteId) {
		this.noteId = noteId;
	}


	/**
	 * Getter.
	 * @return notatka
	 */
	public final Note getNote() {
		return note;
	}


	/**
	 * Setter.
	 * @param note notatka
	 */
	public final void setNote(Note note) {
		this.note = note;
	}


	/**
	 * Getter.
	 * @return the owner
	 */
	public final User getOwner() {
		return owner;
	}

}
