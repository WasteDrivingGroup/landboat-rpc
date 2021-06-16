package com.wastedrivinggroup.consumer.netty.proto.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 调用的结果
 *
 * @author chen
 * @date 2021/6/16
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class InvokeRespProto {
	/**
	 * 调用码,表示是否调用成功
	 */
	private Integer invokeCode;

	/**
	 * 请求的编号
	 */
	private Long invokeId;

	/**
	 * 错误信息
	 */
	private String errMemo;

	/**
	 * 正常返回
	 */
	private Object ret;
}
