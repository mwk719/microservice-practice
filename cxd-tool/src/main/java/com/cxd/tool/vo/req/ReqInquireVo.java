package com.cxd.tool.vo.req;

import com.cxd.tool.util.Pager;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author MinWeikai
 * @date 2019/8/31 17:02
 */
@ApiModel("排序参数分页")
@Data
public class ReqInquireVo {

	/**
	 * 排序
	 */
	private List<ReqSortVo> sortVos;

	/**
	 * 范围
	 */
	private List<ReqFrameVo> frameVos;

	/**
	 * 分页
	 */
	private Pager page;

}
