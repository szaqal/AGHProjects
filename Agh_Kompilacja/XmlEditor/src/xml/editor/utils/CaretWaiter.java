package xml.editor.utils;

import xml.editor.gui.EditorPane;
import xml.editor.gui.StatusBar;

/**
 * Powoduje opoznienie parsowania do czasu przestania ciaglego pisania.
 * @author malczyk.pawel@gmail.com
 *
 * @deprecated NIE UZYWANE
 */
@Deprecated
public final class CaretWaiter implements Runnable {

	/** Sta³a. */
	private static final int SLEEP = 2;

	/** Sta³a. */
	private static final int TRESHOLD = 100;
	
	/** Ilosc. */
	private int treshold = TRESHOLD;
	
	private EditorPane editorPane;
	
	private StatusBar statusBar;
	
	/**
	 * Konstruktor.
	 * @param editPane edytor.
	 */
	public CaretWaiter(final EditorPane editPane, final StatusBar statusBar) {
		this.editorPane = editPane;
		this.statusBar = statusBar;
		
	}
	
	/** {@inheritDoc} */
	@Override
	public void run() {
		while (true) {
			while (treshold > 0) {
				treshold--;
				try {
					Thread.sleep(SLEEP);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
//			EditorStyles.resetStyle((StyledDocument) editorPane.getDocument());
			editorPane.processParsing();
			
			statusBar.updateLocationLabel( editorPane.getResult() );
	
			
			synchronized (this) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}	
			}
			
		}
	}
	
	/**
	 * Resetuje licznik.
	 */
	public void reset() {
		synchronized (this) {
			notify();	
		}
		treshold = TRESHOLD;
	}

}
