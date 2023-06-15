package com.laba4s;

public class Company {
	private String siret;
	private String name;
	private String phone;
	
	public Company() {}
	
	public Company(String siret, String name, String phone) {
		super();
		this.siret = siret;
		this.name = name;
		this.phone = phone;
	}
	public String getSiret() {
		return siret;
	}
	public void setSiret(String siret) {
		this.siret = siret;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	
	
	
	

}
