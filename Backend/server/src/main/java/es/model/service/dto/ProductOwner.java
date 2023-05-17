package es.model.service.dto;

public class ProductOwner {
	private String id;
	private String name;
	private String surname;
	
    public ProductOwner() {
    }

	public ProductOwner(String id, String name, String surname) {
		super();
		this.setId(id);
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
}
