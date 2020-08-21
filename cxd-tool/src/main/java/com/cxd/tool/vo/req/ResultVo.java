package com.cxd.tool.vo.req;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * 存在单条或数组数据用ResultVo包装
 *
 * @author MinWeikai
 * @date 2019/6/6 10:53
 */
@ApiModel("ResultVo包装")
public class ResultVo<T> implements Serializable {

	private T resultVo;

	public ResultVo(T resultVo) {
		this.resultVo = resultVo;
	}

	public T getResultVo() {
		return resultVo;
	}

	public void setResultVo(T resultVo) {
		this.resultVo = resultVo;
	}
}
