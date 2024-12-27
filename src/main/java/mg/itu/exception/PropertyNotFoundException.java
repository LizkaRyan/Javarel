package mg.itu.exception;

public class PropertyNotFoundException extends RuntimeException{
    public PropertyNotFoundException(String variable){
        super("The variable "+variable+" is not found");
    }
}
