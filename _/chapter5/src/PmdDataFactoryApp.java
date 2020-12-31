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

import org.pentaho.reporting.engine.classic.extensions.datasources.pmd.PmdDataFactory;
import org.pentaho.reporting.engine.classic.extensions.datasources.pmd.PmdConnectionProvider;

public class PmdDataFactoryApp extends JFrame { 

	// constructor which displays the simple 
	// application shell

	public PmdDataFactoryApp() { 
		super("PmdDataFactoryApp"); 

		// exit the JVM when the window is closed

		this.addWindowStateListener(new WindowAdapter() { 
			public void windowClosed(WindowEvent e) { 
				System.exit(0); 
			} 
		});
		
		// display a preview and exit button in the 
		// main window of the example application

		add(new JLabel("Chapter 5 PmdDataFactoryApp Application")); 
		 
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
				new URL("file:data/pmd_report.prpt"), MasterReport.class);
			MasterReport report = (MasterReport) res.getResource();
			
			// load metadata query
			PmdDataFactory factory = new PmdDataFactory();
			factory.setConnectionProvider(new PmdConnectionProvider());
			factory.setXmiFile("data/libraryinfo.xmi");
			factory.setDomainId("Library Info");
			factory.setQuery("default", 
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + 
			"<mql>" + 
			"	<domain_type>relational</domain_type>" + 
			"	<domain_id>Library Info</domain_id>" + 
			"	<model_id>LIBRARYINFO_MODEL</model_id>" + 
			"	<model_name>Library Info</model_name>" + 
			"	<selections>" + 
			"		<selection>" + 
			"			<view>BC_LIBRARYINFO</view>" + 
			"			<column>BC_LIBRARYINFO_NAME</column>" + 
			"		</selection>" + 
			"		<selection>" + 
			"			<view>BC_LIBRARYINFO</view>" + 
			"			<column>BC_LIBRARYINFO_DESCRIPTION</column>" + 
			"		</selection>" + 
			"		<selection>" + 
			"			<view>BC_LIBRARYINFO</view>" + 
			"			<column>BC_LIBRARYINFO_SIZE</column>" + 
			"		</selection>" + 
			"	</selections>" +
			"</mql>");
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
		
		PmdDataFactoryApp app = new PmdDataFactoryApp(); 
		app.pack(); 
		app.setVisible(true); 
	} 
}
