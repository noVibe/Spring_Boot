package pro.sky.lessons.spring_boot.exceptions;

public class IdNotFound extends RuntimeException {
    public IdNotFound() {
        super("Nothing found for this ID");
    }
}
