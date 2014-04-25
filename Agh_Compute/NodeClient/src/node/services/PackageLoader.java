package node.services;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;

import node.utils.JarFileLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Loads computations package from classpath.
 * 
 * @author malczyk.pawel@gmail.com
 *
 */
public final class PackageLoader {
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(PackageLoader.class);
	
	/**
	 * Current instance.
	 */
	private static PackageLoader instance;
	
	/**
	 * Path to directory where computation packages are stored.
	 */
	private String jarPath;
	
	/**
	 * File loader.
	 */
	private JarFileLoader jarFileLoader;
	
	/**
	 * Constructor.
	 * @param location location where computation package Jar's are stored
	 */
	private PackageLoader(String location) { 
		this.jarPath = location;
		this.jarFileLoader = new JarFileLoader(new URL[]{});
		load();
	}
	
	/**
	 * Loads computation package.
	 */
	private void load() { 
		LOG.info("Loading JAR file from {}", jarPath);
		File jarDir = new File(jarPath);
		if(!jarDir.isDirectory()) {
			LOG.error("Invlid jar directory location");
			return;
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
			String fileName = file.getAbsolutePath();
			try {
				LOG.info("Loading library {}", fileName);
				jarFileLoader.addFile(fileName);
			} catch (MalformedURLException e) {
				LOG.warn("Invalid file {}", fileName);
			}
		}
		
	}
	
	/**
	 * Loads particular class.
	 * @param className clazzname
	 * @return class object.
	 */
	public Class<?> load(String className) {
		try {
			return jarFileLoader.loadClass(className);
		} catch (ClassNotFoundException e) {
			LOG.warn("Class not found {}", className);
		}
		return null;
	}

	/**
	 * Getter.
	 * @param location base location
	 * @return the instance
	 */
	public static PackageLoader getInstance(String location) {
		return (instance == null) ? instance = new PackageLoader(location) : instance;
	}
	
}
