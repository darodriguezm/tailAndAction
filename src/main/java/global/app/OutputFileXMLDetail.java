package global.app;

/**
 * 
 */
public class OutputFileXMLDetail {
    
    public OutputFileXMLDetail(String pDirectory, String pExtension, String pPrename, String pPostname, int pCorrelative, String pAutomaticCreateExpression, String pAutomaticCloseExpression, String pHeader, String pFooter){
		this.directory = pDirectory;
		
		if (pExtension.indexOf(".") == -1) {
			this.extension = "." + pExtension;
		} else {
			this.extension = pExtension;
		}
		
        this.prename = pPrename;
        this.postname = pPostname;
        this.correlative = pCorrelative;
        this.automaticCreateExpression = pAutomaticCreateExpression;
        this.automaticCloseExpression = pAutomaticCloseExpression;
        this.header = pHeader;
        this.footer = pFooter;
    }

    private String directory;
    private String extension;
    private String prename;
    private String postname;
	private int correlative;
	private String automaticCreateExpression;
    private String automaticCloseExpression;
    private String header;
    private String footer;

    public String getDirectory(){
    	return directory;
    }
    
    public String getExtension(){
        return extension;
    }

    public String getPrename(){
        return prename;
    }

    public String getPostname() {
		return postname;
	}
    
    public int getCorrelative() {
		return correlative;
	}
    
    public String getAutomaticCreateExpression(){
        return automaticCreateExpression;
    }
    
    public String getAutomaticCloseExpression(){
        return automaticCloseExpression;
    }
    
    public String getHeader(){
        return header;
    }

    public String getFooter(){
        return footer;
    }

}