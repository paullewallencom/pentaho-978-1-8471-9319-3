import java.io.File;
import java.io.IOException; 
import javax.servlet.*; 
import javax.servlet.http.*; 

import java.net.URL;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.ClassicEngineBoot;
import org.pentaho.reporting.engine.classic.core.ReportProcessingException;
import org.pentaho.reporting.engine.classic.core.modules.output.pageable.pdf.PdfReportUtil;
import org.pentaho.reporting.engine.classic.core.modules.output.table.html.HtmlReportUtil;
import org.pentaho.reporting.engine.classic.core.modules.output.table.rtf.RTFReportUtil;
import org.pentaho.reporting.engine.classic.core.modules.output.table.xls.ExcelReportUtil;
//import org.jfree.report.ReportProcessingException;
//import org.jfree.report.modules.output.pageable.pdf.PdfReportUtil; 
import org.pentaho.reporting.libraries.resourceloader.Resource;
import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;


public class DashboardServlet extends HttpServlet { 

	// servlet initialization method
	
	int reportNum = 0;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config); 
		
		// TODO: Initialize the Reporting Engine 
		
		ClassicEngineBoot.getInstance().start();
	} 

	// the doGet method handles all the requests
	// received by this servlet
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException { 
		
		// TODO: Handle Pentaho Report Request
		
		String reportName = request.getParameter("reportName");
		if (reportName == null) {
			response.getWriter().println("No report parameter specified");
			return;
		}
		
		String sessionId = request.getParameter("sessionId"); 
		
		try {
			// load report definition
			ResourceManager manager = new ResourceManager();
			manager.registerDefaults();
			String reportPath = "file:" + this.getServletContext().getRealPath("data/" + reportName + ".prpt");
			Resource res = manager.createDirectly(new URL(reportPath), MasterReport.class);
			MasterReport report = (MasterReport) res.getResource();

			if (sessionId != null) {
				report.getParameterValues().put("SESSIONID", new Integer(sessionId));
			}
			
			// determine the output format and render accordingly
			String outputFormat = request.getParameter("outputFormat");
			if ("pdf".equals(outputFormat)) {
				// render in pdf
				response.setContentType("application/pdf");
				PdfReportUtil.createPDF(report, response.getOutputStream());
			} else if ("xls".equals(outputFormat)) {
				// render in excel
				response.setContentType("application/vnd.ms-excel");
				ExcelReportUtil.createXLS(report, response.getOutputStream());
			} else if ("html".equals(outputFormat)) {
				String reportLoc = "report_" + reportNum++;
				String path = this.getServletContext().getRealPath(reportLoc);
				File folder = new File(path);
				folder.mkdir();
				System.out.println("REPORTLOC: " + path);
				HtmlReportUtil.createDirectoryHTML(report, path + File.separator + "index.html");
				response.sendRedirect(reportLoc + "/index.html");
			} else {
				// render in rtf
				response.setContentType("application/rtf");
				RTFReportUtil.createRTF(report, response.getOutputStream());
			}

		} catch (ResourceException e) {
			e.printStackTrace();
		} catch (ReportProcessingException e) {
			e.printStackTrace();
		}
	}

	// the doPost method simply calls the doGet method

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException { 
		doGet(request, response); 
	} 
}
