package com.tree.search.exception;


import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class TreeSearchGlobalExceptionHandler {

    @ExceptionHandler(TreeNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(TreeNotFoundException ex, WebRequest request) {
        TreeSearchErrorDetails treeSearchErrorDetails = new TreeSearchErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(treeSearchErrorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
        TreeSearchErrorDetails treeSearchErrorDetails = new TreeSearchErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(treeSearchErrorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

