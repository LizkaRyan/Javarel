package mg.itu.request;

import mg.itu.relation.*;
import mg.itu.tools.ReflectionTools;

public class Entity {
    protected String alias;
    public QueryBuilder createQuery(String alias){
        this.alias=alias;
        return new QueryBuilder(this.getClass(),alias);
    }

    public QueryBuilder createQuery(){
        this.alias=ReflectionTools.getEntityName(this.getClass());
        QueryBuilder queryBuilder=new QueryBuilder(this.getClass());
        Relation relation=new HasOne(this.getClass(),null,null);
        relation.setAlias(queryBuilder.getName());
        relation.setAliasTable(queryBuilder.getName());
        queryBuilder.joinInfos.add(relation);
        return queryBuilder;
    }

    public HasMany hasMany(Class<? extends Entity> classe){
        return hasMany(classe,ReflectionTools.getIdByDefault(this.getClass()),ReflectionTools.getIdByDefault(this.getClass()));
    }

    public HasMany hasMany(Class<? extends Entity> classe, String idReference){
        return hasMany(classe,ReflectionTools.getIdByDefault(this.getClass()),idReference);
    }

    public HasMany hasMany(Class<? extends Entity> classe, String idReference,String idLocal){
        return new HasMany(classe,idLocal,idReference);
    }

    public HasOne hasOne(Class<? extends Entity> classe){
        return hasOne(classe,ReflectionTools.getIdByDefault(this.getClass()),ReflectionTools.getIdByDefault(classe));
    }

    public HasOne hasOne(Class<? extends Entity> classe, String idReference){
        return hasOne(classe,ReflectionTools.getIdByDefault(this.getClass()),idReference);
    }

    public HasOne hasOne(Class<? extends Entity> classe, String idColumn, String idReference){
        return new HasOne(classe,idColumn,idReference);
    }

    public BelongsTo belongsTo(Class<? extends Entity> classe){
        return belongsTo(classe,ReflectionTools.getIdByDefault(classe),ReflectionTools.getIdByDefault(classe));
    }

    public BelongsTo belongsTo(Class<? extends Entity> classe,String idReference){
        return belongsTo(classe,ReflectionTools.getIdByDefault(classe),idReference);
    }

    public BelongsTo belongsTo(Class<? extends Entity> classe,String idReference,String idLocal){
        return new BelongsTo(classe,idLocal,idReference);
    }

    public BelongsToMany belongsToMany(Class<? extends Entity> classe,String linkTable){
        return belongsToMany(classe,linkTable,ReflectionTools.getIdByDefault(classe),ReflectionTools.getIdByDefault(this.getClass()));
    }

    public BelongsToMany belongsToMany(Class<? extends Entity> classe,String linkTable,String idReference){
        return belongsToMany(classe,linkTable,idReference,ReflectionTools.getIdByDefault(this.getClass()));
    }

    public BelongsToMany belongsToMany(Class<? extends Entity> classe,String linkTable,String idReference,String idLocal){
        return new BelongsToMany(classe,idLocal,idReference,linkTable);
    }
}
