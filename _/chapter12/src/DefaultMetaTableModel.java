import java.util.HashMap;
import java.util.Map;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.pentaho.reporting.engine.classic.core.MetaTableModel;
import org.pentaho.reporting.engine.classic.core.wizard.DataAttributes;
import org.pentaho.reporting.engine.classic.core.wizard.DefaultDataAttributes;
import org.pentaho.reporting.engine.classic.core.wizard.EmptyDataAttributes;

public class DefaultMetaTableModel implements MetaTableModel {

// wrap an existing table model instance
	
	TableModel model;
	
// Define a map of column attributes that map to each 
// column index.

	Map<Integer, DefaultDataAttributes> columnAttributes = new HashMap<Integer, DefaultDataAttributes>();
	
// This constructor wraps a given model and provides 
// column attributes as well

	public DefaultMetaTableModel(TableModel model) {
		this.model = model;
	}
	

// The getColumnAttributes method returns a list of column
// attributes for a particular column.  This should never
// return null, so return an empty list if one doesn't 
// already exist.

	public DataAttributes getColumnAttributes(int column) {
		DefaultDataAttributes attribs = columnAttributes.get(column);
		if (attribs == null) {
			return EmptyDataAttributes.INSTANCE;
		} else {
			return attribs;
		}
	}
	
// Allow setting of column attributes.

	public void setColumnAttribute(int column, String domain, String name, Object value) {

// First find the attribute list for this column.

		DefaultDataAttributes attribs = columnAttributes.get(column);
		if (attribs == null) {

// If an attribute list doesn't exist, create one and add 
// it to the map.

			attribs = new DefaultDataAttributes();
			columnAttributes.put(column, attribs);
		}

// Set the attribute.  Note that a null ConceptMapper 
// results in the default mapping behavior.

		attribs.setMetaAttribute(domain, name, null, value);
	}

// Support for table attributes is not provided in 
// this example.

	public DataAttributes getTableAttributes() {
		return EmptyDataAttributes.INSTANCE;
	}

// Support for cell attributes is not provided in 
// this example.

	public DataAttributes getCellDataAttributes(int row, int column) {
		return null;
	}

	public boolean isCellDataAttributesSupported() {
		return false;
	}

// Wrap the public API of TableModel

	public void addTableModelListener(TableModelListener listener) {
		model.addTableModelListener(listener);
	}

	public Class<?> getColumnClass(int col) {
		return model.getColumnClass(col);
	}

	public int getColumnCount() {
		return model.getColumnCount();
	}

	public String getColumnName(int col) {
		return model.getColumnName(col);
	}

	public int getRowCount() {
		return model.getRowCount();
	}

	public Object getValueAt(int row, int col) {
		return model.getValueAt(row, col);
	}

	public boolean isCellEditable(int row, int col) {
		return model.isCellEditable(row, col);
	}

	public void removeTableModelListener(TableModelListener listener) {
		model.removeTableModelListener(listener);
	}

	public void setValueAt(Object value, int row, int col) {
		model.setValueAt(value, row, col);
	}
}
