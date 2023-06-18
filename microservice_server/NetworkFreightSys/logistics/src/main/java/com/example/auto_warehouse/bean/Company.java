package com.example.auto_warehouse.bean;


public class Company {
	private String coid;
	private String coName;
	private String psw;
	private String phone;

	public Company(String coid, String coName, String psw, String phone) {
		this.coid = coid;
		this.coName = coName;
		this.psw = psw;
		this.phone = phone;
	}
	public Company(String coid, String coName, String phone) {
		this.coid = coid;
		this.coName = coName;
		this.phone = phone;
	}

	public String getCoName() {
		return coName;
	}

	public void setCoName(String coName) {
		this.coName = coName;
	}



	public String getCoid() {
		return coid;
	}

	public void setCoid(String coid) {
		this.coid = coid;
	}



	public String getPsw() {
		return psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}



	public Company() {
		super();
	}

	
	
}
