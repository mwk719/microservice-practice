package com.microservice.repository.sysuser.dao.repository;

import com.microservice.repository.sysuser.pojo.entity.SysUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author MinWeikai
 * @date 2019/11/9 12:00
 */
@Repository
public interface SysUserRoleRepository extends JpaRepository<SysUserRole, Integer> {
	List<SysUserRole> findByUserId(Integer userId);

	void deleteByUserId(Integer userId);
}
