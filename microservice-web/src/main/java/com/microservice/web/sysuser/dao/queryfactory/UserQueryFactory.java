package com.microservice.web.sysuser.dao.queryfactory;

import cn.hutool.core.convert.Convert;
import com.microservice.repository.sysuser.pojo.entity.QSysRole;
import com.microservice.repository.sysuser.pojo.entity.QSysUser;
import com.microservice.repository.sysuser.pojo.entity.QSysUserRole;
import com.microservice.tool.util.Pager;
import com.microservice.tool.util.StringUtils;
import com.microservice.web.sysuser.pojo.vo.req.ReqUseFilterVo;
import com.microservice.web.sysuser.pojo.vo.resp.RespUseListVo;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author MinWeikai
 * @date 2019/11/14 12:09
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserQueryFactory {

	@NonNull
	private JPAQueryFactory jpaQueryFactory;


	public Pager<RespUseListVo> listPage(ReqUseFilterVo param) {
		Pager<RespUseListVo> page = param.getPage();
		QSysUser user = QSysUser.sysUser;
		QSysUserRole ur = QSysUserRole.sysUserRole;
		QSysRole role = QSysRole.sysRole;

		JPAQuery<RespUseListVo> jpaQuery = jpaQueryFactory.select(
				Projections.bean(
						RespUseListVo.class,
						user.userId,
						user.luck,
						user.createTime,
						user.nickname,
						user.username,
						role.description
				)
		).from(user).leftJoin(ur).on(user.userId.eq(ur.userId))
				.leftJoin(role).on(ur.roleId.eq(role.roleId));

		if (StringUtils.isNotBlank(param.getDescription())) {
			jpaQuery.where(role.description.eq(param.getDescription()));
		}

		if (StringUtils.isNotBlank(param.getUsername())) {
			jpaQuery.where(user.username.eq(param.getUsername()));
		}
		QueryResults<RespUseListVo> results= jpaQuery.orderBy(user.createTime.desc())
				.offset(page.getOffset())
				.limit(page.getPageSize())
				.fetchResults();
		page.setContent(results.getResults());
		page.setRecordTotal(Convert.toInt(results.getTotal()));
		return page;

	}
}
