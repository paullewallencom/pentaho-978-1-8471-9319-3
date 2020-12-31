import java.util.ArrayList;
import java.util.List;

import org.pentaho.reporting.engine.classic.core.layout.model.LogicalPageBox;
import org.pentaho.reporting.engine.classic.core.layout.model.ParagraphPoolBox;
import org.pentaho.reporting.engine.classic.core.layout.model.RenderBox;
import org.pentaho.reporting.engine.classic.core.layout.model.RenderNode;
import org.pentaho.reporting.engine.classic.core.layout.model.RenderableText;
import org.pentaho.reporting.engine.classic.core.layout.model.SpacerRenderNode;
import org.pentaho.reporting.engine.classic.core.layout.output.AbstractOutputProcessor;
import org.pentaho.reporting.engine.classic.core.layout.output.ContentProcessingException;
import org.pentaho.reporting.engine.classic.core.layout.output.LogicalPageKey;
import org.pentaho.reporting.engine.classic.core.layout.output.OutputProcessorMetaData;
import org.pentaho.reporting.libraries.base.config.Configuration;

// The PojoOutputProcessor extends the
// AbstractOutputProcessor, which manages the 
// processing state.

public class PojoOutputProcessor extends AbstractOutputProcessor {

// Define a pojo object which you'll populate when 
// rendering the report

	public static class PojoObject {

// Define the x location of the PojoObject.

		int x;

// Define the y location of the PojoObject.

		int y;

// Define the text extracted during report rendering.

		String text = "";
	}
	
// Define a reference to the PojoOutputMetaData class.

	PojoOutputMetaData metadata;

// Define a list of the pojo objects you're about to generate.

	List<PojoObject> pojoObjects = new ArrayList<PojoObject>();
	
// The constructor creates a new metadata object.

	public PojoOutputProcessor(final Configuration configuration) {
		metadata = new PojoOutputMetaData(configuration);
	}
	
// The processPageContent callback renders a report page from 
// a LogicalPageBox.  This method is called by 
// the ReportProcessor when the page content is ready 
// for rendering.

	protected void processPageContent(LogicalPageKey logicalPageKey,
			LogicalPageBox logicalPage) throws ContentProcessingException {

// Call into the recursive handle method, which
// traverses the logical page DOM.

		handle(logicalPage);
	}

// The handle method loops through the child nodes of
// a specified RenderBox, either recursing into child nodes
// or rendering ParagraphPoolBox nodes.

	public void handle(RenderBox box) {
		RenderNode node = box.getFirstChild();
		while (node != null) {
			if (node instanceof ParagraphPoolBox) {

// The ParagraphPoolBox contains the text within a report.

				ParagraphPoolBox ppb = (ParagraphPoolBox)node;
				handleParagraph(ppb);
			}
			if (node instanceof RenderBox) {

// Recurse into the DOM.

				handle((RenderBox)node);
			}
			node = node.getNext();
		}
	}

// The handleParagraph method generates a PojoObject based 
// on a ParagraphPoolBox, which contains text and spacer
// nodes.

	public void handleParagraph(ParagraphPoolBox box) {
		RenderNode node = box.getFirstChild();

// Create a PojoObject with x and y coordinates.

		PojoObject object = new PojoObject();
		object.x = (int)node.getX() / 1000;
		object.y = (int)node.getY() / 1000;

// Populate the PojoObject's text appropriately.

		while (node != null) {
			if (node instanceof RenderableText) {
				object.text += ((RenderableText)node).getRawText();
			} else if (node instanceof SpacerRenderNode) {
				for (int i = 0; i < ((SpacerRenderNode)node).getSpaceCount(); i++) {
					object.text += " ";
				}
			}
			node = node.getNext();
		}

// Add the PojoObject to the list.

		pojoObjects.add(object);
	}
	
// Return a reference to the custom OutputProcessorMetaData
// instance.

	public OutputProcessorMetaData getMetaData() {
		return metadata;
	}
	
// Allow access to the generated PojoObject list.

	public List<PojoObject> getPojoObjects() {
		return pojoObjects;
	}
}
