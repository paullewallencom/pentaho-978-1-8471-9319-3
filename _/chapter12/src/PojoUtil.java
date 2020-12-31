import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.pentaho.reporting.engine.classic.core.ClassicEngineBoot;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.ReportProcessingException;
import org.pentaho.reporting.engine.classic.core.modules.output.table.base.StreamReportProcessor;
import org.pentaho.reporting.libraries.resourceloader.Resource;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;

public class PojoUtil {

// The createPojoReport method generates a PojoObject 
// list from a report.

	public static List<PojoOutputProcessor.PojoObject> createPojoReport(final MasterReport report)
	      throws ReportProcessingException, IOException {

// Instantiate a target PojoOutputProcessor class,
// passing in the report configuration.

		final PojoOutputProcessor target = new PojoOutputProcessor(report.getConfiguration());

// Instantiate a StreamReportProcessor with references 
// to the report and the OutputProcessor.

		final StreamReportProcessor reportProcessor = new StreamReportProcessor(report, target);

// Process the report.

		reportProcessor.processReport();

// Close the report after processing.

		reportProcessor.close();

// Return a list of the plain old Java objects 
// generated from this report.

		return target.getPojoObjects();
	}

// The main method generates a plain old Java object 
// output from the simple report defined earlier.

	public static void main(final String[] args) throws Exception {

// Boot the reporting engine.

		ClassicEngineBoot.getInstance().start();

// Load the report PRPT file.

	   ResourceManager manager = new ResourceManager();
	   manager.registerDefaults();
	   Resource res = manager.createDirectly(new URL("file:data/metadata_table.prpt"), MasterReport.class);
	    MasterReport report = (MasterReport) res.getResource();

// Generate the PojoObject list.

	    List<PojoOutputProcessor.PojoObject> objs = PojoUtil.createPojoReport(report);

// Write the PojoObject list to System.out as an example.

	    for (PojoOutputProcessor.PojoObject obj : objs) {
	    	System.out.println("" + obj.x + "," + obj.y + ": " + obj.text);
	    }	
   }
}
