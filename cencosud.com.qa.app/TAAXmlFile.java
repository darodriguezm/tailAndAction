package cencosud.com.qa.app;

import java.util.*;

/**
 * 
 */
public final class TAAXmlFile {
//Clase Singleton
private static instanciaXMLFile = new TAAXmlFile();

 /**
  * Default constructor
  */
 private TAAXmlFile() {
 }

 public static TAAXmlFile getInstance(){
     return instanciaXMLFile;
 }
 /**
  * 
  */
 public HashMap<String, ConversionItem> hashConversionItem;

 /**
  * 
  */
 public String inputfilePath;

 /**
  * 
  */
 public Integer lineStacked;

 /**
  * 
  */
 public Integer mode;

 /**
  * 
  */
 public ArrayList<CheckClass> checksArray;

 public OutputFileXMLDetail outputFileDetails;


}