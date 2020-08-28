package com.microservice.web.sysuser.process;

import com.microservice.tool.util.Pager;
import com.microservice.web.sysuser.pojo.vo.req.ReqAddUserVo;
import com.microservice.web.sysuser.pojo.vo.req.ReqIdStatusVo;
import com.microservice.web.sysuser.pojo.vo.req.ReqLoginVo;
import com.microservice.web.sysuser.pojo.vo.req.ReqUseFilterVo;
import com.microservice.web.sysuser.pojo.vo.resp.RespUseListVo;
import com.microservice.web.sysuser.pojo.vo.resp.RespUserLoginVo;

/**
 * @author MinWeikai
 * @date 2019/11/9 10:52
 */
public interface SysUserProcess {
	RespUserLoginVo login(ReqLoginVo param);

	void delete(ReqIdStatusVo vo);

	void logout(Integer userId);

	Pager<RespUseListVo> listPage(ReqUseFilterVo param);

	void luck(ReqIdStatusVo vo);

	void save(ReqAddUserVo param);
}
