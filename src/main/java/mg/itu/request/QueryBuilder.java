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
        return "\""+table+"\"";
    }

    public QueryBuilder join(String link,String alias)throws Exception{
        Relation relation=ReflectionTools.getRelationEntity(this.classe,link);
        relation.alias=alias;
        relation.setAliasTable(this.getName());
        this.join+="\n\t"+relation.join();
        return this;
    }

    public QueryBuilder join(String link)throws Exception{
        Relation relation=ReflectionTools.getRelationEntity(this.classe,link);
        relation.setAliasTable(this.getName());
        this.join+="\n\t"+relation.join();
        return this;
    }

    public QueryBuilder where(String where){
        String and="";
        if(!Objects.equals(this.where, "")){
            and=" and";
        }
        this.where+=and+" "+where;
        return this;
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
        String request="select "+this.select+" from \""+table+"\"";
        if(alias!=null){
            request+=" as "+alias;
        }
        if(!Objects.equals(this.join, "")){
            request+=" "+this.join;
        }
        if(!Objects.equals(this.where, "")){
            request+="\n where"+this.where;
        }
        if(!Objects.equals(this.orderBy, "")){
            request+="\n "+this.orderBy;
        }
        if(!Objects.equals(this.limit, "")){
            request+="\n "+this.limit;
        }
        return request;
    }
}
