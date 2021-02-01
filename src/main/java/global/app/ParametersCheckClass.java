package global.app;

import java.util.*;

/**
 * 
 */
public class ParametersCheckClass {

 String name;
 String pattern;
 String conv_id;

 /**
  * Default constructor
  */
 public ParametersCheckClass() {
 }

 public ParametersCheckClass(String pName, String pPattern, String pConv_id) {
  this.name = pName;
  this.pattern = pPattern;
  this.conv_id = pConv_id;
 }
 /**
  * 
  */
 public String getName(){
  return this.name;
 }

 /**
  * 
  */
 public String getPattern(){
  return this.pattern;
 }

 /**
  * 
  */
 public String getConv_id(){
  return this.conv_id;
 }


}