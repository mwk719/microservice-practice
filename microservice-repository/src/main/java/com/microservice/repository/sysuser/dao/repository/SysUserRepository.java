package com.microservice.repository.sysuser.dao.repository;

import com.microservice.repository.sysuser.pojo.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author MinWeikai
 * @date 2019/11/9 11:53
 */
@Repository
public interface SysUserRepository extends JpaRepository<SysUser, Integer> {
	SysUser findByUsername(String userName);

	void deleteByUserId(Integer userId);

	SysUser findByUserId(Integer userId);
}
