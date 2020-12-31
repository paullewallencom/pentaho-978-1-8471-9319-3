import org.pentaho.reporting.engine.classic.core.modules.parser.bundle.layout.elements.AbstractElementReadHandler;
import org.pentaho.reporting.libraries.xmlns.parser.ParseException;

// this class handles reading in of the star element

public class StarReadHandler extends AbstractElementReadHandler {
	
  // all we need to do is pass the name of the element to our parent class
  public StarReadHandler() throws ParseException {
    super("star");
  }
}
