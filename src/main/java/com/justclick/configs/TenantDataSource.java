package com.justclick.configs;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import com.justclick.beans.Tenant;
import com.justclick.repositories.TenantRepository;

@Component
public class TenantDataSource implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3026120030213546897L;

	private HashMap<String, DataSource> dataSources = new HashMap<>();

    @Autowired
    private TenantRepository tenantRepo;
    
    @Value("${spring.datasource.username}")
    private String usernameDB;
    
    @Value("${spring.datasource.password}")
    private String passwordDB;
    
    @Value("${spring.datasource.url}")
    private String urlDB;

    public DataSource getDataSource(String schemaName) {
        if (dataSources.get(schemaName) != null) {
            return dataSources.get(schemaName);
        }
        DataSource dataSource = createDataSource(schemaName);
        if (dataSource != null) {
            dataSources.put(schemaName, dataSource);
        }
        return dataSource;
    }
    
    @PostConstruct
    public Map<String, DataSource> getAll() {
        List<Tenant> tenantList = tenantRepo.findAll();
        Map<String, DataSource> result = new HashMap<>();
        for (Tenant tenant : tenantList) {
            DataSource dataSource = getDataSource(tenant.getSchema());
            result.put(tenant.getSchema(), dataSource);
        }
        
        return result;
    }

    
    public DataSource createDataSource(String schemaName) {
        Tenant tenant = tenantRepo.findBySchema(schemaName);
        if (tenant != null) {
            DataSourceBuilder<?> factory = DataSourceBuilder
                    .create().driverClassName("org.postgresql.Driver")
                    .username(usernameDB)
                    .password(passwordDB)
                    .url(urlDB+"&currentSchema="+schemaName);
            DataSource ds = factory.build();
            return ds;
        }
        return null;
    }

}
