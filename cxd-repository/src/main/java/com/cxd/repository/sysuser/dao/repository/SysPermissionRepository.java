package com.cxd.repository.sysuser.dao.repository;

import com.cxd.repository.sysuser.pojo.entity.SysPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author MinWeikai
 * @date 2019/11/9 11:53
 */
@Repository
public interface SysPermissionRepository extends JpaRepository<SysPermission,Integer> {
	List<SysPermission> findByPermissionIdInOrderBySortDesc(List<Integer> permissionIds);

	List<SysPermission> findByParentIdInAndResourceTypeOrderBySortDesc(List<Integer> permissionIds, String value);

	List<SysPermission> findByPermissionIdInAndParentIdAndResourceTypeOrderBySortDesc(List<Integer> permissionIds, Integer parentId, String resourceType);
}
