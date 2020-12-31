import org.pentaho.reporting.libraries.formula.function.AbstractFunctionDescription;
import org.pentaho.reporting.libraries.formula.function.FunctionCategory;
import org.pentaho.reporting.libraries.formula.function.userdefined.UserDefinedFunctionCategory;
import org.pentaho.reporting.libraries.formula.typing.Type;
import org.pentaho.reporting.libraries.formula.typing.coretypes.TextType;

public class RegexFunctionDescription extends AbstractFunctionDescription {

	public RegexFunctionDescription() {
		// make sure to call the super constructor, with 
		// the function name and the function resource bundle
		super("REGEX", "Regex-Function");
	}
	
	// place this function in the user defined category
	public FunctionCategory getCategory() {
		// TODO Auto-generated method stub
		return UserDefinedFunctionCategory.CATEGORY;
	}

	// this function requires two parameters, 
	// regex and input string
	public int getParameterCount() {
		return 2;
	}

	// both of the parameters are of type text
	public Type getParameterType(int position) {
		return TextType.TYPE;
	}
	// the output type is of type text
	public Type getValueType() {
		return TextType.TYPE;
	}

	// both parameters are required for execution
	public boolean isParameterMandatory(int position) {
		return true;
	}
}
