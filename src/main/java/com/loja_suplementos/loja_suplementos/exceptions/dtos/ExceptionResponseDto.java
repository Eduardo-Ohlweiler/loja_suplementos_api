package com.loja_suplementos.loja_suplementos.exceptions.dtos;

public class ExceptionResponseDto {
    private String message;

    public ExceptionResponseDto(String message) {
        this.setMessage(message);
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }
}
