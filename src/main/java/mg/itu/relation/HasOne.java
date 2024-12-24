package mg.itu.relation;

import mg.itu.request.Entity;
import mg.itu.tools.ReflectionTools;

import java.util.ArrayList;
import java.util.List;

public final class HasOne extends Relation {
    public HasOne(Class<? extends Entity> classe,String idColumn,String idReference){
        super(classe,idColumn,idReference);
        this.whereRaw("\""+idReference+"\" = :"+idLocal);
    }

    @Override
    public Entity execute(){
        return new Entity();
    }
}
