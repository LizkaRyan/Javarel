package mg.itu.relation;

import mg.itu.request.Entity;
import mg.itu.tools.ReflectionTools;

import java.util.ArrayList;
import java.util.List;

public final class BelongsToMany extends Relation{
    private final String linkTable;
    public BelongsToMany(Class<? extends Entity> classe,String idLocal,String idReference,String linkTable){
        super(classe,idLocal,idReference);
        this.linkTable=linkTable;
        this.whereRaw("\""+idReference+"\" in (select \""+idReference+"\" from \""+linkTable+"\" where \""+idLocal+"\" = :"+idLocal+")");
    }
    @Override
    public List<? extends Entity> execute() {
        return new ArrayList<>();
    }

    @Override
    public String join(){
        String join=" join \""+ linkTable +"\" on \""+linkTable+"\".\""+this.idLocal+"\" = "+this.getAliasTable()+".\""+this.idLocal+"\"";
        if(this.alias!=null){
            join+="\n\t join \""+ ReflectionTools.getEntityName(classe) +"\" as "+this.getName()+" on \""+linkTable+"\".\""+this.idReference+"\" = "+this.getName()+".\""+this.idReference+"\"";
            return join;
        }
        join+="\n\t join \""+ ReflectionTools.getEntityName(classe) +"\" on \""+linkTable+"\".\""+this.idReference+"\" = "+this.getName()+".\""+this.idReference+"\"";
        return join;
    }
}
