package com.justclick.repositories;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.justclick.beans.SchemaCreatedLog;

public interface UserTenantRepository extends JpaRepository<SchemaCreatedLog, Integer>  {
	@Procedure(name="jc.schemacreate")
	Map<String, Object> callCreateSchemaProcedure(String tenantName, 
			String tenantAddress, 
			String tenantSchema, 
			String userName, 
			String userPassword,
			String userEmail,
			boolean userEnabled);
}
