package mg.itu.request;

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

    protected String getName(){
        if(alias!=null){
            return alias;
        }
        return table;
    }

    public void join(String liaison,String alias){
        String idLiaison=UtilString.makePascalCase(liaison);
        this.join+=" join "+liaison+" as "+alias+" "+getName()+".id"+idLiaison+" = "+alias+".id"+idLiaison;
    }

    public void join(String liaison){
        String idLiaison=UtilString.makePascalCase(liaison);
        this.join+=" join "+liaison+" "+getName()+".id"+idLiaison+" = "+liaison+".id"+idLiaison;
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
        if(!Objects.equals(this.alias,"")){
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
