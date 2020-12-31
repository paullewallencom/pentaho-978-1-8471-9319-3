import org.pentaho.reporting.engine.classic.core.layout.output.AbstractOutputProcessorMetaData;
import org.pentaho.reporting.libraries.base.config.Configuration;

public class PojoOutputMetaData extends AbstractOutputProcessorMetaData {

// Define a basic constructor that calls it’s super.

	public PojoOutputMetaData(final Configuration configuration) {
		super(configuration);
	}

// Provide an export descriptor.

	public String getExportDescriptor() {
		return "stream/pojo";
	}
}
