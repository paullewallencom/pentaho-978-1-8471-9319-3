import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.pentaho.reporting.libraries.formula.EvaluationException;
import org.pentaho.reporting.libraries.formula.FormulaContext;
import org.pentaho.reporting.libraries.formula.LibFormulaErrorValue;
import org.pentaho.reporting.libraries.formula.function.Function;
import org.pentaho.reporting.libraries.formula.function.ParameterCallback;
import org.pentaho.reporting.libraries.formula.lvalues.TypeValuePair;
import org.pentaho.reporting.libraries.formula.typing.TypeRegistry;
import org.pentaho.reporting.libraries.formula.typing.coretypes.TextType;

public class RegexFunction implements Function {

	// This method evaluates the regular expression function
	public TypeValuePair evaluate(FormulaContext context,
			ParameterCallback parameters) throws EvaluationException {
		// throw an exception if the function doesn’t have 
		// both parameters
	    if (parameters.getParameterCount() != 2)
	    {
	      throw new EvaluationException(LibFormulaErrorValue.ERROR_ARGUMENTS_VALUE);
	    }

	    final TypeRegistry typeRegistry = context.getTypeRegistry();
	    final String param1 = typeRegistry.convertToText(parameters.getType(0), parameters.getValue(0));
	    final String param2 = typeRegistry.convertToText(parameters.getType(1), parameters.getValue(1));
	    try {
			// create a pattern based on the regex input
			final Pattern p = Pattern.compile(param1);
			final Matcher m = p.matcher(param2);
			// find the first match in the string
			m.find();
			// return the first group found within the match
			return new TypeValuePair(TextType.TYPE, m.group(1));
		} catch (Exception e) {
			// appropriate way to log a error / warning message?
			return new TypeValuePair(TextType.TYPE, e.getMessage());
		}
	}

	public String getCanonicalName() {
		return "REGEX";
	}
}
