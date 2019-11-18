package com.bsu.nvasilyeva.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GenralErrorHandling {
    @ExceptionHandler(value = Exception.class)
    public String errorFired(HttpServletRequest request, Exception ex, Model model) {
        ex.printStackTrace();
        return "redirect:/home";
    }

}
