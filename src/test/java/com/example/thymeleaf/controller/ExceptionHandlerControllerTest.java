package com.example.thymeleaf.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ExceptionHandlerControllerTest {

    @InjectMocks
    private ExceptionHandlerController sut;

    @Test
    void shouldReturnNotFoundException() {
        //when
        ModelAndView modelAndView = sut.noSuchElementExceptionHandler();

        //then
        assertThat(modelAndView.getViewName()).isEqualTo("exception");
        assertThat(modelAndView.getModel()).isEqualTo(Map.of("status", HttpStatus.NOT_FOUND.value()));

    }


    @Test
    void shouldReturnDefaultException() {
        //when
        ModelAndView modelAndView = sut.defaultExceptionHandler();

        //then
        assertThat(modelAndView.getViewName()).isEqualTo("exception");
        assertThat(modelAndView.getModel()).isEqualTo(Map.of("status", HttpStatus.INTERNAL_SERVER_ERROR.value()));

    }


}