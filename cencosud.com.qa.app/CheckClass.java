
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





	/**
	 * @param pPattern 
	 * @param pMatch 
	 * @param pSubMatchLevel
	 */
	public void CheckClass(String pPattern, String pMatch, Integer pSubMatchLevel) {
		// TODO implement here
		this.pattern = pPattern;
		this.match = pMatch;
		this.subMatchLevel = pSubMatchLevel;
		this.parameters<ParametersCheckClass> = new ArrayList<>();
	}

}