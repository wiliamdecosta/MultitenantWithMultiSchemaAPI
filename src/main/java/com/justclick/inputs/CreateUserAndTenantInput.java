package com.justclick.inputs;

public class CreateUserAndTenantInput {
	private String tenantName;
	private String tenantAddress;
	private String tenantSchema;
	private String userName;
	private String userPassword;
	private String userEmail;
	private boolean userEnabled;
	
	public CreateUserAndTenantInput() {
		
	}
	
	public String getTenantName() {
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	public String getTenantAddress() {
		return tenantAddress;
	}
	public void setTenantAddress(String tenantAddress) {
		this.tenantAddress = tenantAddress;
	}
	public String getTenantSchema() {
		return tenantSchema;
	}
	public void setTenantSchema(String tenantSchema) {
		this.tenantSchema = tenantSchema;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public boolean getUserEnabled() {
		return userEnabled;
	}
	public void setUserEnabled(boolean userEnabled) {
		this.userEnabled = userEnabled;
	}
	
	
}
