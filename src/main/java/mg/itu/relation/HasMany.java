package mg.itu.relation;

import mg.itu.request.Entity;
import mg.itu.tools.ReflectionTools;

public final class HasMany extends Relation {

    public HasMany(Class<? extends Entity> classe,String idColumn,String idReference,String alias){
        super(classe,idColumn,idReference,alias);
    }

    public HasMany(Class<? extends Entity> classe,String idColumn,String idReference){
        super(classe,idColumn,idReference);
    }

    @Override
    public String join() {
        return "join "+ ReflectionTools.getEntityName(classe) +" on "+this.alias+"."+this.idColumn+" = "+this.aliasReference+"."+this.idReference;
    }
}