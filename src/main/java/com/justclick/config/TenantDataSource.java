package com.justclick.config;

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

import com.justclick.bean.Tenant;
import com.justclick.repo.TenantRepository;

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

    public DataSource getDataSource(String name) {
        if (dataSources.get(name) != null) {
            return dataSources.get(name);
        }
        DataSource dataSource = createDataSource(name);
        if (dataSource != null) {
            dataSources.put(name, dataSource);
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

    private DataSource createDataSource(String schemaName) {
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
