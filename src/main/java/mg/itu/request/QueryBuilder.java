package mg.itu.request;

import mg.itu.exception.VariableNotFoundException;
import mg.itu.relation.HasOne;
import mg.itu.relation.Relation;
import mg.itu.tools.ReflectionTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class QueryBuilder {
    protected String table;
    protected String alias;
    protected String select="*";
    protected String join="";

    protected String where="";

    protected String having="";

    protected String limit="";

    protected String orderBy="";

    protected String groupBy="";

    protected List<Relation> joinInfos=new ArrayList<Relation>();
    protected Class classe;
    public QueryBuilder(Class<? extends Entity> classe,String alias){
        this.setClasse(classe);
        this.alias=alias;
        Relation relation=new HasOne(classe,null,null);
        relation.setAlias(alias);
        relation.setAliasTable(alias);
        this.joinInfos.add(relation);
    }

    protected void setClasse(Class<? extends Entity> classe){
        this.classe=classe;
        this.table = ReflectionTools.getEntityName(classe);
    }

    public Class<? extends Entity> getClasse(){
        return this.classe;
    }

    public QueryBuilder(Class<? extends Entity> classe){
        this.setClasse(classe);
    }

    public String getName(){
        if(alias!=null){
            return alias;
        }
        return "\""+table+"\"";
    }

    public void addJoin(Relation newRelation,String alias){
        for (Relation relation:this.joinInfos) {
            if(relation.isTheSame(newRelation)){
                relation.setAlias(alias);
                return;
            }
        }
        this.joinInfos.add(newRelation);
    }

    public void addJoin(Relation newRelation){
        for (Relation relation:this.joinInfos) {
            if(relation.isTheSame(newRelation)){
                return;
            }
        }
        this.joinInfos.add(newRelation);
    }

    public QueryBuilder join(String link,String alias)throws Exception{
        Relation relation=this.getRelation(link,0);
        relation.setAlias(alias);
        relation.setAliasTable(this.getName());
        this.addJoin(relation,alias);
        //this.join+="\n\t"+relation.join();
        return this;
    }

    public QueryBuilder join(String link)throws Exception{
        Relation relation=this.getRelation(link,0);
        relation.setAliasTable(this.getName());
        this.addJoin(relation);
        //this.join+="\n\t"+relation.join();
        return this;
    }

    protected void joinAll(){
        for (int i = 1; i < this.joinInfos.size(); i++) {
            this.join+="\n\t"+this.joinInfos.get(i).join();
        }
    }

    public QueryBuilder whereRaw(String where){
        String and="";
        if(!Objects.equals(this.where, "")){
            and=" and";
        }
        this.where+=and+" "+where;
        return this;
    }

    public QueryBuilder where(String columnLeft,String operation,Object object)throws Exception{
        String where=this.getJoinWhere(columnLeft);
        this.where=where+" "+operation+" "+object;
        return this;
    }

    public QueryBuilder select(String select){
        if(this.select!=""){
            this.select+=", ";
        }
        this.select=select;
        return this;
    }

    public QueryBuilder having(String having){
        String and="";
        if(!Objects.equals(this.having, "")){
            and=" and";
        }
        this.having+=and+" "+having;
        return this;
    }

    public void orderBy(String column,String ascendance){
        this.orderBy="order by "+column+" "+ascendance;
    }

    public String getRequest(){
        this.joinAll();
        String request="select "+this.select+" from \""+table+"\"";
        if(alias!=null){
            request+=" as "+alias;
        }
        if(!Objects.equals(this.join, "")){
            request+=" "+this.join;
        }
        if(!Objects.equals(this.where, "")){
            request+="\n where "+this.where;
        }
        if(!Objects.equals(this.having, "")){
            request+="\n having"+this.having;
        }
        if(!Objects.equals(this.orderBy, "")){
            request+="\n "+this.orderBy;
        }
        if(!Objects.equals(this.limit, "")){
            request+="\n "+this.limit;
        }
        if(!Objects.equals(this.groupBy, "")){
            request+="\n group by"+this.groupBy;
        }
        return request;
    }

    public QueryBuilder groupBy(String... groups){
        for (String group:groups) {
            this.groupBy+=" "+group+",";
        }
        this.groupBy=this.groupBy.substring(0,this.groupBy.length()-1);
        return this;
    }

    protected Relation getVariable(String variable)throws VariableNotFoundException {
        for (Relation joinInfo:this.joinInfos) {
            if(joinInfo.getName().compareTo(variable)==0 || joinInfo.getName().compareTo("\""+variable+"\"")==0){
                return joinInfo;
            }
        }
        throw new VariableNotFoundException(variable);
    }

    public String getJoinWhere(String sentence)throws Exception{
        String[] field=sentence.split("[.]");
        Relation joinInfo=this.getDeepJoin(field,1);
        return joinInfo.getName()+"."+field[field.length-1];
    }

    protected Relation getRelation(String sentence,int limit)throws Exception{
        String[] field=sentence.split("[.]");
        return this.getDeepJoin(field,limit);
    }

    protected Relation getDeepJoin(String[] field,int limit)throws Exception{
        if(field.length==1){
            return ReflectionTools.getRelationEntity(this.classe,field[0]);
        }
        Class<? extends Entity> entity=this.getClasse();
        try{
            Relation joinInfo=this.getVariable(field[0]);
            int i;
            for(i = 1; i < field.length - limit; i++){
                joinInfo=getRelation(field[i], joinInfo.getClasse(), joinInfo.getName());
            }
            if(joinInfo.isTheSame(this.joinInfos.get(0))){
                return ReflectionTools.getRelationEntity(this.classe,field[i]);
            }
            return joinInfo;
        }
        catch (Exception exception){
            throw exception;
        }
    }

    protected Relation getRelation(String field,Class<? extends Entity> entity,String alias)throws Exception{
        try{
            Relation newRelation=ReflectionTools.getRelationEntity(entity,field);
            for (Relation joinInfo:this.joinInfos) {
                if(joinInfo.isTheSame(newRelation)){
                    return joinInfo;
                }
            }
            newRelation.setAliasTable(alias);
            this.joinInfos.add(newRelation);
            return newRelation;
        }
        catch (Exception ex){
            throw ex;
        }
    }
}