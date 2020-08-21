package com.cxd.repository.sysuser.dao.repository;

import com.cxd.repository.sysuser.pojo.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author MinWeikai
 * @date 2019/11/9 11:54
 */
@Repository
public interface SysRoleRepository extends JpaRepository<SysRole,Integer> {
	List<SysRole> findByRoleIdIn(List<Integer> roleIds);

	List<SysRole> findByStatusAndRoleIdIn(Integer value, List<Integer> roleIds);

	SysRole findByRole(String role);

	void deleteByRoleId(Integer id);
}
