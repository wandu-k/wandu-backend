package com.example.wandukong.exception;

public class CustomException extends Exception {
    public static class UserNotFoundException extends Exception {
        public UserNotFoundException() {
            // 만약 예외처리 될때 추가적으로 뭔갈 더 하고싶다면 여기에 작성
        }
    }

    public static class UserAlreadyExistsException extends Exception {
        public UserAlreadyExistsException() {
            // 만약 예외처리 될때 추가적으로 뭔갈 더 하고싶다면 여기에 작성
        }
    }

    public static class IncorrectPasswordException extends Exception {
        public IncorrectPasswordException() {
            // 만약 예외처리 될때 추가적으로 뭔갈 더 하고싶다면 여기에 작성
        }
    }

    public static class HomeNotFoundException extends Exception {
        public HomeNotFoundException() {
            // 만약 예외처리 될때 추가적으로 뭔갈 더 하고싶다면 여기에 작성
        }
    }

    public static class PostNotFoundException extends Exception {
        public PostNotFoundException() {
            // 만약 예외처리 될때 추가적으로 뭔갈 더 하고싶다면 여기에 작성
        }
    }

    public static class BoardNotFoundException extends Exception {
        public BoardNotFoundException() {
            // 만약 예외처리 될때 추가적으로 뭔갈 더 하고싶다면 여기에 작성
        }
    }

    public static class PermissionDeniedException extends Exception {
        public PermissionDeniedException() {
            // 만약 예외처리 될때 추가적으로 뭔갈 더 하고싶다면 여기에 작성
        }
    }

    public static class itemlistNotFoundException extends Exception {
        public itemlistNotFoundException() {
            // 만약 예외처리 될때 추가적으로 뭔갈 더 하고싶다면 여기에 작성
        }
    }

    public static class itemUploadNotFoundException extends Exception {
        public itemUploadNotFoundException() {
            // 만약 예외처리 될때 추가적으로 뭔갈 더 하고싶다면 여기에 작성
        }
    }

    public static class NoSuchElementException extends Exception {
        public NoSuchElementException() {
            // 만약 예외처리 될때 추가적으로 뭔갈 더 하고싶다면 여기에 작성
        }

    }

    public static class MethodArgumentNotValidException extends Exception {
        public MethodArgumentNotValidException() {

        }

    }

    public static class BadRequestException extends Exception {
        public BadRequestException() {

        }

    }
}
