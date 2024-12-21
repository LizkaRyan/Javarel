package mg.itu.request;

import mg.itu.relation.HasMany;
import mg.itu.tools.ReflectionTools;

public class Entity {
    protected String alias;
    public QueryBuilder createQuery(String alias){
        this.alias=alias;
        return new QueryBuilder(this.getClass(),alias);
    }

    public QueryBuilder createQuery(){
        this.alias=ReflectionTools.getEntityName(this.getClass());
        return new QueryBuilder(this.getClass());
    }

    public HasMany hasMany(Class<? extends Entity> classe, String idColumn, String idReference){
        return new HasMany(classe,idColumn,idReference);
    }
}
