package mg.itu.exception;

public class NoWhereException extends Exception{
    public NoWhereException(String typeWhere){
        super("Where or WhereRaw must be called first and it is after that the "+typeWhere+" can be called");
    }
}
