import java.io.IOException;

import org.pentaho.reporting.engine.classic.core.Element;
import org.pentaho.reporting.engine.classic.core.modules.parser.bundle.writer.BundleWriterException;
import org.pentaho.reporting.engine.classic.core.modules.parser.bundle.writer.BundleWriterState;
import org.pentaho.reporting.engine.classic.core.modules.parser.bundle.writer.elements.AbstractElementWriteHandler;
import org.pentaho.reporting.libraries.docbundle.WriteableDocumentBundle;
import org.pentaho.reporting.libraries.xmlns.common.AttributeList;
import org.pentaho.reporting.libraries.xmlns.writer.XmlWriter;
import org.pentaho.reporting.libraries.xmlns.writer.XmlWriterSupport;

// manages writing the star element type to .prpt bundle
public class StarWriteHandler extends AbstractElementWriteHandler {

	// namespace for XML elements
	private static final String NAMESPACE = "http://reporting.pentaho.org/namespaces/pr4jd";

	// default consructor
	public StarWriteHandler() {
	}

	// writes the star element to the xmlWriter 
	public void writeElement(final WriteableDocumentBundle bundle,
			final BundleWriterState state, final XmlWriter xmlWriter,
			final Element element) throws IOException, BundleWriterException {
		if (bundle == null) {
			throw new NullPointerException();
		}
		if (state == null) {
			throw new NullPointerException();
		}
		if (xmlWriter == null) {
			throw new NullPointerException();
		}
		if (element == null) {
			throw new NullPointerException();
		}

		// add the attribute namespace if not already there
		final AttributeList attList = createMainAttributes(element, xmlWriter);
	    if (xmlWriter.isNamespaceDefined(NAMESPACE) == false) {
	      attList.addNamespaceDeclaration("pr4jd", NAMESPACE);
	    }
		
	    // write the star element, within the pr4jd namespace
		xmlWriter.writeTag(NAMESPACE, "star", attList,
				XmlWriterSupport.OPEN);
		writeElementBody(bundle, state, element, xmlWriter);
		xmlWriter.writeCloseTag();
	}
}
