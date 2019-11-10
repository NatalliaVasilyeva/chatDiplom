package com.bsu.nvasilyeva.service;

import com.bsu.nvasilyeva.dao.RolesDAO;
import com.bsu.nvasilyeva.entity.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolesService {

	@Autowired
    RolesDAO rolesDAO;

	public void add(Roles roles) {
		rolesDAO.addRole(roles);
	}

}
