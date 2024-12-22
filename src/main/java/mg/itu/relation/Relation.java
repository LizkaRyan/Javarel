package mg.itu.relation;

import mg.itu.request.Entity;
import mg.itu.request.QueryBuilder;
import mg.itu.tools.ReflectionTools;

public abstract class Relation extends QueryBuilder {
    protected String idLocal;
    protected String idReference;
    private String aliasTable;
    public Relation(Class<? extends Entity> classe,String idLocal,String idReference){
        super(classe);
        this.idLocal=idLocal;
        this.idReference=idReference;
    }

    public void setAliasTable(String aliasTable){
        this.aliasTable=aliasTable;
    }

    public String getAliasTable(){
        return this.aliasTable;
    }

    public String join() {
        if(this.alias!=null){
            return " join "+ ReflectionTools.getEntityName(classe) +" as "+alias+" on "+this.getAliasTable()+".\""+this.idLocal+"\" = "+this.getName()+".\""+this.idReference+"\"";
        }
        return " join "+ ReflectionTools.getEntityName(classe) +" on "+this.getAliasTable()+".\""+this.idLocal+"\" = "+this.getName()+".\""+this.idReference+"\"";
    }

    public abstract Object execute();
}
