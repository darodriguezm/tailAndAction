package cencosud.com.qa.app;

import java.util.*;
import java.io.*;

/**
 * 
 */
public class FullInputFile {

	private File archivoTXT = null;
	private FileReader fileReader = null;
	private BufferReader bufferReader = null;

	/**
	 * @param classToCall
	 */
	public void FullInputFile(IReceptLine classToCall) {
		// TODO implement here
		TAAXmlFile xmlInstance = TAAXmlFile.getInstance();

		try {
			archivoTXT = new File(xmlInstance.inputfilePath);
			fileReader = new FileReader(archivoTXT);
			bufferReader = new BufferReader(fileReader);

			String linea;

			while ( (linea = bufferReader.readLine()) != null) {
				classToCall.doProcessLine(linea);
			}
		} catch (Exception e) {
			//TODO: handle exception
			System.out.println(e);
		}
	}

}