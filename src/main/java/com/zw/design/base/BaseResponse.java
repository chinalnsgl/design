package com.zw.design.base;

import java.io.Serializable;

import org.springframework.stereotype.Component;

/**
 * json封装Dto
 */
@Component
public class BaseResponse implements Serializable{

	private static final long serialVersionUID = -28667821450923254L;

	private int status = 200;
	private Object content;
	
	public static BaseResponse STATUS_200 = new BaseResponse(200, "操作成功 O(∩_∩)O~ ！");
	public static BaseResponse STATUS_400 = new BaseResponse(400, "操作失败 (┬＿┬) ！！");

	public BaseResponse(){
	}
	
	public BaseResponse(int status, Object content){
		this.status= status;
		this.content = content;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public static BaseResponse toResponse(Object o) {
		if (null != o) {
			return STATUS_200;
		}
		return STATUS_400;
	}

	public static BaseResponse toNotResponse(Object o) {
		if (null == o) {
			return STATUS_200;
		}
		return STATUS_400;
	}
}
