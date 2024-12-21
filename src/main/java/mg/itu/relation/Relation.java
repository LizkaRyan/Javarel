package mg.itu.relation;

import mg.itu.request.Entity;
import mg.itu.request.QueryBuilder;

public abstract class Relation extends QueryBuilder {
    protected String idColumn;
    protected String idReference;
    protected String aliasReference;
    public Relation(Class<? extends Entity> classe,String idColumn,String idReference){
        super(classe);
        this.idColumn=idColumn;
        this.idReference=idReference;
    }

    public void setAliasReference(String aliasReference){
        this.aliasReference=aliasReference;
    }

    public Relation(Class<? extends Entity> classe,String idColumn,String idReference,String alias){
        super(classe,alias);
        this.idColumn=idColumn;
        this.idReference=idReference;
    }

    public abstract String join();
}
