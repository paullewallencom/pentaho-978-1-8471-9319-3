import org.pentaho.reporting.engine.classic.core.metadata.ElementMetaDataParser;
import org.pentaho.reporting.libraries.base.boot.AbstractModule;
import org.pentaho.reporting.libraries.base.boot.ModuleInitializeException;
import org.pentaho.reporting.libraries.base.boot.SubSystem;

public class Chapter12Module extends AbstractModule {

	// Constructor.  This loads the module specification
	public Chapter12Module() throws ModuleInitializeException {
		loadModuleInfo();
	}
	
	// no custom initialization required
	public void initialize(final SubSystem subSystem) throws ModuleInitializeException {
	}
}
