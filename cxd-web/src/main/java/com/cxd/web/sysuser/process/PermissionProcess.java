package com.cxd.web.sysuser.process;

import com.cxd.web.sysuser.pojo.vo.resp.RespLoginMenuVo;
import com.cxd.web.sysuser.pojo.vo.resp.RespPermissionMenuVo;

import java.util.List;

/**
 * @author MinWeikai
 * @date 2019/11/18 11:00
 */
public interface PermissionProcess {
	List<RespPermissionMenuVo> list(Integer userId);
}
