package mg.itu.request;

@FunctionalInterface
public interface ConditionBlock {
    QueryBuilder block(QueryBuilder queryBuilder)throws Exception;
}
