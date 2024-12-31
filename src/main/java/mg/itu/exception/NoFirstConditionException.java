package mg.itu.exception;

public class NoFirstConditionException extends Exception{
    public NoFirstConditionException(String condition,String typeConditionAfter){
        super(condition+" or "+condition+"Raw must be called first and it is after that the "+typeConditionAfter+" can be called");
    }
}
