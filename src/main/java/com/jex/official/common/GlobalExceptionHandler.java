package com.jex.official.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object defaultErrorHandler(HttpServletRequest req, Exception ex){
        logger.error("---DefaultException Handler begin---");
        logger.error("Url     :{}", req.getRequestURL());
        logger.error("Class   :{}", ex.getClass());
        logger.error("message :{}", ex.getMessage());
        logger.error("throwable", ex);
        logger.error("---DefaultException Handler end  ---");
        String msg = ex.getMessage() == null? ex.getClass().getName(): ex.getMessage();
        return new ResponseMessage(ResponseMessage.SYSTEM_ERROR, msg);
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseBody
    public Object responseHandlerNoFound(HttpServletRequest req, Exception ex){
        logger.error("---NoHandlerFoundException Handler begin---");
        logger.error("Url     :{}", req.getRequestURL());
        logger.error("---NoHandlerFoundException Handler end  ---");
        String msg = ex.getMessage() == null? ex.getClass().getName(): ex.getMessage();
        ResponseMessage responseMessage = new ResponseMessage(ResponseMessage.NO_MAPPING_URL, "url not exists");
        ResponseEntity<ResponseMessage> responseEntity = new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.NOT_FOUND);
        return responseEntity;
    }

    @ExceptionHandler(value = ResponseBaseException.class)
    @ResponseBody
    public Object responseBaseHandler(HttpServletRequest req, ResponseBaseException ex){
        logger.error("---ResponseBaseException Handler begin---");
        logger.error("Host    :{}", req.getRemoteHost());
        logger.error("Url     :{}", req.getRequestURL());
        logger.error("Url     :{}", req.getQueryString());
        logger.error("message :{}", ex.getMessage());
        logger.error("---ResponseBaseException Handler end  ---");
        return new ResponseMessage(ex.getCode(), ex.getMessage());
    }
}
