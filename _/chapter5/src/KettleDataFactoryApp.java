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

import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.util.EnvUtil;
import org.pentaho.di.job.JobEntryLoader;
import org.pentaho.di.trans.StepLoader;

import org.pentaho.reporting.engine.classic.core.ParameterMapping;
import org.pentaho.reporting.engine.classic.extensions.datasources.kettle.KettleTransFromFileProducer;
import org.pentaho.reporting.engine.classic.extensions.datasources.kettle.KettleDataFactory;

public class KettleDataFactoryApp extends JFrame { 

	// constructor which displays the simple 
	// application shell

	public KettleDataFactoryApp() { 
		super("KettleDataFactoryApp"); 

		// exit the JVM when the window is closed

		this.addWindowStateListener(new WindowAdapter() { 
			public void windowClosed(WindowEvent e) { 
				System.exit(0); 
			} 
		});
		
		// display a preview and exit button in the 
		// main window of the example application

		add(new JLabel("Chapter 5 KettleDataFactoryApp Application")); 
		 
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
				new URL("file:data/kettle_report.prpt"), MasterReport.class);
			MasterReport report = (MasterReport) res.getResource();
			
			// load Kettle data source

			// Initialize Kettle
			EnvUtil.environmentInit();
			StepLoader.init();
			JobEntryLoader.init();
			
			// Build Data Factory
			KettleTransFromFileProducer producer = new KettleTransFromFileProducer("Embedded Repository", "data/libraryinfo.ktr", "Table input", "", "", new String[0], new ParameterMapping[0]);
			KettleDataFactory factory = new KettleDataFactory();
			factory.setQuery("default", producer);
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
		} catch (KettleException e) {
			e.printStackTrace();
		}
	} 
	 
	// the main method is the entry point into our application
	
	public static void main(String args[]) {
		
		// Initialize the Reporting Engine
		
		ClassicEngineBoot.getInstance().start();
		
		KettleDataFactoryApp app = new KettleDataFactoryApp(); 
		app.pack(); 
		app.setVisible(true); 
	} 
}
