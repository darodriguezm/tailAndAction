package global.app.InputFileFactory;

import global.app.*;

public class InputFileFactory {

	public static InputFile getInputFile(int tipo, IReceptLine process) {
		
		switch (tipo) {
		case 0:
			// Se crea el objeto de lectura secuencial activada por cambios
			return new LiveInputFile(process);

		case 1:
			// Se crea el objeto de lectura completa del archivo estático
			return new FullInputFile(process);
		}
		
		return null;
	}

}
