package global.app;

import java.io.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import global.app.InputFileFactory.*;

/**
 * De acuerdo al parámetro <mode> del TAAXmlFile se crea una instancia de
 * LiveInputfile o FullInputFile. Al crear esta instancia y se le pasa como
 * parámetro la instancia de la clase ProcessLines. En estas clases
 * (LiveInputfile o FullInputFile) se llama a la función doProcessLine de la
 * clase que implementa la interfaz IReceptLine.
 */
public class Loader implements Runnable{

	/**
	 * 
	 */
	private TAAXmlFile xmlFile;
	private ProcessLines process;
	private OutputFile outputFile;
	private InputFile readInputFile;
	private Thread threadPrincipal;

	/**
	 * Default constructor
	 */
	public Loader() {
		xmlFile = TAAXmlFile.getInstance();

		readXMLFile();
		
		outputFile = new OutputFile(xmlFile);
		process = new ProcessLines(outputFile);

		readInputFile = InputFileFactory.getInputFile(xmlFile.mode, process);
		
		threadPrincipal = new Thread(this);
		threadPrincipal.start();

	}
	
	public void run() {
		
		System.out.print(" -- Ejecuci�n en modo: ");
		
		switch (xmlFile.mode) {
		case 0:
			System.out.println("INTERACTIVO --");
			break;
		case 1:
			System.out.println("AUTOMATICO --");
			break;
		}
		
		System.out.print(" -- Permite repetici�n de salida: ");
		
		if (xmlFile.allowRepeat) {
			System.out.println("ACTIVADO --");
		} else if (xmlFile.mode == 1) {
			System.out.println("DESACTIVADO --");
		}
		
		System.out.println();
		
		readInputFile.leerArchivo();
	}

	public void readXMLFile() {
		// TODO implement here
		try {
			File fisicXMLFile = new File("config.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fisicXMLFile);

			doc.getDocumentElement().normalize();

			// Parseo de los tag del archivo
			// Tag de configuraci�n inicial <config>
			Element configNode = (Element) doc.getElementsByTagName("config").item(0);

			xmlFile.inputFilePath = ((Element) configNode.getElementsByTagName("origin").item(0)).getTextContent();
			xmlFile.allowRepeat = Boolean.parseBoolean(configNode.getElementsByTagName("allowrepeat").item(0).getTextContent());
			xmlFile.mode = Integer.parseInt(configNode.getElementsByTagName("mode").item(0).getTextContent());

			// Tags con expresiones regulares de coincidencia inicial
			// <expressions>
			NodeList expressionsNodeList = doc.getElementsByTagName("expressions").item(0).getChildNodes();

			for (int i = 0; i < expressionsNodeList.getLength(); i++) {

				Node checkNodeList = expressionsNodeList.item(i);

				if (checkNodeList.getNodeType() == Node.ELEMENT_NODE) {
					Element checkElement = (Element) checkNodeList;

					CheckClass tempCheckItem = new CheckClass(checkElement.getAttribute("pattern"),
							checkElement.getAttribute("match"),
							Integer.parseInt(checkElement.getAttribute("submatchlevel")));

					// Tag de parámetros de cada check
					NodeList parametersNodeList = checkNodeList.getChildNodes();

					for (int j = 0; j < parametersNodeList.getLength(); j++) {

						Node parametersNode = parametersNodeList.item(j);

						if (parametersNode.getNodeType() == Node.ELEMENT_NODE) {
							Element parameterElement = (Element) parametersNode;

							tempCheckItem.parameters.add(new ParametersCheckClass(parameterElement.getNodeName(),
									parameterElement.getAttribute("pattern"),
									parameterElement.getAttribute("conv_id")));
						}
					}

					xmlFile.checksArray.add(tempCheckItem);

				}

			}

			// Tag de string de conversion según su ID <conversions>
			NodeList conversionsNodeList = ((Element) doc.getElementsByTagName("conversions").item(0))
					.getElementsByTagName("conv");

			for (int i = 0; i < conversionsNodeList.getLength(); i++) {

				Node convNode = conversionsNodeList.item(i);

				if (convNode.getNodeType() == Node.ELEMENT_NODE) {

					Element convElement = (Element) convNode;

					xmlFile.hashConversionItem.put(convElement.getAttribute("id"),
							new ConversionItem(convElement.getAttribute("input"), convElement.getAttribute("output")));
				}
			}

			// Tag de configuraciòn del archivo de salida <output file>
			Element outputFileElement = (Element) doc.getElementsByTagName("outputfile").item(0);

			xmlFile.outputFileDetails = new OutputFileXMLDetail(
					outputFileElement.getAttribute("directory"),
					outputFileElement.getAttribute("extension"),
					outputFileElement.getAttribute("prename"),
					outputFileElement.getAttribute("postname"),
					Integer.parseInt(outputFileElement.getAttribute("correlative")),
					((Element) outputFileElement.getElementsByTagName("automatic").item(0)).getAttribute("create_on"),
					((Element) outputFileElement.getElementsByTagName("automatic").item(0)).getAttribute("close_on"),
					((Element) outputFileElement.getElementsByTagName("header").item(0)).getTextContent(),
					((Element) outputFileElement.getElementsByTagName("footer").item(0)).getTextContent());
		} catch (IOException e) {
			System.out.println(e);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		}
	}

}