package mg.itu.request;

import mg.itu.relation.*;
import mg.itu.tools.ReflectionTools;

public class Entity {
    public QueryBuilder createQuery(String alias){
        return new QueryBuilder(this.getClass(),alias);
    }

    public QueryBuilder createQuery(){
        QueryBuilder queryBuilder=new QueryBuilder(this.getClass());
        Relation relation=new HasOne(this.getClass(),null,null);
        relation.setAlias(queryBuilder.getName());
        relation.setAliasTable(queryBuilder.getName());
        queryBuilder.joinInfos.add(relation);
        return queryBuilder;
    }

    protected HasMany hasMany(Class<? extends Entity> classe){
        return hasMany(classe,ReflectionTools.getIdByDefault(this.getClass()),ReflectionTools.getIdByDefault(this.getClass()));
    }

    protected HasMany hasMany(Class<? extends Entity> classe, String idReference){
        return hasMany(classe,ReflectionTools.getIdByDefault(this.getClass()),idReference);
    }

    protected HasMany hasMany(Class<? extends Entity> classe, String idReference,String idLocal){
        return new HasMany(classe,idLocal,idReference);
    }

    protected HasOne hasOne(Class<? extends Entity> classe){
        return hasOne(classe,ReflectionTools.getIdByDefault(this.getClass()),ReflectionTools.getIdByDefault(classe));
    }

    protected HasOne hasOne(Class<? extends Entity> classe, String idReference){
        return hasOne(classe,ReflectionTools.getIdByDefault(this.getClass()),idReference);
    }

    protected HasOne hasOne(Class<? extends Entity> classe, String idColumn, String idReference){
        return new HasOne(classe,idColumn,idReference);
    }

    protected BelongsTo belongsTo(Class<? extends Entity> classe){
        return belongsTo(classe,ReflectionTools.getIdByDefault(classe),ReflectionTools.getIdByDefault(classe));
    }

    protected BelongsTo belongsTo(Class<? extends Entity> classe,String idReference){
        return belongsTo(classe,ReflectionTools.getIdByDefault(classe),idReference);
    }

    protected BelongsTo belongsTo(Class<? extends Entity> classe,String idReference,String idLocal){
        return new BelongsTo(classe,idLocal,idReference);
    }

    protected BelongsToMany belongsToMany(Class<? extends Entity> classe,String linkTable){
        return belongsToMany(classe,linkTable,ReflectionTools.getIdByDefault(classe),ReflectionTools.getIdByDefault(this.getClass()));
    }

    protected BelongsToMany belongsToMany(Class<? extends Entity> classe,String linkTable,String idReference){
        return belongsToMany(classe,linkTable,idReference,ReflectionTools.getIdByDefault(this.getClass()));
    }

    protected BelongsToMany belongsToMany(Class<? extends Entity> classe,String linkTable,String idReference,String idLocal){
        return new BelongsToMany(classe,idLocal,idReference,linkTable);
    }
}
