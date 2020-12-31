import java.net.URL;

import javax.swing.table.TableModel;

import org.pentaho.reporting.engine.classic.core.ClassicEngineBoot;
import org.pentaho.reporting.engine.classic.core.CompoundDataFactory;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.TableDataFactory;
import org.pentaho.reporting.engine.classic.core.modules.output.pageable.pdf.PdfReportUtil;
import org.pentaho.reporting.libraries.resourceloader.Resource;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;

public class MetaTableModelDemo {

// The main method generates a PDF based on the defined
// report along with the DefaultMetaTableModel implementation.

	public static void main(final String[] args) throws Exception {

// Boot the engine and load the report.

		ClassicEngineBoot.getInstance(). start();
		ResourceManager manager = new ResourceManager();
		manager.registerDefaults();
		Resource res = manager.createDirectly(new URL("file:data/metadata_table.prpt"), MasterReport.class);
		MasterReport report = (MasterReport) res.getResource();
		    
// Get a reference to the default TableModel defined in 
// the report designer.

		CompoundDataFactory factory = (CompoundDataFactory)report.getDataFactory();
		TableDataFactory tableFactory = (TableDataFactory)factory.getReference(0);
		TableModel tableModel = tableFactory.queryData("default", null);

// create a MetaTableModel based on the regular table model

		DefaultMetaTableModel metaTableModel = new DefaultMetaTableModel(tableModel);
		    
// Add bg-color and exampleLabel metadata values as part of
// the exampleDomain for the first column of data.

		metaTableModel.setColumnAttribute(0, "exampleDomain", "bg-color", "#abcdef");
		metaTableModel.setColumnAttribute(0, "exampleDomain", "exampleLabel", "Field Label");

// Overwrite the earlier table model with the new
// MetaTableModel.

		tableFactory.addTable("default", metaTableModel);

// Generate a PDF output of the report.

		PdfReportUtil.createPDF(report, "metadata_table.pdf");
	}
}
