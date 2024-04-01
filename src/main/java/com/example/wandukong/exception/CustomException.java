package com.example.wandukong.exception;

public class CustomException extends Exception {
    public static class UserNotFoundException extends Exception {
        public UserNotFoundException() {
            // super(message);
        }
    }

}
