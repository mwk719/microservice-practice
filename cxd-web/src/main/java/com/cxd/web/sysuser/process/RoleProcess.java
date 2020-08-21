package com.cxd.web.sysuser.process;

import com.cxd.web.sysuser.pojo.vo.req.ReqAddRoleVo;
import com.cxd.web.sysuser.pojo.vo.req.ReqIdStatusVo;
import com.cxd.web.sysuser.pojo.vo.resp.RespRoleListVo;

import java.util.List;

/**
 * @author MinWeikai
 * @date 2019/11/14 11:23
 */
public interface RoleProcess {
	List<RespRoleListVo> list();

	void save(ReqAddRoleVo param);

	void delete(ReqIdStatusVo param);
}
