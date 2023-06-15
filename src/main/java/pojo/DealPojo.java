package pojo;


public class DealPojo {
	
    private String description;
    private String link;
    private String name;
    private String validated;
    
	public DealPojo(String description, String link, String name, String validated) {
		super();
		this.description = description;
		this.link = link;
		this.name = name;
		this.validated = validated;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValidated() {
		return validated;
	}
	public void setValidated(String validated) {
		this.validated = validated;
	}
    
    

}
