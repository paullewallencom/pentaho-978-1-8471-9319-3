import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*;

import org.pentaho.reporting.engine.classic.core.ClassicEngineBoot;
import java.net.URL;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.libraries.resourceloader.Resource;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;
import org.pentaho.reporting.engine.classic.core.modules.gui.base.PreviewDialog;
import java.io.IOException;
import org.pentaho.reporting.libraries.resourceloader.ResourceException;

import org.pentaho.reporting.engine.classic.extensions.datasources.olap4j.connections.DriverConnectionProvider;
import org.pentaho.reporting.engine.classic.extensions.datasources.olap4j.BandedMDXDataFactory;

public class BandedMDXDataFactoryApp extends JFrame { 

	// constructor which displays the simple 
	// application shell
	
	public BandedMDXDataFactoryApp() { 
		super("BandedMDXDataFactoryApp"); 

		// exit the JVM when the window is closed

		this.addWindowStateListener(new WindowAdapter() { 
			public void windowClosed(WindowEvent e) { 
				System.exit(0); 
			} 
		});
		
		// display a preview and exit button in the 
		// main window of the example application

		add(new JLabel("Chapter 5 BandedMDXDataFactoryApp Application")); 
		 
		JPanel buttonPanel = new JPanel(); 
		JButton previewButton = new JButton("Preview"); 
		JButton exitButton = new JButton("Exit"); 

		buttonPanel.add(previewButton); 
		buttonPanel.add(exitButton); 
		 
		add(buttonPanel, BorderLayout.SOUTH);

		previewButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				onPreview(); 
			} 
		}); 
		 
		exitButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				System.exit(0);
			} 
		}); 
	} 

	// The onPreview method is called when the preview
	// button is pressed
	
	public void onPreview() { 
		// Load Report and Launch the Preview Dialog
		try {
			// load report definition
			ResourceManager manager = new ResourceManager();
			manager.registerDefaults();
			Resource res = manager.createDirectly(
				new URL("file:data/banded_mdx_report.prpt"), MasterReport.class);
			MasterReport report = (MasterReport) res.getResource();

			// load olap data

			DriverConnectionProvider provider = new  DriverConnectionProvider();
			provider.setDriver("mondrian.olap4j.MondrianOlap4jDriver");
			provider.setUrl("jdbc:mondrian:");
			provider.setProperty("Catalog", "data/libraryinfo.mondrian.xml");
			provider.setProperty("JdbcUser", "sa");
			provider.setProperty("JdbcPassword", "");
			provider.setProperty("Jdbc", "jdbc:hsqldb:file:data/libraryinfo");
			provider.setProperty("JdbcDrivers", "org.hsqldb.jdbcDriver");

			// create the factory

			BandedMDXDataFactory factory = new BandedMDXDataFactory(provider);

			// add the MDX query

			factory.setQuery("default", "WITH MEMBER [Measures].[Name] AS '[Library].CurrentMember.Caption' MEMBER [Measures].[Description] AS '[Library].CurrentMember.Properties(\"LibDescription\")' select [Library].Children on rows, {[Measures].[Name], [Measures].[Description], [Measures].[Size]} on columns from [Library Info]");

			report.setDataFactory(factory);
			
			final PreviewDialog preview = new PreviewDialog(report);
			preview.addWindowListener(new WindowAdapter() {
				public void windowClosing (final WindowEvent event) {
					preview.setVisible(false);
				}
			});
			preview.pack();
			preview.setVisible(true);

		} catch (ResourceException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
	 
	// the main method is the entry point into our application
	
	public static void main(String args[]) {
		
		// Initialize the Reporting Engine
		
		ClassicEngineBoot.getInstance().start();
		
		BandedMDXDataFactoryApp app = new BandedMDXDataFactoryApp(); 
		app.pack(); 
		app.setVisible(true); 
	} 
}
