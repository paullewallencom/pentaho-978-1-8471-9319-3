import org.pentaho.reporting.engine.classic.core.metadata.ElementMetaDataParser;
import org.pentaho.reporting.libraries.base.boot.AbstractModule;
import org.pentaho.reporting.libraries.base.boot.ModuleInitializeException;
import org.pentaho.reporting.libraries.base.boot.SubSystem;

public class Chapter11Module extends AbstractModule {

	// Constructor.  This loads the module specification
	public Chapter11Module() throws ModuleInitializeException {
		loadModuleInfo();
	}

	// initialize the module by loading the expression metadata
	public void initialize(final SubSystem subSystem) throws ModuleInitializeException {
		ElementMetaDataParser.initializeOptionalExpressionsMetaData("meta-expressions.xml");
		ElementMetaDataParser.initializeOptionalElementMetaData("meta-elements.xml");
	}
}
