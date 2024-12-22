package mg.itu.relation;

import mg.itu.request.Entity;
import mg.itu.tools.ReflectionTools;

public final class HasOne extends Relation {
    public HasOne(Class<? extends Entity> classe,String idColumn,String idReference){
        super(classe,idColumn,idReference);
    }

    @Override
    public String join() {
        if(this.alias!=null){
            return "join "+ ReflectionTools.getEntityName(classe) +" as "+alias+" on "+this.getAliasTable()+".\""+this.idLocal+"\" = "+this.getName()+".\""+this.idReference+"\"";
        }
        return "join "+ ReflectionTools.getEntityName(classe) +" on "+this.getAliasTable()+".\""+this.idLocal+"\" = "+this.getName()+".\""+this.idReference+"\"";
    }
}
