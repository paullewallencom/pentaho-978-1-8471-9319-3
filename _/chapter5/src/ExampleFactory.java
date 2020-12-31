public class ExampleFactory {	
	public ExampleTableModel getAllData() {
		return new ExampleTableModel();
	}
	
	public ExampleTableModel getLibData(boolean libOnly) {
		return new ExampleTableModel(libOnly);
	}
}
