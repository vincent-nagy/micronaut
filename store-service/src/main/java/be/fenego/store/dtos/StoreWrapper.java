package be.fenego.store.dtos;

public class StoreWrapper {
	private Store[] elements;
	private String type;

	public Store[] getElements() {
		return elements;
	}

	public void setElements(Store[] elements) {
		this.elements = elements;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
