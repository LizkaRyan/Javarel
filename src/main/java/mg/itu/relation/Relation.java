package mg.itu.relation;

import mg.itu.request.Entity;
import mg.itu.request.QueryBuilder;

public abstract class Relation extends QueryBuilder {
    protected String idColumn;
    protected String idReference;
    private String aliasTable;
    public Relation(Class<? extends Entity> classe,String idColumn,String idReference){
        super(classe);
        this.idColumn=idColumn;
        this.idReference=idReference;
    }

    public void setAliasTable(String aliasTable){
        this.aliasTable=aliasTable;
    }

    public String getAliasTable(){
        return this.aliasTable;
    }

    public abstract String join();
}
