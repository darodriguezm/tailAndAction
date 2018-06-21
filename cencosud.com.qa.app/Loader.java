package cencosud.com.qa.app;

import java.util.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

/**
 * De acuerdo al parámetro <mode> del TAAXmlFile se crea una instancia de LiveInputfile o FullInputFile.
 * Al crear esta instancia y se le pasa como parámetro la instancia de la clase ProcessLines.
 * En estas clases (LiveInputfile o FullInputFile) se llama a la función doProcessLine de la clase que implementa la interfaz IReceptLine.
 */
public class Loader implements IOutputResult {

 /**
  * Default constructor
  */
 public Loader() {
   IReceptLine readerInputFile;
   xmlFile = TAAXmlFile.getInstance();
   outputfile = new OutputFile();
   process = new ProcessLines(this);

   readXMLFile();

   if (xmlFile.mode = 0){
     //Se crea el objeto de lectura secuencial activada por cambios
    readerInputFile = new LiveInputfile(process);
   }else{
     //Se crea el objeto de lectura completa del archivo estático
    readerInputFile = new FullInputFile(process);
   }
   
 }

 /**
  * 
  */
 private TAAXmlFile xmlFile;
 private ProcessLines process;
 private OutputFile outputFile;

 /**
  * 
  */
 public void readXMLFile() {
  // TODO implement here
   try{
     File fisicXMLFile = new File("/config.xml");
     DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
     DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
     Document doc = dBuilder.parse(fisicXMLFile);
     
     doc.getDocumentElement().normalize();
     
    //Parseo de los tag del archivo
     //Tag de configuraci�n inicial <config>
     Element configNode = doc.getDocumentElement().getNodeName().getElementsByTagName("config");
     xmlFile.inputFilePath = configNode.getElementsByTagName("origin").item(0).getTextContent();
     xmlFile.lineStacked = Integer.parseInt(configNode.getElementsByTagName("linesstacked").item(0).getTextContent());
     xmlFile.mode = Integer.parseInt(configNode.getElementsByTagName("mode").item(0).getTextContent());
     
     //Tags con expresiones regulares de coincidencia inicial <expressions>
     NodeList expressionsNodeList = doc.getDocumentElement().getNodeName().getElementsByTagName("expressions").getElementsByTagName("check");

     for (int i = 0; i < expressionsNodeList.getLength(); i++){

       Node checkNodeList = expressionsNodeList.item(i);
       Element checkElement = (Element) checkNodeList;
       
       CheckClass tempCheckItem = new CheckClass(
            checkElement.getAttribute("pattern"),
            checkElement.getAttribute("match"),
            checkElement.getAttribute("submarchlevel")
          );
        
        //Tag de parámetros de cada check
        NodeList parametersNodeList = checkNodeList.getChildNodes();
        
        for (int j = 0; j < parametersNodeList.getLength(); j++){
          Node parametersNode = parametersNodeList.item(j);
          Element parameterElement = (Element) parametersNode;
          
          tempCheckItem.parameters.add( new ParametersCheckClass(
              parameterElement.getNodeName(),
              parameterElement.getAttribute("pattern"),
              parameterElement.getAttribute("conv_id")
            );
          )

        }
        
      }
      
      //Tag de string de conversion según su ID <conversions>
      NodeList conversionsNodeList = doc.getDocumentElement().getNodeName().getElementsByTagName("conversions").getElementsByTagName("conv");
     
      for (int i = 0; i < conversionsNodeList.getLength(); i++){
        Node convNode = conversionsNodeList.item(i);
        Element convElement = (Element) convNode;

        HashMap<String, ConversionItem> tempHashConversionItem = new HashMap<>();
        tempHashConversionItem.put(
          convElement.getAttribute("id"),
          new ConversionItem(
            convElement.getAttribute("input"),
            convElement.getAttribute("output")
          ));
      }

      //Tag de configuraciòn del archivo de salida <output file>
      Element outputFileElement = doc.getDocumentElement().getNodeName().getElementsByTagName("outputfile");
      xmlFile.outputFileDetails = new outputFileDetails(
        outputFileElement.getAttribute("extension"),
        outputFileElement.getAttribute("prename"),
        outputFileElement.getElementsByTagName("automatic").getAttribute("create_on"),
        outputFileElement.getElementsByTagName("automatic").getAttribute("close_on"),
        outputFileElement.getElementsByTagName("header").getTextContent(),
        outputFileElement.getElementsByTagName("footer").getTextContent()
      );
     
   }catch(IOException e){
     System.out.println(e);
   }
 }

 /**
  * @param operation 
  * @param processResult
  */
 public void doProcessOutput(EOutputOperations operation, String processResult) {
  // TODO implement here
 }

}