package com.duha.duhabank.role.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.duha.duhabank.exceptions.BadRequestException;
import com.duha.duhabank.res.Response;
import com.duha.duhabank.role.entity.Role;
import com.duha.duhabank.role.repo.RoleRepo;
import com.duha.duhabank.exceptions.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{
	
	private final RoleRepo roleRepo;

	@Override
	public Response<Role> createRole(Role request) {
		if(roleRepo.findByName(request.getName()).isPresent()) {
			throw new BadRequestException("Role already exists!");
		}
		
		Role roleSave = roleRepo.save(request);
		
		return Response.<Role>builder()
				.statusCode(HttpStatus.OK.value())
				.message("Role saved successfully!")
				.data(roleSave)
	            .build();
	}

	@Override
	public Response<Role> updateRole(Role request) {
		Role role = roleRepo.findById(request.getId())
                .orElseThrow(()-> new NotFoundException("Role not found!"));
		role.setName(request.getName());

        Role updatedRole = roleRepo.save(role);
		return Response.<Role>builder()
				.statusCode(HttpStatus.OK.value())
				.data(updatedRole)
				.message("Role updated successfully!")
				.build();
	}

	@Override
	public Response<List<Role>> getAllRoles() {
		List<Role> roles = roleRepo.findAll();
		return Response.<List<Role>>builder()
				.statusCode(HttpStatus.OK.value())
                .message("Roles retreived successfully")
                .data(roles)
                .build();
	}

	@Override
	public Response<?> deleteRole(Long id) {
		if(!roleRepo.existsById(id)) {
			throw new NotFoundException("Role Not Found!");
		}
		roleRepo.deleteById(id);
		return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Role deleted successfully")
                .build();
	}

}
