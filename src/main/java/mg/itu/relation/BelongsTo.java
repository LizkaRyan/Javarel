package mg.itu.relation;

import mg.itu.request.Entity;
import mg.itu.tools.ReflectionTools;

public class BelongsTo extends Relation{
    public BelongsTo(Class<? extends Entity> classe, String idColumn, String idReference){
        super(classe,idColumn,idReference);
    }

    @Override
    public String join() {
        if(this.alias!=null){
            return "join "+ ReflectionTools.getEntityName(classe) +" as "+alias+" on "+this.getAliasTable()+".\""+this.idColumn+"\" = "+this.getName()+".\""+this.idReference+"\"";
        }
        return "join "+ ReflectionTools.getEntityName(classe) +" on "+this.getAliasTable()+".\""+this.idColumn+"\" = "+this.getName()+".\""+this.idReference+"\"";
    }
}
