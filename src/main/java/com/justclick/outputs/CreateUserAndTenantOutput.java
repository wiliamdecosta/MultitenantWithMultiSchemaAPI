package com.justclick.outputs;

public class CreateUserAndTenantOutput {
	private String message;
	private boolean success;
	private boolean isCreateUserSuccess;
	private boolean isCreateTenantSuccess;
	
	public CreateUserAndTenantOutput() {
		
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public boolean isCreateUserSuccess() {
		return isCreateUserSuccess;
	}
	public void setCreateUserSuccess(boolean isCreateUserSuccess) {
		this.isCreateUserSuccess = isCreateUserSuccess;
	}
	public boolean isCreateTenantSuccess() {
		return isCreateTenantSuccess;
	}
	public void setCreateTenantSuccess(boolean isCreateTenantSuccess) {
		this.isCreateTenantSuccess = isCreateTenantSuccess;
	}
	
	
}
