package com.microservice.web.role.process;

import com.microservice.web.sysuser.pojo.vo.req.ReqAddRoleVo;
import com.microservice.web.sysuser.pojo.vo.resp.RespRoleListVo;
import com.microservice.web.sysuser.process.RoleProcess;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author MinWeikai
 * @date 2019/11/13 15:58
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleProcessTest {

	@Autowired
	private RoleProcess roleProcess;

	@Test
	public void listTest() {
		List<RespRoleListVo> roleListVos = roleProcess.list();
		log.info("角色数量：{}，列表：{}", roleListVos.size(), roleListVos);
	}

	@Test
	public void addTest() {
		roleProcess.save(new ReqAddRoleVo("customer","客服"));
	}
}
