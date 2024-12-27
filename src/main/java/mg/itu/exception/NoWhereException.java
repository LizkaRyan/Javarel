package mg.itu.exception;

public class NoWhereException extends Exception{
    public NoWhereException(){
        super("Where must be called first and it is after the andWhere or orWhere can be called");
    }
}
