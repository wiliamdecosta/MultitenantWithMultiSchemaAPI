package com.justclick.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.justclick.bean.Tenant;

public interface TenantRepository extends JpaRepository<Tenant, Integer> {
	public Tenant findBySchema(String schema);
}
