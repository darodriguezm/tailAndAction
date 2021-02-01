package global.app;

import java.util.*;

/**
 * 
 */
public class ConversionItem {

  
 String input;
 String output;
 /**
  * Default constructor
  */
 public ConversionItem() {
 }

 public ConversionItem(String pInput, String pOutput){
  this.input = pInput;
  this.output = pOutput;
 }

 /**
  * 
  */
 public String getInput(){
  return this.input;
 }

 /**
  * 
  */
 public String getOutput(){
  return this.output;
 }



}