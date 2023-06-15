package com.laba4s;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;




@Entity
@Table(name="deal")
public class Deal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
        
    @NotBlank(message = "description is mandatory")
    @Column(name = "description")
    private String description;
    
    @NotBlank(message = "link is mandatory")
    @Column(name = "link")
    private String link;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "validated")
    private String validated;
    
    @Column(name = "session")
    private String session;
    
    public Deal() {
    	
    }

	public Deal(@NotBlank(message = "description is mandatory") String description,
			@NotBlank(message = "link is mandatory") String link, String name, String validated, String session) {
		super();
		this.description = description;
		this.link = link;
		this.name = name;
		this.validated = validated;
		this.session = session;
	}
	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

}
