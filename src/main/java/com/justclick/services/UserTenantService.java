package com.justclick.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.justclick.impl.DataSourceBasedMultiTenantConnectionProviderImpl;
import com.justclick.inputs.CreateUserAndTenantInput;
import com.justclick.outputs.CreateUserAndTenantOutput;
import com.justclick.repositories.UserTenantRepository;

@Service
public class UserTenantService {
	
	@Autowired
	private UserTenantRepository utRepo;
	
	 @Autowired
	 private ApplicationContext context;

	public CreateUserAndTenantOutput callCreateSchemaProcedure(CreateUserAndTenantInput input) {
		
		//tenant schema is from tenant name
		String tenantScema = input.getTenantName().trim().replace(". "," ").replace(".","-").replace(" ", "-").toLowerCase();
		input.setTenantSchema(tenantScema);
		input.setUserEnabled(true);
		
		Map<String, Object> outputParam =  utRepo.callCreateSchemaProcedure(input.getTenantName(),
				input.getTenantAddress(), 
				input.getTenantSchema(),
				input.getUserName(),
				input.getUserPassword(),
				input.getUserEmail(),
				input.getUserEnabled());
		
		CreateUserAndTenantOutput createdUserAndTenantOutput = new CreateUserAndTenantOutput();
		
		outputParam.forEach((k, v) -> {
			System.err.println(k + " : " + v);
			
			if(k.compareTo("outMessage") == 0) {
				createdUserAndTenantOutput.setMessage((String) v);
			}
			
			if(k.compareTo("outSuccess") == 0) {
				createdUserAndTenantOutput.setSuccess((boolean)v);
			}
			
			if(k.compareTo("outIsCreateUserSuccess") == 0) {
				createdUserAndTenantOutput.setCreateUserSuccess((boolean)v);
			}
			
			if(k.compareTo("outIsCreateTenantSuccess") == 0) {
				createdUserAndTenantOutput.setCreateTenantSuccess((boolean)v);
			}
		});
		
		//add data source if created schema success
		if(createdUserAndTenantOutput.isSuccess()) {
			addDataSource(tenantScema);
		}
		
		return createdUserAndTenantOutput;
	}
	
	/**
	 * add data source while application running
	 * @param tenantSchema
	 */
	private void addDataSource(String tenantSchema) {
		DataSourceBasedMultiTenantConnectionProviderImpl dataSourceProvider = context.getBean(DataSourceBasedMultiTenantConnectionProviderImpl.class);
		dataSourceProvider.addDataSource(tenantSchema);
	}
}
