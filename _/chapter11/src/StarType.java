import java.awt.Polygon;
import java.util.Locale;

import org.pentaho.reporting.engine.classic.core.Element;
import org.pentaho.reporting.engine.classic.core.function.ExpressionRuntime;
import org.pentaho.reporting.engine.classic.core.metadata.ElementMetaData;
import org.pentaho.reporting.engine.classic.core.metadata.ElementType;
import org.pentaho.reporting.engine.classic.core.metadata.ElementTypeRegistry;
import org.pentaho.reporting.engine.classic.core.style.ElementStyleKeys;
import org.pentaho.reporting.engine.classic.core.util.StringUtil;

// This ElementType implementation renders a Star in a report 
public class StarType implements ElementType {

	// the default namespace for this element
	private static String NAMESPACE = "http://reporting.pentaho.org/namespaces/pr4jd";

	// a reference to the element metadata, defined in the 
	// meta-elements.xml file
	private transient ElementMetaData elementType;

	// a default constructor
	public StarType() {
	}

	// load the default metadata about the star element type
	public ElementMetaData getMetaData() {
		if (elementType == null) {
			elementType = ElementTypeRegistry.getInstance().getElementType(
					"star");
		}
		return elementType;
	}

	// renders a star, using inner-percent, start-angle, 
	// and points  as custom attributes
	public Object getValue(final ExpressionRuntime runtime,
			final Element element) {
		if (element == null) {
			throw new NullPointerException("Element must never be null.");
		}

		// read in Star’s custom parameters
		final float innerPercent = parseParam(element, "inner-percent", 0.5f);
		final float startAngle = parseParam(element, "start-angle", 0f);
		final int points = (int) parseParam(element, "points", 5);

		// render a star based on the parameters
		int outerRadius = 100;
		int innerRadius = (int) (outerRadius * innerPercent);
		double startingRotation = (startAngle - 90) * Math.PI / 180;
		double angleIncrement = 2.0 * Math.PI / points;
		double currRadians = startingRotation;
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		final Polygon p = new Polygon();
		for (int i = 0; i < points; i++) {
			// gotta love trig
			double outerX = outerRadius + outerRadius * Math.cos(currRadians);
			double outerY = outerRadius + outerRadius * Math.sin(currRadians);
			double innerX = outerRadius + innerRadius
					* Math.cos(currRadians + angleIncrement / 2);
			double innerY = outerRadius + innerRadius
					* Math.sin(currRadians + angleIncrement / 2);
			p.addPoint((int) outerX, (int) outerY);
			p.addPoint((int) innerX, (int) innerY);
			currRadians += angleIncrement;
			
			// keep track of the smallest x and y values
			minX = Math.min((int)outerX, minX);
			minY = Math.min((int)outerY, minY);
		}
		// move the star's points to 0,0 for
		// appropriate rendering
		if (minX > 0 || minY > 0) {
			final Polygon p2 = new Polygon();
			for (int i = 0; i < p.npoints; i++) {
				p2.addPoint(p.xpoints[i] - minX, p.ypoints[i] - minY);
			}
			return p2;
		} else {
			return p;
		}
	}

	// returns the design time value of this element, rendered 
	// in the report designer
	public Object getDesignValue(final ExpressionRuntime runtime,
			final Element element) {
		return getValue(runtime, element);
	}

	// this method is called when a star is first added to 
	// a report within the report designer.  Set up
	// the default values here.
	public void configureDesignTimeDefaults(final Element element,
			final Locale locale) {
		element.getStyle().setStyleProperty(ElementStyleKeys.SCALE,
				Boolean.TRUE);
		element.getStyle().setStyleProperty(ElementStyleKeys.DRAW_SHAPE,
				Boolean.TRUE);
		element.getStyle().setStyleProperty(ElementStyleKeys.MIN_WIDTH,
				new Float(100));
		element.getStyle().setStyleProperty(ElementStyleKeys.MIN_HEIGHT,
				new Float(100));

		element.setAttribute(NAMESPACE, "inner-percent", 0.5f);
		element.setAttribute(NAMESPACE, "start-angle", 0f);
		element.setAttribute(NAMESPACE, "points", 5);
	}

	// this is a utility function that parses the
	// custom attributes
	private float parseParam(final Element element, final String attrName,
			float defaultValue) {
		final float val;
		final Object attrib = element.getAttribute(NAMESPACE, attrName);
		if (attrib != null) {
			if (attrib instanceof Number) {
				final Number n = (Number) attrib;
				val = n.floatValue();
			} else {
				val = StringUtil.parseFloat(String.valueOf(attrib),
						defaultValue);
			}
		} else {
			val = defaultValue;
		}
		return val;
	}

	// clone is required, because the reporting engine may
	// create new instances of the StarType when new reports 
	// are rendered.
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
