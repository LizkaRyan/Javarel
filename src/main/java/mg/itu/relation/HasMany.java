package mg.itu.relation;

import mg.itu.request.Entity;
import mg.itu.tools.ReflectionTools;

import java.util.ArrayList;
import java.util.List;

public final class HasMany extends Relation {

    public HasMany(Class<? extends Entity> classe,String idColumn,String idReference){
        super(classe,idColumn,idReference);
        this.whereRaw("\""+idReference+"\" = :"+idLocal);
    }

    @Override
    public List<? extends Entity> execute(){
        return new ArrayList<>();
    }
}