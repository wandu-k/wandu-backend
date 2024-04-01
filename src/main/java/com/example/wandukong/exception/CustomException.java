package com.example.wandukong.exception;

public class CustomException extends Exception {
    public static class UserNotFoundException extends Exception {
        public UserNotFoundException() {
            // super(message);
            System.out.println("해당하는 회원이 없습니다.");
        }
    }

    public static class UserAlreadyExistsException extends Exception {
        public UserAlreadyExistsException(String message) {
            // super(message);
            System.out.println("이미 존재하는 회원 입니다.");
        }
    }

}
