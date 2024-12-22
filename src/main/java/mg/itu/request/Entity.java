package mg.itu.request;

import mg.itu.relation.BelongsTo;
import mg.itu.relation.HasMany;
import mg.itu.relation.HasOne;
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

    public HasMany hasMany(Class<? extends Entity> classe){
        return new HasMany(classe,ReflectionTools.getIdByDefault(this.getClass()),ReflectionTools.getIdByDefault(this.getClass()));
    }

    public HasMany hasMany(Class<? extends Entity> classe, String idReference){
        return new HasMany(classe,ReflectionTools.getIdByDefault(this.getClass()),idReference);
    }

    public HasMany hasMany(Class<? extends Entity> classe, String idReference,String idLocal){
        return new HasMany(classe,idLocal,idReference);
    }

    public HasOne hasOne(Class<? extends Entity> classe){
        return new HasOne(classe,ReflectionTools.getIdByDefault(this.getClass()),ReflectionTools.getIdByDefault(classe));
    }

    public HasOne hasOne(Class<? extends Entity> classe, String idReference){
        return new HasOne(classe,ReflectionTools.getIdByDefault(this.getClass()),idReference);
    }

    public HasOne hasOne(Class<? extends Entity> classe, String idColumn, String idReference){
        return new HasOne(classe,idColumn,idReference);
    }

    public BelongsTo belongsTo(Class<? extends Entity> classe){
        return new BelongsTo(classe,ReflectionTools.getIdByDefault(classe),ReflectionTools.getIdByDefault(classe));
    }

    public BelongsTo belongsTo(Class<? extends Entity> classe,String idReference){
        return new BelongsTo(classe,ReflectionTools.getIdByDefault(classe),idReference);
    }

    public BelongsTo belongsTo(Class<? extends Entity> classe,String idReference,String idLocal){
        return new BelongsTo(classe,idLocal,idReference);
    }
}
