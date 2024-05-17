package com.colab1.funfinder.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseDto {
	private String result;
	private Object data;
	
    public List<?> getDataAsList() {
        if (data instanceof List<?>) {
            return (List<?>) data;
        } else {
            return null; // or throw an exception if needed
        }
    }
}
