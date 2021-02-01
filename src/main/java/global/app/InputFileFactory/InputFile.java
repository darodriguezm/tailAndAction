package global.app.InputFileFactory;

import global.app.*;

public abstract class InputFile {
	private IReceptLine inputClass;
	
	public InputFile(IReceptLine classToCall){
		this.inputClass = classToCall;
	}
	
	public IReceptLine getInputClass(){
		return this.inputClass;
	}
	
	public abstract void leerArchivo();
}
