package global.app.InputFileFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import global.app.*;

/**
 * 
 */
public class FullInputFile extends InputFile {

	private File archivoTXT;
	private FileReader fileReader;
	private BufferedReader bufferReader;

	/**
	 * @param classToCall
	 */
	public FullInputFile(IReceptLine classToCall) {
		super(classToCall);
	}

	@Override
	public void leerArchivo() {
		// TODO Auto-generated method stub
		// TODO implement here
		TAAXmlFile xmlInstance = TAAXmlFile.getInstance();

		try {
			archivoTXT = new File(xmlInstance.inputFilePath);
			fileReader = new FileReader(archivoTXT);
			bufferReader = new BufferedReader(fileReader);

			String linea;

			while ((linea = bufferReader.readLine()) != null) {
				getInputClass().doProcessLine(linea);
			}
			
			fileReader.close();
			bufferReader.close();

		} catch (IOException e) {
			// TODO: handle exception
			System.out.println(e.getLocalizedMessage());
		}
	}

}