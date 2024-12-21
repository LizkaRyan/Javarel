package mg.itu.relation;

import mg.itu.request.Entity;

public final class HasOne {
    protected Class<? extends Entity> classe;
    protected String idColumn;
    protected String idReference;

    public HasOne(Class<? extends Entity> classe,String idColumn,String idReference){
        this.classe=classe;
        this.idColumn=idColumn;
        this.idReference=idReference;
    }
}
