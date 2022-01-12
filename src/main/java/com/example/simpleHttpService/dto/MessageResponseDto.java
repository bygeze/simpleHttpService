package com.example.simpleHttpService.dto;

public class MessageResponseDto extends ResponseDto {
	private String msg;
	
	public MessageResponseDto(String msg) {
		this.setMsg(msg);
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
