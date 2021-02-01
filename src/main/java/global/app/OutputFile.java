package global.app;

import java.io.*;

/**
 * 
 */
public class OutputFile implements IOutputResult {

	/**
	 * 
	 */
	private File outputFile;
	private BufferedWriter bw;
	private PrintWriter pw;
	private int fileCorrelativo;
	private TAAXmlFile xmlInstance;
	private String lastLineWrited;

	public OutputFile(TAAXmlFile pXmlInstance) {
		// TODO implement here
		xmlInstance = pXmlInstance;
		fileCorrelativo = pXmlInstance.outputFileDetails.getCorrelative();
		lastLineWrited = "";
	}

	/**
	 * @param operation
	 * @param processResult
	 */
	public void doProcessOutput(EOutputOperations operation, String processResult, int lineNumber) {
		// TODO implement here
		
		try {
			switch (operation) {
			case CREATE:
				switch (xmlInstance.mode) {
				case 0:
					// Lectura secuencial activada por cambios
					break;

				case 1:
					// // lectura completa del archivo estático
					if (outputFile == null) {

						if (!(new File(xmlInstance.outputFileDetails.getDirectory()).exists())) {
							new File(xmlInstance.outputFileDetails.getDirectory()).mkdir();
						}

						outputFile = new File(xmlInstance.outputFileDetails.getDirectory()
								+ xmlInstance.outputFileDetails.getPrename() + fileCorrelativo++
								+ xmlInstance.outputFileDetails.getPostname()
								+ xmlInstance.outputFileDetails.getExtension());

						outputFile.createNewFile();

						bw = new BufferedWriter(new FileWriter(outputFile));
						pw = new PrintWriter(bw);

						pw.write(xmlInstance.outputFileDetails.getHeader());
						lastLineWrited = xmlInstance.outputFileDetails.getHeader();

						// Impresión de traseo
						System.out.println("[archivo creado: " + outputFile.getCanonicalPath() + "]"+ "\n[cabecera escrita]");
					
					}

					break;
				}
				break;

			case WRITE:
				if (outputFile != null && outputFile.canWrite()) {

					if (processResult.equals(lastLineWrited)) {
						if (xmlInstance.allowRepeat) {
							pw.append(processResult);
							lastLineWrited = processResult;

							// Impresión de traseo
							System.out.print("- línea " + lineNumber + " --> " + processResult);
						}else{
							// Impresión de traseo
							System.out.print("[ línea " + lineNumber + " (OMITIDA POR REPETICION) ]\n");
						}
					} else {
						pw.append(processResult);
						lastLineWrited = processResult;

						// Impresión de traseo
						System.out.print("- línea " + lineNumber + " --> " + processResult);
					}

				}
				break;

			case CLOSE:

				if (outputFile != null) {
					pw.append(xmlInstance.outputFileDetails.getFooter());
					lastLineWrited = "";

					pw.close();
					bw.close();
					
					// Impresión de traseo
					System.out.println("[pié escrito]\n[archivo cerrado: " + outputFile.getCanonicalPath() + "]\n");
					
					outputFile = null;
				}
				break;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}