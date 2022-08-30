package com.justclick.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justclick.inputs.CreateUserAndTenantInput;
import com.justclick.outputs.CreateUserAndTenantOutput;
import com.justclick.repositories.UserTenantRepository;

@Service
public class UserTenantService {
	
	@Autowired
	private UserTenantRepository utRepo;

	public CreateUserAndTenantOutput callCreateSchemaProcedure(CreateUserAndTenantInput input) {
		
		String tenantScema = input.getTenantName().trim().replace("."," ").replace(" ", "-").toLowerCase();
		input.setTenantSchema(tenantScema);
		
		Map<String, Object> outputParam =  utRepo.callCreateSchemaProcedure(input.getTenantName(),
				input.getTenantAddress(), 
				input.getTenantSchema(),
				input.getUserName(),
				input.getUserPassword(),
				input.getUserEmail(),
				input.getUserEnabled());
		
		CreateUserAndTenantOutput createdUserAndTenantOutput = new CreateUserAndTenantOutput();
		
		outputParam.forEach((k, v) -> {
			if(k.compareTo("outMessage") == 0) {
				createdUserAndTenantOutput.setMessage((String) v);
			}
			
			if(k.compareTo("isSuccess") == 0) {
				createdUserAndTenantOutput.setSuccess((boolean)v);
			}
			
			if(k.compareTo("isCreateUserSuccess") == 0) {
				createdUserAndTenantOutput.setCreateUserSuccess((boolean)v);
			}
			
			if(k.compareTo("outIsCreateTenantSuccess") == 0) {
				createdUserAndTenantOutput.setCreateTenantSuccess((boolean)v);
			}
		});
		
		return createdUserAndTenantOutput;
	}
}
