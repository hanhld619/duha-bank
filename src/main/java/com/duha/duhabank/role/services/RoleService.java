package com.duha.duhabank.role.services;

import java.util.List;

import com.duha.duhabank.res.Response;
import com.duha.duhabank.role.entity.Role;

public interface RoleService {
	
	Response<Role> createRole(Role request);

	Response<Role> updateRole(Role request);
	
	Response<List<Role>> getAllRoles();
	
	Response<?> deleteRole (Long id);
}
