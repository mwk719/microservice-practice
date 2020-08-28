package com.microservice.repository.sysuser.dao.repository;

import com.microservice.repository.sysuser.pojo.entity.SysRolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author MinWeikai
 * @date 2019/11/9 11:56
 */
@Repository
public interface SysRolePermissionRepository extends JpaRepository<SysRolePermission, Integer> {
	List<SysRolePermission> findByRoleIdIn(List<Integer> roleIds);
}
