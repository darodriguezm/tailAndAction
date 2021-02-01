package global.app;

import java.util.*;

/**
 * 
 */
public class CheckClass {

 /**
  * Default constructor
  */
 public CheckClass() {
 }

  /**
  * @param pPattern 
  * @param pMatch 
  * @param pSubMatchLevel
  */
 public CheckClass(String pPattern, String pMatch, int pSubMatchLevel) {
  // TODO implement here
  this.pattern = pPattern;
  this.match = pMatch;
  this.subMatchLevel = pSubMatchLevel;
  this.parameters = new ArrayList<ParametersCheckClass>();

 }
 
 /**
  * 
  */
 public String pattern;

 /**
  * 
  */
 public String match;

 /**
  * 
  */
 public Integer subMatchLevel;

 /**
  * 
  */
 public ArrayList<ParametersCheckClass> parameters;

}