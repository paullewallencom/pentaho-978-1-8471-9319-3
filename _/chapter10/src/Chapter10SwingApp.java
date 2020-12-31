import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*;

import javax.swing.table.DefaultTableModel;

import org.pentaho.reporting.engine.classic.core.ClassicEngineBoot;
import org.pentaho.reporting.engine.classic.core.modules.gui.base.PreviewDialog;

import org.pentaho.reporting.engine.classic.core.Element;
import org.pentaho.reporting.engine.classic.core.ItemBand;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.ReportFooter;
import org.pentaho.reporting.engine.classic.core.ReportHeader;
import org.pentaho.reporting.engine.classic.core.TableDataFactory;
import org.pentaho.reporting.engine.classic.core.elementfactory.LabelElementFactory;
import org.pentaho.reporting.engine.classic.core.elementfactory.NumberFieldElementFactory;
import org.pentaho.reporting.engine.classic.core.elementfactory.TextFieldElementFactory;
import org.pentaho.reporting.engine.classic.core.function.FormulaExpression;


public class Chapter10SwingApp extends JFrame { 

	// constructor which displays the simple 
	// application shell
	
	public Chapter10SwingApp() { 
		super("Chapter 10"); 

		// exit the JVM when the window is closed

		this.addWindowStateListener(new WindowAdapter() { 
			public void windowClosed(WindowEvent e) { 
				System.exit(0); 
			} 
		});
		
		// display a preview and exit button in the 
		// main window of the example application

		add(new JLabel("Chapter 10 Swing Application")); 
		 
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

		// load report definition
		// create a new report object

		MasterReport report = new MasterReport();

		// add a table data source to the report

		DefaultTableModel tableModel = new DefaultTableModel(
				 new Object[][] {
						 {"libloader", 114287},
						 {"libformula", 331839}
				 },
				 new String[] {"Name", "Size"}
		);
		final TableDataFactory dataFactory = new TableDataFactory();
		dataFactory.addTable("default", tableModel);
		report.setDataFactory(dataFactory);

		// add a formula expression to the report

		FormulaExpression formula = new FormulaExpression();
		formula.setName("SizeKilobytes");
		formula.setFormula("=[Size] / 1024");
		report.addExpression(formula);

		// add the report header and footer

		ReportHeader reportHeader = new ReportHeader();
		ReportFooter reportFooter = new ReportFooter();
		report.setReportHeader(reportHeader);
		report.setReportFooter(reportFooter);

		// add the item band, with a shortcut that  
		// handles creating the default group

		ItemBand itemBand = new ItemBand();
		report.setItemBand(itemBand);

		// create a label and add it to the header

		LabelElementFactory labelFactory = new LabelElementFactory();

		labelFactory.setText("Library Information");
		labelFactory.setX(1f);
		labelFactory.setY(1f);
		labelFactory.setMinimumWidth(100f);
		labelFactory.setMinimumHeight(20f);
		labelFactory.setBold(true);

		Element label = labelFactory.createElement();
		reportHeader.addElement(label);
			
		// Add a text field for the library name, added to 
		// the item band

		TextFieldElementFactory textFactory = new TextFieldElementFactory();
		textFactory.setFieldname("Name");
		textFactory.setX(1f);
		textFactory.setY(1f);
		textFactory.setMinimumWidth(100f);
		textFactory.setMinimumHeight(20f);
		Element nameField = textFactory.createElement();
		itemBand.addElement(nameField);

		// Add a number field for the library size, added to
		// the item band 

		NumberFieldElementFactory numberFactory = new NumberFieldElementFactory();
		numberFactory.setFieldname("SizeKilobytes");
		numberFactory.setX(101f);
		numberFactory.setY(1f);
		numberFactory.setMinimumWidth(100f);
		numberFactory.setMinimumHeight(20f);
		Element sizeField = numberFactory.createElement();
		itemBand.addElement(sizeField);

		
		final PreviewDialog preview = new PreviewDialog(report);
		preview.addWindowListener(new WindowAdapter() {
			public void windowClosing (final WindowEvent event) {
				preview.setVisible(false);
			}
		});
		preview.pack();
		preview.setVisible(true);
	} 
	 
	// the main method is the entry point into our application
	
	public static void main(String args[]) {
		
		// Initialize the Reporting Engine
		
		ClassicEngineBoot.getInstance().start();
		
		Chapter10SwingApp app = new Chapter10SwingApp(); 
		app.pack(); 
		app.setVisible(true); 
	} 
}
