import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.pentaho.reporting.engine.classic.core.function.AbstractExpression;

public class RegexExpression extends AbstractExpression {
	public RegexExpression() {
	}
	// add properties here
	
	private String regex;
	private String field;
	public void setRegex(String regex) {
		this.regex = regex;
	}
	public String getRegex() {
		return regex;
	}
	public void setField(String field) {
	this.field = field;
	}
	public String getField() {
		return field;
	}

	// add getValue here
	public Object getValue() {
		// wrap the regex code in a try catch, so that an
	   // error message can be presented if the parsing failed.
		try {
			// create a pattern based on the regex input
			final Pattern p = Pattern.compile(regex);
			
		// lookup the field, and if not null, create a matcher
			final Object o = getDataRow().get(getField());
			if (o == null) {
				return null;
			}
			final Matcher m = p.matcher(o.toString());
			
		// find the first match in the string
			m.find();
			
		// return the first group found within the match
			return m.group(1);
		} catch (Exception e) {
			// return the error message instead
			return e.getMessage();
		}
	}

}
