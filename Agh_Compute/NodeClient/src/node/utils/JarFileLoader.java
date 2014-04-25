package node.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author malczyk.pawel@gmail.com
 *
 */
public class JarFileLoader extends URLClassLoader {
	
	
	/**
	 * Constructor.
	 * 
	 * @param urls
	 *            array of urls for loading jars.
	 */
	public JarFileLoader(URL[] urls) {
		super(urls);
	}

	/**
	 * Adds file.
	 * @param path path
	 * @throws MalformedURLException exception throwed
	 */
	public void addFile(String path) throws MalformedURLException {
		String urlPath = "jar:file://" + path + "!/";
		addURL(new URL(urlPath));
	}

}
