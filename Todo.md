# JAVAREL

## QueryBuilder
    QueryBuilder(Class classe)
    QueryBuilder(Class classe,String alias)
    join(String liaison)
        execute has(One,Many) or belongsTo of the entity
    join(String liaison,String alias)
    where(String where)
    select(String select)
    limit(int limit)
    offset(int offset)
    orderby(String column)
    orderby(String column,String ascendance)
    execute()

## Entity
    createQueryBuilder()
    createQueryBuilder(String alias)
    hasOne(Class classe)
    hasOne(Class classe,String idColumn)
    hasOne(Class classe,String idColumn,String idReference)
    hasMany(Class classe)
    hasMany(Class classe,String idColumn)
    hasMany(Class classe,String idColumn,String idReference)
    belongsTo(Class classe)
    belongsTo(Class classe,String idColumn)
    belongsTo(Class classe,String idColumn,String idReference)