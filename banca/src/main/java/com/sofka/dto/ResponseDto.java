package com.sofka.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class ResponseDto<T> {

	public ResponseDto(T data) {
		this.data = data;
	}

	public ResponseDto(Map<String, String> state, T data) {
		this.code = state.get("code");
		this.message = state.get("message");
		this.data = data;
	}

	public ResponseDto(Map<String, String> state) {
		this.code = state.get("code");
		this.message = state.get("message");
	}
	public ResponseDto(ErrorDto error) {
		this.dtoError = error;
	}

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("Data")
	private T data;
	@JsonInclude(Include.NON_NULL)
	private String code  = "200";
	@JsonInclude(Include.NON_NULL)
	private String  message  = "Successful";
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("Error")
	private ErrorDto dtoError;
	
}