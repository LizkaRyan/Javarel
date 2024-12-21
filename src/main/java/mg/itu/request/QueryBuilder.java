package mg.itu.request;

import mg.itu.relation.Relation;
import mg.itu.tools.ReflectionTools;
import mg.itu.tools.UtilString;

import java.util.Objects;

public class QueryBuilder {
    public QueryBuilder(Class<? extends Entity> classe,String alias){
        this.setClasse(classe);
        this.alias=alias;
    }

    protected void setClasse(Class<? extends Entity> classe){
        this.classe=classe;
        this.table = ReflectionTools.getEntityName(classe);
    }

    public QueryBuilder(Class<? extends Entity> classe){
        this.setClasse(classe);
    }

    protected String table;
    protected String alias;
    protected String requete;
    protected String select="*";
    protected String join="";

    protected String where="";

    protected String limit="";

    protected String orderBy="";

    protected Class classe;

    public void setRequete(String requete){
        this.requete=requete;
    }

    public String getName(){
        if(alias!=null){
            return alias;
        }
        return table;
    }

    public QueryBuilder join(String liaison,String alias)throws Exception{
        Relation relation=ReflectionTools.getRelationEntity(this.classe,liaison);
        relation.alias=alias;
        relation.setAliasTable(this.getName());
        this.join+="\n\t"+relation.join();
        return this;
    }

    public QueryBuilder join(String liaison)throws Exception{
        Relation relation=ReflectionTools.getRelationEntity(this.classe,liaison);
        relation.setAliasTable(this.getName());
        this.join+="\n\t"+relation.join();
        return this;
    }

    public void where(String where){
        this.where="where "+where;
    }

    public void select(String select){
        this.select="select "+select+" from "+table;
        if(alias!=null){
            this.select+=" as "+alias;
        }
    }

    public void orderBy(String column,String ascendance){
        this.orderBy="order by "+column+" "+ascendance;
    }

    public String getRequest(){
        String request="select "+this.select+" from "+table;
        System.out.println(this.alias);
        if(alias!=null){
            request+=" as "+alias;
        }
        if(!Objects.equals(this.join, "")){
            request+=" "+this.join;
        }
        if(!Objects.equals(this.where, "")){
            request+=" "+this.where;
        }
        if(!Objects.equals(this.orderBy, "")){
            request+=" "+this.orderBy;
        }
        if(!Objects.equals(this.limit, "")){
            request+=" "+this.limit;
        }
        return request;
    }
}
