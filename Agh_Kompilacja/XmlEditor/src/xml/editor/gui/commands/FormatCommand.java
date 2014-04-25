package xml.editor.gui.commands;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import xml.editor.gui.EditorPane;

/**
 * Formatowanie wczytanego/napisanego dokumentu XML.
 * @author malczyk.pawel@gmail.com
 *
 */
public class FormatCommand implements EditorCommand {
	
	/** Writer. */
	private StringWriter writer;

	/** {@inheritDoc} */
	@Override
	public void perform() {
		System.out.println("Formatowanie..");
		Format format = Format.getPrettyFormat();
		XMLOutputter out = new XMLOutputter(format);
		writer = new StringWriter();
		
		Document doc = EditorPane.getInstance().getDocument();
		try {
			String text = EditorPane.getInstance().getDocument().getText(0, doc.getLength());
			SAXBuilder builder = new SAXBuilder(false);
			org.jdom.Document xmlDoc = builder.build(new StringReader(text));
			out.output(xmlDoc, writer);
			
			
			/*
			 * TODO:
			 * Gramatyka chwilowo nie obsluguje 
			 * <?xml ..... ?>
			 * dlatego pierwsza linia jest usuwana
			 * trzeba to docelowo usunac
			 */
			StringBuffer buffer = writer.getBuffer();
			buffer.delete(0, buffer.indexOf(System.getProperty("line.separator")));
			
			String formatted = buffer.toString();
			writer.write(formatted);
			
			System.out.println();
			doc.remove(0, doc.getLength());
			doc.insertString(0, formatted, null);
			EditorPane.getInstance().processParsing();
		} catch (BadLocationException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
