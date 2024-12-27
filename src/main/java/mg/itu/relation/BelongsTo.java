package mg.itu.relation;

import mg.itu.request.Entity;
import mg.itu.tools.ReflectionTools;

import java.util.ArrayList;
import java.util.List;

public final class BelongsTo extends Relation{
    public BelongsTo(Class<? extends Entity> classe, String idLocal, String idReference){
        super(classe,idLocal,idReference);
        this.whereRaw("\""+idReference+"\" = :"+idLocal);
    }

    @Override
    public Entity execute(){
        return new Entity();
    }
}
