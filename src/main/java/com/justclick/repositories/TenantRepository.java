package com.justclick.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.justclick.beans.Tenant;

public interface TenantRepository extends JpaRepository<Tenant, Integer> {
	public Tenant findBySchema(String schema);
}
