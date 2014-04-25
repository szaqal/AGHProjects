package xml.editor.engine.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.Document;

import xml.editor.engine.XmlDocument;

/**
 * Ogolny parser dostaje uaktualniona tresc dokumentu
 * i poddaje ja parsowaniu.
 *
 * (Tutaj jest punkt zlaczenia edytora z obsluga parsowania.)
 *
 * @author malczyk.pawel@gmail.com
 * 
 * @deprecated !!! NIE UZYWANE !!!!!!!
 *
 */
@Deprecated
public class XmlParser {

	/**
	 * Przechowuje bledy parsowania znalezione w tekscie.
	 */
	private List<ParseViolation> parseViolations;

	/**
	 * Konstruktor.
	 */
	public XmlParser() {
		parseViolations = new ArrayList<ParseViolation>();
	}

	/**
	 * Przeprowadza parsowanie dokumentu pod katem DTD. (powinien zwracac jakies
	 * bledy.)
	 *
	 * @param contentToParse
	 *            parsonwana tresc
	 *
	 * @return lista znalezionych bledow w parsowaniu.
	 */
	public List<ParseViolation> parse(final Document contentToParse) {
		if (contentToParse instanceof XmlDocument) {
			testParse((XmlDocument) contentToParse);
		}
		return parseViolations;
	}


	/**
	 * Probna metoda dla sprawdzania czy w tekscie nie ma slowa dupa :-).
	 * @param document dokument
	 */
	private void testParse(final XmlDocument document) {

		String searchFor = " dupa ";
		/*
		 *	TODO:
		 *	Trzeba by zastanowic sie w jakis sposob mozna by to optymalizowac
		 *  tzn czy nie daloby sie na biezaco wrzucac do bufora to co uzytkownik
		 *  wpisuje.
		 */
		try {
			BufferedReader reader = new BufferedReader(new StringReader(document.getFullText()));
			String line = null;
			int lineNr = 0;
			while ((line = reader.readLine()) != null) {
				if (line.contains(searchFor)) {
					int startIndex = 0;
					while (startIndex < line.length()) {
						int foundIndex = line.indexOf(searchFor, startIndex);
						if (foundIndex != -1) {
							startIndex = foundIndex + searchFor.length();
							parseViolations.add(new ParseViolation(ErrorCause.INVALID_DOC_ROOT_ELEMENT,
									ErrorType.WARNING, document.computeLocation(lineNr, foundIndex + 1)));
						} else {
							break;
						}
					}
				}
				lineNr++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}



}
