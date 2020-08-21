package com.cxd.tool.test;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * @author MinWeikai
 * @date 2019/11/5 15:49
 */
public class test2 {


	public static void main(String[] args) {
		System.out.println(DateUtil.offsetMinute(new Date(), 15));

	}
}
