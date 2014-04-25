package node.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Loads libraries.
 * @author malczyk.pawel@gmail.com
 *
 */
public final class TaskPopulator {
	
	/**
	 * Error message.
	 */
	private static final String ERROR_PARSING_MSG = "Error parsing configuration file";
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(TaskPopulator.class);
	
	/**
	 * Constructor.
	 */
	private TaskPopulator() { }
	
	/**
	 * Populates task mapping.
	 * @param jarLocation location where libraries are stored.
	 * @return populated map
	 */
	public static HashMap<String, String> populate(String jarLocation) {
		
		HashMap<String, String> result = new HashMap<String, String>();
		
		LOG.info("Loading JAR file from {}", jarLocation);
		File jarDir = new File(jarLocation);
		if(!jarDir.isDirectory()) {
			LOG.error("Invlid jar directory location");
			return result;
		}
		
		File []  files = jarDir.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				String substring = name.substring(name.lastIndexOf('.')+1);
				boolean equals = "jar".equals(substring);
				LOG.info("Is valid file {} {}", name, equals);
				return equals;
			}
		});
		
		for(File file : files) {
			try {
				byte[] bytes = readFile(file);
				JarEntry config = getComputationConfig(bytes);
				if(config != null) {
					LOG.info("Found configuration for file {}", file.getName());
					result.putAll(parse(config, new JarFile(file)));
				} else {
					continue;
				}
			} catch (IOException e) {
				continue;
			}
		}
		
		return result;
	}
	
	/**
	 * Populates map through configs.
	 * @param configLocation directory where computation configurations are stored.
	 * @return populated map
	 */
	public static Map<String, String> parseConfigs(String configLocation){
		HashMap<String, String> result = new HashMap<String, String>();
		File locationDir = new File(configLocation);
		if (locationDir.isDirectory()) {
			File[] files = locationDir.listFiles();

			for (File file : files) {
				if (file.isFile()) {
					try {
						result.putAll(populate(new HashMap<String, String>(), new FileInputStream(file)));
					} catch (IOException e) {
						LOG.warn("IO ", e);
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * Parses single Jar archive file.
	 * @param configEntry configuration file
	 * @param jarFile jar file
	 * @return populated map
	 * @throws IOException exception throwed
	 */
	private static Map<String, String> parse(JarEntry configEntry, JarFile jarFile) throws IOException {
		HashMap<String, String> result = new HashMap<String, String>();
		ZipEntry configFileZipEntry = jarFile.getEntry(configEntry.getName());
		InputStream is = jarFile.getInputStream(configFileZipEntry);
		return populate(result, is);
	}

	/**
	 * Populates task map.
	 * @param result resulting map
	 * @param is input stream
	 * @return task map
	 * @throws IOException exception throwed
	 */
	private static Map<String, String> populate(HashMap<String, String> result, InputStream is) throws IOException {
		try {
	    	
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(is);

			
			XPath xpath = XPathFactory.newInstance().newXPath();
		    
			XPathExpression expr = xpath.compile("//task");
	    	
			NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			
			for (int i = 0; i < nodes.getLength(); i++) {
				String className = nodes.item(i).getAttributes().getNamedItem("className").getNodeValue();
				String name = nodes.item(i).getAttributes().getNamedItem("name").getNodeValue();
				result.put(name, className);
			}
		} catch (XPathExpressionException e) {
			LOG.warn(ERROR_PARSING_MSG, e);
		} catch (ParserConfigurationException e) {
			LOG.warn(ERROR_PARSING_MSG, e);
		} catch (SAXException e) {
			LOG.warn(ERROR_PARSING_MSG, e);
		}
		
		is.close();

		return result;
	}

	/**
	 * Reads file to byte[].
	 * @param file file being readed.
	 * @return raw jar file data
	 * @throws IOException exception throwed
	 */
	private static byte[] readFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);
		long length = file.length();
		byte[] bytes = new byte[(int) length];

		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		if (offset < bytes.length) {
			LOG.error("Error reading file");
		}
		return bytes;
	}

	/**
	 * Retrieves computation.xml from jar archive.
	 * @param bytes raw jar file content.
	 * @return JarEntry which actually is computaiton.xml file.
	 */
	private static JarEntry getComputationConfig(byte[] bytes) {
		JarEntry entry = null;
		JarEntry result = null;
		try {
			JarInputStream packageFileStream = new JarInputStream(new ByteArrayInputStream(bytes));
			while ( (entry = packageFileStream.getNextJarEntry()) != null) {
				if("computation.xml".equals(entry.getName())) {
					result = entry;
					break;
				}
			}
			packageFileStream.close();
		} catch (IOException e) {
			LOG.warn("II exception", e.getMessage());
		}
		return result;
	}
	
}
