package com.sofka.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@Data
@JsonRootName("Error")
public class ErrorDto {

    private String code;

    private String message;

	public ErrorDto(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

    
}