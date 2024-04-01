package com.example.wandukong.exception;

public class CustomException extends Exception {
    public static class UserNotFoundException extends Exception {
        public UserNotFoundException() {
            // super(message);
            System.out.println("해당하는 유저가 없습니다.");
        }
    }

}
