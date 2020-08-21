package com.cxd.web.sysuser.pojo.bo;

import com.cxd.repository.sysuser.pojo.entity.SysRole;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author MinWeikai
 * @date 2019/11/11 15:33
 */
@Data
@Builder
public class RoleBo {

	private SysRole role;

	private List<Integer> roleIds;
}
