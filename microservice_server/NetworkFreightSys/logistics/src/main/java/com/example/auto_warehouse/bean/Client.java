package com.example.auto_warehouse.bean;


public class Client {
	private String ceid;
	private String ceName;
	private String psw;
	private String phone;

	public Client(String ceid, String ceName, String psw, String phone) {
		this.ceid = ceid;
		this.ceName = ceName;
		this.psw = psw;
		this.phone = phone;
	}
	public Client(String ceid, String ceName, String phone) {
		this.ceid = ceid;
		this.ceName = ceName;
		this.phone = phone;
	}

	public String getCeName() {
		return ceName;
	}

	public void setCeName(String ceName) {
		this.ceName = ceName;
	}


	public String getCeid() {
		return ceid;
	}

	public void setCeid(String ceid) {
		this.ceid = ceid;
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



	public Client() {
		super();
	}


	
}
