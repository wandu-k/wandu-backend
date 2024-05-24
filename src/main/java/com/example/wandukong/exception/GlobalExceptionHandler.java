package com.example.wandukong.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.wandukong.exception.CustomException.*;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        String message = "해당하는 회원이 없습니다.";
        log.info(message);
        return new ResponseEntity<>(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        String message = "이미 존재하는 회원 입니다.";
        log.info(message);
        return new ResponseEntity<>(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleIncorrectPasswordException(IncorrectPasswordException e) {
        String message = "비밀번호가 일치 하지 않습니다.";
        log.info(message);
        return new ResponseEntity<>(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleHomeNotFoundException(HomeNotFoundException e) {
        String message = "해당하는 미니홈이 없습니다.";
        log.info(message);
        return new ResponseEntity<>(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<?> handlePostNotFoundException(PostNotFoundException e) {
        String message = "게시글을 찾을 수 없습니다.";
        log.info(message);
        return new ResponseEntity<>(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<?> handlePermissionDeniedException(PermissionDeniedException e) {
        String message = "권한이 없습니다.";
        log.info(message);
        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException e) {

        String message = "해당하는 게시물이 없습니다.";
        log.info(message);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleBoardNotFoundException(BoardNotFoundException e) {

        String message = "해당하는 게시판이 없습니다.";
        log.info(message);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        String message = "해당하는 페이지가 없습니다.";
        log.info(message);
        return new ResponseEntity<>(message, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler
    public ResponseEntity<?> badRequestException(BadRequestException e) {

        String message = "잘못된 요청입니다.";
        log.info(message);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}