package xml.editor.gui.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import xml.editor.engine.FileLoader;

/**
 * Abstrakcyjne klasa implementacja podstawowe zachowanie obslugi wczytywanych
 * plikow.
 *
 * @author malczyk.pawel@gmail.com
 *
 */
public abstract class AbstractOpenFileCommand {

	/**
	 * Typy mozliwych do wczytania dokumentow.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public enum DocumentType {

		/** Rozszerzenie DTD. */
		DTD("dtd"),
		/** Rozszerzenie XML. */
		XML("xml");

		/**
		 * Wybrany typ dokumentu.
		 */
		private String docType;

		/**
		 * Konstruktor.
		 *
		 * @param selectedType
		 *            wybrany typ dokumentu
		 */
		DocumentType(final String selectedType) {
			docType = selectedType;
		}

		/**
		 * Zwraca wybrany typ dokumentu.
		 *
		 * @return wybrany typ dokumentu
		 */
		public String getDocType() {
			return docType;
		}


	}


	/**
	 * Zwaca {@link JFileChooser}.
	 *
	 * @param documentType
	 *            wybrany typ dokumentu
	 *
	 * @return dialog otwierania pliku.
	 */
	public JFileChooser createDialog(final DocumentType documentType) {
		JFileChooser fileChooser = FileLoader.getInstance().createOpenDialog();
		FileFilter filter = this.new OpenFileFilter(documentType);
		fileChooser.addChoosableFileFilter(filter);
		fileChooser.setAcceptAllFileFilterUsed(false);
		return fileChooser;
	}

	/**
	 * Przetwarza rezultat dzialania {@link JFileChooser } tzn albo uzytkownik
	 * wybral jakis plik albo nie.
	 *
	 * @param action
	 *            okresla co zrobil uzytkownik
	 * @param selectedFile
	 * 			  wybrana nazwa pliku.           
	 */
	public void processDialogFeedBack(final int action, final File selectedFile) {
		switch (action) {
		case JFileChooser.APPROVE_OPTION:
			System.out.println("OK");
			FileLoader.getInstance().loadDocument(selectedFile);
			break;
		case JFileChooser.CANCEL_OPTION:
			System.out.println("Abort");
			break;
		default:
			break;
		}
	}

	

	/**
	 * Filtr otwieranych plikow.
	 * Oranicza liste plikow przy otwieraniu do XML albo DTD
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	private final class OpenFileFilter extends FileFilter {

		/**
		 * Wybrany typ dokumentu.
		 */
		private DocumentType documentType;

		/**
		 * Konstruktor filtra.
		 * @param docType typ dokuentu
		 */
		public OpenFileFilter(final DocumentType docType) {
			this.documentType = docType;
		}

		/** {@inheritDoc} */
		public boolean accept(final File pathname) {
			boolean result = false;
			String fileExt = getExtension(pathname);
			for (DocumentType docType : DocumentType.values()) {
				if (docType.getDocType().equals(fileExt)) {
					result = true;
					break;
				}
			}

			result = (!result) ? pathname.isDirectory() : result;
			return result;
		}

		/**
	     * Pobiera rozszerzenie pliku.
	     * @param file plik
	     * @return rozszerzenie
	     */
	    private String getExtension(final File file) {
	        String ext = null;
	        String fileName = file.getName();
	        int i = fileName.lastIndexOf('.');

	        if (i > 0 &&  i < fileName.length() - 1) {
	            ext = fileName.substring(i + 1).toLowerCase();
	        }
	        return ext;
	    }

	    /** {@inheritDoc} */
		@Override
		public String getDescription() {
			String result = "Pliki : ";
			switch (documentType) {
			case DTD:
				result += "DTD";
				break;
			case XML:
				result += "XML";
				break;
			default:
				break;
			}
			return result;
		}


	}

}