package com.microservice.repository.sysuser.dao.repository;

import com.microservice.repository.sysuser.pojo.entity.SysLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author MinWeikai
 * @date 2019/11/15 16:10
 */
@Repository
public interface SysLogRepository extends JpaRepository<SysLog, Integer> {
}
