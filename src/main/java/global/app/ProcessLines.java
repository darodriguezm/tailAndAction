package global.app;

import java.util.regex.*;
import java.util.Iterator;
import java.util.Collection;

/**
 * 
 */
public class ProcessLines implements IReceptLine {

	/**
	* 
	*/
	private IOutputResult outputFileTarjet;
	private int lineCounter;

	/**
	 * @param outputFileTarjet
	 */
	public ProcessLines(IOutputResult pOutputFileTarjet) {
		// TODO implement here
		this.outputFileTarjet = pOutputFileTarjet;
		lineCounter = 0;
	}

	/**
	 * 
	 */
	public void doProcessLine(String lineToProcess) {

		TAAXmlFile xmlIntance = TAAXmlFile.getInstance();
		lineCounter++;

		// TODO implement here
		if (xmlIntance.outputFileDetails.getAutomaticCreateExpression() != ""
				&& Pattern.compile(xmlIntance.outputFileDetails.getAutomaticCreateExpression()).matcher(lineToProcess).find()) {
			// Se evalúa la expresión regular para iniciar la apertura automática
			// del archivo de salida para escritura
			
			outputFileTarjet.doProcessOutput(EOutputOperations.CREATE, xmlIntance.outputFileDetails.getHeader(), lineCounter);
			
		} else if (xmlIntance.outputFileDetails.getAutomaticCloseExpression() != ""
				&& Pattern.compile(xmlIntance.outputFileDetails.getAutomaticCloseExpression()).matcher(lineToProcess).find()) {
			// Se evalúa la expresión regular para el cierre automático del
			// archivo de salida
			
			outputFileTarjet.doProcessOutput(EOutputOperations.CLOSE, xmlIntance.outputFileDetails.getFooter(), lineCounter);
			
		} else {
			// Se evalúa con la expresion regular inicial de cada checkcer
			// almacenada en la instancia del XML leído
			
			for (int i = 0; i < xmlIntance.checksArray.size(); i++) {

				Pattern pa = Pattern.compile(xmlIntance.checksArray.get(i).pattern);
				Matcher ma = pa.matcher(lineToProcess);

				if (ma.find()) {
					// Se crea la variable de salida con el string definido en
					// el
					// xml como retorno en caso de cumplir con el patrón
					String outputLineString = xmlIntance.checksArray.get(i).match;
					String outputLineForSubProcess = "";

					if (xmlIntance.checksArray.get(i).subMatchLevel == 0
							|| xmlIntance.checksArray.get(i).subMatchLevel == null) {
						// subMatchLevel 0: Evalúa sobre la conicidencia del
						// checker
						outputLineForSubProcess = ma.group(0);

					} else if (xmlIntance.checksArray.get(i).subMatchLevel == 1) {
						// subMatchLevel 1: Evalúa sobre la conicidencia de
						// toda la
						// lìnea
						outputLineForSubProcess = lineToProcess;
					}

					// Recorremos los parámetros definidos dentro del checker
					// en
					// proceso
					for (int j = 0; j < xmlIntance.checksArray.get(i).parameters.size(); j++) {
						String patternParamInString = "";

						Pattern subpa = Pattern.compile(xmlIntance.checksArray.get(i).parameters.get(j).getPattern());
						Matcher subma = subpa.matcher(outputLineForSubProcess);

						if (subma.find() || xmlIntance.checksArray.get(i).parameters.get(j).getConv_id().length() > 0) {

							patternParamInString = subma.group(0);

							if (xmlIntance.checksArray.get(i).parameters.get(j).getConv_id().indexOf("*") != -1) {
								// Se indicò * en el conv_id del parámetro
								// Se recorren y se aplican todos los items de
								// conversión

								Collection<ConversionItem> conversionItemValues = xmlIntance.hashConversionItem
										.values();
								Iterator<ConversionItem> conversionItemIterator = conversionItemValues.iterator();

								while (conversionItemIterator.hasNext()) {
									ConversionItem cit = (ConversionItem) conversionItemIterator.next();
									patternParamInString = patternParamInString.replaceAll(cit.getInput(),
											cit.getOutput());
								}

							} else {
								// Se indicáron los items de conversión
								// explicitamente, separados por ,
								// Se recorren y aplican los items especificados
								if (xmlIntance.checksArray.get(i).parameters.get(j).getConv_id().length() > 0) {

									String[] conv_idArray = xmlIntance.checksArray.get(i).parameters.get(j).getConv_id()
											.split(",");

									for (int cId = 0; cId < conv_idArray.length; cId++) {
										ConversionItem conversionItemReference = xmlIntance.hashConversionItem
												.get(conv_idArray[cId]);
										patternParamInString = patternParamInString.replaceAll(
												conversionItemReference.getInput(),
												conversionItemReference.getOutput());
									}

								}
							}
						}

						outputLineString = outputLineString.replaceAll(
								xmlIntance.checksArray.get(i).parameters.get(j).getName(), patternParamInString);
					}

					outputFileTarjet.doProcessOutput(EOutputOperations.WRITE, outputLineString, lineCounter);

					break;
				}
			}
		}
	}
}