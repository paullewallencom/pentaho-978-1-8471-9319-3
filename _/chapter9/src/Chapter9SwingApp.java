import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.pentaho.reporting.engine.classic.core.ClassicEngineBoot;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.modules.gui.base.PreviewDialog;
import org.pentaho.reporting.engine.classic.core.modules.gui.base.event.ReportActionEvent;
import org.pentaho.reporting.engine.classic.core.modules.gui.base.event.ReportActionListener;
import org.pentaho.reporting.engine.classic.core.modules.gui.base.event.ReportHyperlinkEvent;
import org.pentaho.reporting.engine.classic.core.modules.gui.base.event.ReportHyperlinkListener;
import org.pentaho.reporting.engine.classic.core.modules.gui.base.event.ReportMouseEvent;
import org.pentaho.reporting.engine.classic.core.modules.gui.base.event.ReportMouseListener;
import org.pentaho.reporting.libraries.resourceloader.Resource;
import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;


public class Chapter9SwingApp extends JFrame { 

	// constructor which displays the simple 
	// application shell
	
	Integer sessionId = null;
	
	public Chapter9SwingApp() { 
		super("Chapter 9"); 

		// exit the JVM when the window is closed

		this.addWindowStateListener(new WindowAdapter() { 
			public void windowClosed(WindowEvent e) { 
				System.exit(0); 
			} 
		});
		
		// display a preview and exit button in the 
		// main window of the example application

		add(new JLabel("Chapter 9 Swing Application")); 
		 
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

	public MasterReport createReport() throws IOException, ResourceException {
		ResourceManager manager = new ResourceManager();
		manager.registerDefaults();
		Resource res = manager.createDirectly(
			new URL("file:data/interactive_swing.prpt"), MasterReport.class);
		MasterReport report = (MasterReport) res.getResource();

		if (sessionId != null) {
			report.getParameterValues().put("REPORT_PARAM_SESSIONID", sessionId);
		}
		
		return report;
	}
	

	// The onPreview method is called when the preview
	// button is pressed
	
	public void onPreview() { 
		// TODO: Load Report and Launch the Preview Dialog
		try {
			// load report definition
			MasterReport report = createReport();
			
			final PreviewDialog preview = new PreviewDialog(report);
			preview.addWindowListener(new WindowAdapter() {
				public void windowClosing (final WindowEvent event) {
					preview.setVisible(false);
				}
			});
			
			preview.getPreviewPane().getReportPreviewArea().addReportActionListener(new ReportActionListener() {
				public void reportActionPerformed(ReportActionEvent event) {
					Integer newSessionId = ((Number)event.getActionParameter()).intValue();
					if (!newSessionId.equals(sessionId)) {
						sessionId = newSessionId; 
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								try {
									preview.setReportJob(createReport());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
					}
				}
		    });

			preview.getPreviewPane().addReportHyperlinkListener(new ReportHyperlinkListener() {
				public void hyperlinkActivated(final ReportHyperlinkEvent event) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							JOptionPane.showMessageDialog(null, "Link Clicked: " + event.getTarget());
						}
					});
				}
		    });

			preview.getPreviewPane().getReportPreviewArea().addReportMouseListener(new ReportMouseListener() {
				  public void reportMouseClicked(ReportMouseEvent event) {
				    if (event.getSourceNode() != null && event.getSourceNode().getName() != null &&
				    		event.getSourceNode().getName().equals("Action1")) {
					JOptionPane.showMessageDialog(null, "Action 1 Rectangle Clicked");
				    } else if (event.getSourceNode() != null && 
				    		event.getSourceNode().getName() != null &&
				    		event.getSourceNode().getName().equals("Action2")) {
					JOptionPane.showMessageDialog(null, "Action 2 Rectangle Clicked");
				    }
				  }

				  public void reportMousePressed(ReportMouseEvent event) {}

				  public void reportMouseReleased(ReportMouseEvent event) {}
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
		
		// TODO: Initialize the Reporting Engine
		
		ClassicEngineBoot.getInstance().start();
		
		Chapter9SwingApp app = new Chapter9SwingApp(); 
		app.pack(); 
		app.setVisible(true); 
	} 
}
