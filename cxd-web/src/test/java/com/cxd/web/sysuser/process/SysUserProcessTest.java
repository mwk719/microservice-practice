package com.cxd.web.sysuser.process;

import com.cxd.tool.util.Pager;
import com.cxd.web.sysuser.pojo.vo.req.ReqAddUserVo;
import com.cxd.web.sysuser.pojo.vo.req.ReqUseFilterVo;
import com.cxd.web.sysuser.pojo.vo.resp.RespUseListVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author MinWeikai
 * @date 2019/11/14 12:22
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysUserProcessTest {

	@Autowired
	private SysUserProcess sysUserProcess;

	@Test
	public void listTest() {
		Pager<RespUseListVo> useListVos = sysUserProcess.listPage(
				new ReqUseFilterVo(
						new Pager(1, 10)
				)
		);
		log.info("用户数量：{}，列表：{}", useListVos.getRecordTotal(), useListVos.getContent());
	}

	@Test
	public void saveTest() {
		sysUserProcess.save(
				ReqAddUserVo.builder()
						.userId(3)
						.username("cxd")
						.nickname("挂啊")
						.build()
		);
	}
}
