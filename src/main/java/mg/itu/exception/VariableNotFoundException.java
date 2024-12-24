package mg.itu.exception;

public class VariableNotFoundException extends RuntimeException{
    public VariableNotFoundException(String variable){
        super("The variable "+variable+" is not found");
    }
}
