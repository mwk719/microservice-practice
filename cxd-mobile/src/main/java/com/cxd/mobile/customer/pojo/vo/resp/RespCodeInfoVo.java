package com.cxd.mobile.customer.pojo.vo.resp;

import io.swagger.annotations.ApiModel;
import lombok.Data;


@ApiModel("小程序授权返回")
@Data
public class RespCodeInfoVo {

	private String openId;

	private String unionId;


}