package pro.sky.lessons.spring_boot.exceptions;

public class AlreadyExistsException extends RuntimeException{
    public AlreadyExistsException() {
        super("One or more already exists");
    }
}
