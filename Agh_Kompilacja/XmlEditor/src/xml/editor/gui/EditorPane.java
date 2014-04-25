package xml.editor.gui;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.EditorKit;
import javax.swing.text.Highlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

import parser.ASTAttributeListNode;
import parser.ParseException;
import parser.SimpleNode;
import parser.Token;
import parser.TokenMgrError;
import parser.XMLDTDParser;
import xml.editor.engine.XmlDocument;
import xml.editor.engine.XmlEditorKit;
import xml.editor.engine.parser.MyParser;

/**
 * Rozszerzony edytor dla naszych potrzeb.
 * @author malczyk.pawel@gmail.com
 *
 */
public final class EditorPane extends JEditorPane {

	private static final long serialVersionUID = 686528944510309030L;
	
	private static EditorPane instance;
	
	private EditorKit editorKit;
	
	public XMLDTDParser erroredParser = null;
	
	private EditorPane() {
		getCaret().setSelectionVisible( false );
		setDragEnabled( false );
		setContentType(XmlEditorKit.MIME_XML);
		editorKit = new XmlEditorKit();
		setEditorKit(editorKit);
		setDocument( new XmlDocument() );
		
	}
	
	public void setPage(final File file)
	{
		try
		{
			super.setPage( file.toURI().toURL() );
			processParsing(file);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void processParsing(final File file) throws FileNotFoundException
	{
		XMLDTDParser parser = new MyParser(new FileReader(file), this);
	}
	
	public void processParsing() 
	{
		String docText;
		XMLDTDParser parser = null;
		try {
			docText = getDocument().getText(0, getDocument().getLength());
			if( docText.length() == 0 )
				return;
			parser = new MyParser(new StringReader(docText), this);
			
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		parse( parser );
	}
	
	private void parse( XMLDTDParser parser )
	{
		if( erroredParser != null )
		{
			erroredParser.parseException = null;
			erroredParser.parseError = null;
			parser.tokenError = null;
		}
		erroredParser = null;
		try
		{
			Highlighter h = getHighlighter();
			h.removeAllHighlights();
			
			SimpleNode root = parser.rootDocType();
			ArrayList<?> elementNodeList = parser.getParsedElements();
		
			for( int i = 0; i != elementNodeList.size(); i++ )
			{
				SimpleNode node = ( SimpleNode ) elementNodeList.get( i );;
				int start = getTextPosition( node.jjtGetFirstToken().beginLine, node.jjtGetFirstToken().beginColumn );
				int end = getTextPosition( node.jjtGetLastToken().endLine, node.jjtGetLastToken().endColumn );
				end += node.jjtGetLastToken().image.length();
				try {
					h.addHighlight( start, end, new DefaultHighlightPainter(Color.orange) );
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}

			for( int i = 0; i != elementNodeList.size(); i++ )
			{
				SimpleNode node = ( SimpleNode ) elementNodeList.get( i );
				
				boolean matches = false;
				
				if( node.jjtGetParent() != null )
				{
					if ( ( ( Token ) ( ( SimpleNode ) node.jjtGetParent() ).jjtGetValue() ).image.compareToIgnoreCase( ( ( Token ) node.jjtGetValue() ).image ) == 0 )
						matches = true;
					else
					{
						for( int j = 0; j != elementNodeList.size(); j++)
						{
							SimpleNode otherNode = ( SimpleNode ) elementNodeList.get( j );
							if( otherNode == node )
								continue;
							
							for( int k = 0; k != otherNode.jjtGetNumChildren(); k++ )
							{
								String name = parser.erraseMultiplicityFromName( ( ( Token ) ( ( SimpleNode) otherNode.jjtGetChild( k ) ).jjtGetValue() ).image );
								
								if( node.jjtGetValue().toString().compareToIgnoreCase( name ) == 0 )
									matches = true;
							}
							
						}
					}
				}
								
				if( !matches )
				{
					parser.tokenError = ( Token ) node.jjtGetValue();
					parser.messageError = "element \"" + ( ( Token ) node.jjtGetValue() ).image + "\" isn't defined in dtd's elements hierarchy";
					throw new Exception();
				}
					
			}
			
			ArrayList<?> attributesNodeList = parser.getParsedAttributes();
			
			for( int i = attributesNodeList.size(); i != 0 ; i-- )
			{
				boolean matches = false;
				ASTAttributeListNode attributeNode = ( ASTAttributeListNode ) attributesNodeList.get( i - 1);
				
				for( int j = 0; j != elementNodeList.size(); j++ )
				{
					SimpleNode node = ( SimpleNode ) elementNodeList.get( j );
					String name = parser.erraseMultiplicityFromName( ( ( Token ) node.jjtGetValue() ).image );
					if( ( name.compareToIgnoreCase( ( ( Token ) attributeNode.jjtGetValue() ).image ) == 0 ) )
						matches = true;
				}
			
				if( !matches )
				{
					parser.messageError = "element's \"" + attributeNode.jjtGetValue() + "\" attribute \"" + ( ( Token ) attributeNode.attributeNameToken ).image + "\" isn't defined in dtd's elements hierarchy";
					parser.tokenError = ( Token )attributeNode.jjtGetValue();
					throw new Exception();
				}
			}
			
			SimpleNode rootData = parser.rootData();
			int a = 0;
			
		
		}
		catch (ParseException e)
		{
			erroredParser = parser;
			erroredParser.parseException = e;
		}
		catch ( TokenMgrError e )
		{
			erroredParser = parser;
			erroredParser.parseError = e;
		}
		catch (Exception e)
		{
			if( parser.tokenError != null )
				erroredParser = parser; 
		}

	}
	
 
	/**
	 * Tworzy komponent edycji.
	 *
	 * @return utworzony komponenet edycji
	 */
	public static EditorPane getInstance() {

		if (instance == null) {
			instance = new EditorPane();
		}

		return instance;
	}
	
	public String getResult()
	{
		String result = "";
		Highlighter h = getHighlighter();
		if( erroredParser == null )
			result = String.valueOf( getCaretPosition() );
		else if( erroredParser.parseError != null )
		{
			result = erroredParser.parseError.getMessage();
			int caretStartLinePosition = Integer.parseInt( result.substring( result.indexOf("Lexical error at line ") + new String( "Lexical error at line " ).length(), result.indexOf(", column ") ) );
			int caretStartCoulmLinePosition = Integer.parseInt( result.substring( result.indexOf("column ") + new String( "column " ).length(), result.indexOf(".  Encountered") ) );
			
			int start = getTextPosition( caretStartLinePosition, caretStartCoulmLinePosition );
			int end = start;
			
			int temp = result.indexOf("Encountered: \"") + new String( "Encountered: \"" ).length();
			while( result.charAt( temp ) != '\"' )
			{
				temp++;
				end++;
			}
			
			try {
				h.addHighlight(start, end, new DefaultHighlightPainter(Color.RED));
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		}
		else if( ( erroredParser.parseException != null ) && ( erroredParser.parseException.getMessage() != null ) )
		{
			result = erroredParser.parseException.getMessage();
			int start = getTextPosition( erroredParser.parseException.currentToken.next.beginLine, erroredParser.parseException.currentToken.next.beginColumn );
			int end = start + erroredParser.parseException.currentToken.next.image.length();
			try {
				h.addHighlight( start, end, new DefaultHighlightPainter(Color.RED) );
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		}
		else if( erroredParser.tokenError != null )
		{
			String text = "";
			String text2 = "";
			int pos = -1;
			int pos2 = -1;
			result = erroredParser.messageError;
			
			text = erroredParser.tokenError.toString();
			pos = getTextPosition( erroredParser.tokenError.beginLine, erroredParser.tokenError.beginColumn );
			if( erroredParser.tokenErrorSecondary != null )
			{
				text2 = erroredParser.tokenErrorSecondary.toString();
				pos2 = getTextPosition( erroredParser.tokenErrorSecondary.beginLine, erroredParser.tokenErrorSecondary.beginColumn );
			}
			
			try {
				h.addHighlight( pos, pos + text.length(), new DefaultHighlightPainter(Color.RED) );
				if( pos2 != -1 )
					h.addHighlight( pos2, pos2 + text2.length(), new DefaultHighlightPainter(Color.ORANGE) );

			} catch (BadLocationException e) {
				e.printStackTrace();
			}
	
		}
		
		return result;
	
	}
	
	public int getTextPosition( int _row, int _column )
	{
		_row--;
		_column--;
		String text = getText();
		int counter = 0;
		int result = 0;
		while( ( counter < text.length() ) && ( _row != 0 ) )
		{
			if( text.charAt( counter ) == '\r' )
			{
				if( text.charAt( counter + 1 ) == '\n' )
					counter++;
				_row--;
			}

			counter++;
			result++;
		}
		
		while( ( counter < text.length() ) && ( _column != 0 ) )
		{
			result++;
			_column--;
			counter++;
		}
		
		return result;
	}


}
