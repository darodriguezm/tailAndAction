package global.app;

import java.util.*;

/**
 * 
 */
public final class TAAXmlFile {
	// Clase Singleton

	public HashMap<String, ConversionItem> hashConversionItem;
	public String inputFilePath;
	public boolean allowRepeat;
	public int mode;
	public ArrayList<CheckClass> checksArray;
	public OutputFileXMLDetail outputFileDetails;

	private static TAAXmlFile instanciaXMLFile = new TAAXmlFile();

	/**
	 * Default constructor
	 */
	private TAAXmlFile() {
		checksArray = new ArrayList<CheckClass>();
		hashConversionItem = new HashMap<String, ConversionItem>();
	}

	public static TAAXmlFile getInstance() {
		return instanciaXMLFile;
	}

}