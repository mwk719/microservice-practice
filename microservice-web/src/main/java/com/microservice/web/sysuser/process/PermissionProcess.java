package com.microservice.web.sysuser.process;

import com.microservice.web.sysuser.pojo.vo.resp.RespLoginMenuVo;
import com.microservice.web.sysuser.pojo.vo.resp.RespPermissionMenuVo;

import java.util.List;

/**
 * @author MinWeikai
 * @date 2019/11/18 11:00
 */
public interface PermissionProcess {
	List<RespPermissionMenuVo> list(Integer userId);
}
