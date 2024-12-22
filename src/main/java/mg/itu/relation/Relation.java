package mg.itu.relation;

import mg.itu.request.Entity;
import mg.itu.request.QueryBuilder;

public abstract class Relation extends QueryBuilder {
    protected String idLocal;
    protected String idReference;
    private String aliasTable;
    public Relation(Class<? extends Entity> classe,String idLocal,String idReference){
        super(classe);
        this.idLocal=idLocal;
        this.idReference=idReference;
        this.where(idReference+" = :"+idLocal);
    }

    public void setAliasTable(String aliasTable){
        this.aliasTable=aliasTable;
    }

    public String getAliasTable(){
        return this.aliasTable;
    }

    public abstract String join();
}
