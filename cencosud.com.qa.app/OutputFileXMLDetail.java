package cencosud.com.qa.app;

/**
 * 
 */
public class OutputFileXMLDetail {
    
    public OutputFileXMLDetail(String pExtension, String pPrename, String pAutomaticCreateExpression, String pAutomaticCloseExpression, String pHeader, String pFooter){
        this.extension = pExtension;
        this.prename = pPrename;
        this.automaticCreateExpression = pAutomaticCreateExpression;
        this.automaticCloseExpression = pAutomaticCloseExpression;
        this.header = pHeader;
        this.footer = pFooter;
    }

    private String extension;
    private String prename;
    private String automaticCreateExpression;
    private String automaticCloseExpression;
    private String header;
    private String footer;

    public void getExtension(){
        return extension;
    }

    public void getPrename(){
        return prename;
    }

    
    public void getAutomaticCreateExpression(){
        return automaticCreateExpression;
    }
    
    public void getAutomaticCloseExpression(){
        return automaticCloseExpression;
    }
    
    public void getHeader(){
        return header;
    }

    public void getFooter(){
        return footer;
    }

}