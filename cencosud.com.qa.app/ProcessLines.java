package cencosud.com.qa.app;

import java.util.regex.*;

/**
 * 
 */
public class ProcessLines implements IReceptLine {

	/**
	 * @param outputFileTarjet
	 */
	public void ProcessLines(IOutputResult pOutputFileTarjet) {
		// TODO implement here
		this.outputFileTarjet = pOutputFileTarjet;
	}
	
	/**
	 * 
	 */
	private File inputFile;
	private IOutputResult outputFileTarjet;


	/**
	 * 
	 */
	public void String doProcessLine(String lineToProcess) {
		TAAXmlFile xmlIntance = TAAXmlFile.getInstance();
		
		// TODO implement here
		// Se evalúa la expresión regular para iniciar la apertura automática del archivo de salida para escritura
		if (xmlIntance.outputFileDetails.getAutomaticCreateExpression() != null && Pattern.matches(xmlIntance.outputFileDetails.getAutomaticCreateExpression(), lineToProcess)){
			outputFileTarjet.doProcessOutput(EOutputOperations.CREATE, xmlIntance.outputFileDetails.getHeader());
		}
		
		// Se evalúa la expresión regular para el cierre automático del archivo de salida
		if (xmlIntance.outputFileDetails.getAutomaticCloseExpression() != null && Pattern.matches(xmlIntance.outputFileDetails.getAutomaticCloseExpression(), lineToProcess)){
			outputFileTarjet.doProcessOutput(EOutputOperations.CLOSE, xmlIntance.outputFileDetails.getFooter());
		}

		// Se evalúa con la expresion regular inicial de cada checkcer almacenada en la instancia del XML leído
		for (int i = 0; i < xmlIntance.checksArray.size(); i++) {
			
			Pattern pa = Pattern.compile(xmlIntance.checksArray.get(i).pattern);
			Matcher ma = pa.matcher(lineToProcess);

			if (ma.matches()){
				// Se crea la variable de salida con el string definido en el xml como retorno en caso de cumplir con el patrón
				String outputLineString = xmlIntance.checksArray.get(i).match;
				String outputLineForSubProcess;

				if (xmlIntance.checksArray.get(i).subMatchLevel == 0 || xmlIntance.checksArray.get(i).subMatchLevel == null){
					// subMatchLevel 0: Evalúa sobre la conicidencia del checker
					outputLineForSubProcess = ma.group(1);
					
				}else if (xmlIntance.checksArray.get(i).subMatchLevel == 1){
					// subMatchLevel 1: Evalúa sobre la conicidencia de toda la lìnea
					outputLineForSubProcess = lineToProcess;
				}
				
				// Recorremos los parámetros definidos dentro del checker en proceso
				for (int j = 0; j < xmlIntance.checksArray.get(i).parameters.size(); j++) {
					Pattern subpa = Pattern.compile(xmlIntance.checksArray.get(i).parameters.get(j).getPattern());
					Matcher subma = subpa.matcher(outputLineForSubProcess);

					if (subma.matches() || xmlIntance.checksArray.get(i).parameters.get(j).getConv_id().length() > 0) {
						if (xmlIntance.checksArray.get(i).parameters.get(j).getConv_id().indexOf("*") != -1){
							// Se indicò * en el conv_id del parámetro
							// Se recorren y se aplican todos los items de conversión

							for (int c = 0; c < xmlIntance.hashConversionItem.size(); c++) {
								Collection<ConversionItem> conversionItemValues = xmlIntance.hashConversionItem.values();
								ConversionItem[] conversionItemArray = conversionItemValues.toArray();
								
							}


						}else{
							// Se indicáron los items de conversión explicitamente
							// Se recorren y aplican los items especificados

						}
				}


				outputFileTarjet.doProcessOutput(EOutputOperations.WRITE, outputLineString);
				break;
			};
		}
	}

}