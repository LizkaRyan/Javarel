package mg.itu.request;

@FunctionalInterface
public interface WhereBlock {
    QueryBuilder block(QueryBuilder queryBuilder)throws Exception;
}
