package global.app;

/**
 * En esta interfaz se define el método que recibe el texto resultante para procesar en el archivo de salida luego de la evaluación y conversión de la cadena inicial.
 * También se definen los posibles valores que se podrían tomar para realizar las acciones sobre el archivo inicial de acuerdo a lo configurado inicialmente en el archivo xml.
 * Esto es:
 *     0: Crear archivo de salida
 *     1: Cerrar archivo de salida
 *     2: Escribir en el archivo de salidael texto indicado
 */
public interface IOutputResult {


	/**
	 * @param operation 
	 * @param processResult
	 */
	public void doProcessOutput(EOutputOperations operation, String processResult, int lineNumber);

}