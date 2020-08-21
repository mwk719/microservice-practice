package com.cxd.mobile.customer.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.crypto.SecureUtil;
import com.cxd.mobile.common.util.RedisSpringProxy;
import com.cxd.mobile.customer.pojo.vo.req.ReqLoginVo;
import com.cxd.repository.customer.dao.repository.CustomerRepository;
import com.cxd.repository.customer.pojo.entity.Customer;
import com.cxd.tool.constant.ErrorCode;
import com.cxd.tool.constant.RedisKeyEnum;
import com.cxd.tool.constant.YesOrNoInd;
import com.cxd.tool.exception.BusinessException;
import com.cxd.tool.util.AssertUtil;
import com.cxd.tool.util.SignUtil;
import com.cxd.tool.util.StringUtils;
import com.cxd.tool.util.ValidatorUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author MinWeikai
 * @date 2019/11/4 10:41
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

	@NonNull
	private CustomerRepository customerRepository;

	@NonNull
	private RedisSpringProxy redisSpringProxy;

	/**
	 * 登陆成功生成key
	 *
	 * @param customer
	 * @return
	 */
	public String createKey(Customer customer) {
		String key;
		String redisKey = RedisKeyEnum.CUSTOMER.getCxdValue(customer.getCustomerId());
		Object object = redisSpringProxy.read(redisKey);
		if (StringUtils.isBlank(object)) {
			key = SignUtil.createSign(customer.getUsername(), customer.getPassword());
			//有效期30天
			redisSpringProxy.saveEx(redisKey, 60 * 60 * 24 * 30, key);
		}
		return Convert.toStr(object);
	}

	/**
	 * 根据用户名查询用户
	 *
	 * @param username
	 * @return
	 */
	public Customer findByUsername(String username) {
		Customer customer;
		try {
			customer = customerRepository.findByUsername(username);
		} catch (Exception e) {
			log.error("重复账号：{},错误信息：{}", username, e.getMessage());
			throw new BusinessException(ErrorCode.NUM_REPEAT);
		}
		AssertUtil.isNotNull(customer, ErrorCode.USER_NOT_EXIST);
		return customer;
	}

	public Customer findByUsername(ReqLoginVo loginVo) {
		ValidatorUtil.validateNull(loginVo, new String[]{"username", "password"});
		String username = loginVo.getUsername();
		Customer customer = this.findByUsername(username);
		AssertUtil.isTrue(customer.getLuck().equals(YesOrNoInd.NO.getValue()), ErrorCode.ACCOUNT_LOCKED);
		return customer;
	}

	/**
	 * 密码验证
	 *
	 * @param lPassword 登陆密码
	 * @param password  已存密码
	 */
	public void checkPassword(String lPassword, String password) {
		if (!SecureUtil.md5(lPassword).equals(password)) {
			throw new BusinessException(ErrorCode.PASSWORD_ERROR);
		}
	}

}
