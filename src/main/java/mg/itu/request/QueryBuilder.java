package mg.itu.request;

import mg.itu.exception.NoFirstConditionException;
import mg.itu.exception.PropertyNotFoundException;
import mg.itu.relation.HasOne;
import mg.itu.relation.Relation;
import mg.itu.tools.ReflectionTools;
import mg.itu.tools.UtilString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QueryBuilder implements Cloneable {
    protected String table;
    protected String alias;
    protected String select="*";
    protected String join="";
    protected String having="";
    protected String limit="";
    protected String orderBy="";
    protected String groupBy="";
    protected List<Relation> joinInfos=new ArrayList<Relation>();
    protected Class<? extends Entity> classe;
    protected List<ConditionInfo> whereInfos=new ArrayList<ConditionInfo>();
    protected List<ConditionInfo> havingInfos=new ArrayList<ConditionInfo>();
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

    protected String getName(){
        if(alias!=null){
            return alias;
        }
        return "\""+table+"\"";
    }

    protected void addJoin(Relation newRelation,String alias){
        for (Relation relation:this.joinInfos) {
            if(relation.isTheSame(newRelation)){
                relation.setAlias(alias);
                return;
            }
        }
        this.joinInfos.add(newRelation);
    }

    protected void addJoin(Relation newRelation){
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
        this.whereInfos.add(new ConditionInfo(where));
        return this;
    }

    public QueryBuilder where(String columnLeft,String operation,String columnRight)throws Exception{
        this.whereInfos.add(0,new ConditionInfo(columnLeft,operation,columnRight));
        return this;
    }

    public QueryBuilder where(ConditionBlock where)throws Exception{
        this.whereInfos.add(new ConditionInfo(where,true));
        return this;
    }

    public QueryBuilder andWhere(ConditionBlock where)throws Exception{
        if(this.whereInfos.size()==0){
            throw new NoFirstConditionException("Where","andWhere");
        }
        this.whereInfos.add(new ConditionInfo(where,"and",true));
        return this;
    }

    public QueryBuilder orWhere(ConditionBlock where)throws Exception{
        if(this.whereInfos.size()==0){
            throw new NoFirstConditionException("Where","andWhere");
        }
        this.whereInfos.add(new ConditionInfo(where,"or",true));
        return this;
    }

    public QueryBuilder andWhere(String columnLeft,String operation,String columnRight)throws NoFirstConditionException {
        if(this.whereInfos.size()==0){
            throw new NoFirstConditionException("Where","andWhere");
        }
        this.whereInfos.add(new ConditionInfo(columnLeft,operation,columnRight,"and"));
        return this;
    }

    public QueryBuilder orWhere(String columnLeft,String operation,String columnRight)throws NoFirstConditionException {
        if(this.whereInfos.size()==0){
            throw new NoFirstConditionException("Where","orWhere");
        }
        this.whereInfos.add(new ConditionInfo(columnLeft,operation,columnRight,"or"));
        return this;
    }

    public QueryBuilder havingRaw(String condition){
        this.havingInfos.add(new ConditionInfo(condition));
        return this;
    }

    public QueryBuilder having(String columnLeft,String operation,String columnRight)throws Exception{
        this.havingInfos.add(0,new ConditionInfo(columnLeft,operation,columnRight));
        return this;
    }

    public QueryBuilder having(ConditionBlock condition)throws Exception{
        this.havingInfos.add(new ConditionInfo(condition,false));
        return this;
    }

    public QueryBuilder andHaving(ConditionBlock condition)throws Exception{
        if(this.havingInfos.size()==0){
            throw new NoFirstConditionException("Having","andHaving");
        }
        this.havingInfos.add(new ConditionInfo(condition,"and",false));
        return this;
    }

    public QueryBuilder orHaving(ConditionBlock condition)throws Exception{
        if(this.havingInfos.size()==0){
            throw new NoFirstConditionException("Having","andHaving");
        }
        this.havingInfos.add(new ConditionInfo(condition,"or",false));
        return this;
    }

    public QueryBuilder andHaving(String columnLeft,String operation,String columnRight)throws NoFirstConditionException {
        if(this.havingInfos.size()==0){
            throw new NoFirstConditionException("Having","andHaving");
        }
        this.havingInfos.add(new ConditionInfo(columnLeft,operation,columnRight,"and"));
        return this;
    }

    public QueryBuilder orHaving(String columnLeft,String operation,String columnRight)throws NoFirstConditionException {
        if(this.havingInfos.size()==0){
            throw new NoFirstConditionException("Having","orHaving");
        }
        this.havingInfos.add(new ConditionInfo(columnLeft,operation,columnRight,"or"));
        return this;
    }

    protected String getScriptWhere()throws Exception{
        StringBuilder request= new StringBuilder();
        for (int i=0;i<this.whereInfos.size();i++) {
            request.append(whereInfos.get(i).getCondition(this));
        }
        return request.toString();
    }

    protected String getScriptHaving()throws Exception{
        StringBuilder request= new StringBuilder();
        for (int i=0;i<this.havingInfos.size();i++) {
            request.append(havingInfos.get(i).getCondition(this));
        }
        return request.toString();
    }

    public QueryBuilder select(String select){
        if(this.select!=""){
            this.select+=", ";
        }
        this.select=select;
        return this;
    }

    public void orderBy(String column,String ascendance){
        this.orderBy="order by "+column+" "+ascendance;
    }

    public String getRequest()throws Exception{
        this.joinAll();
        String request="select "+this.select+" from \""+table+"\"";
        if(alias!=null){
            request+=" as "+alias;
        }
        if(!Objects.equals(this.join, "")){
            request+=" "+this.join;
        }
        if(this.whereInfos.size()!=0){
            request+="\n where "+this.getScriptWhere();
        }
        if(this.havingInfos.size()!=0){
            request+="\n having "+this.getScriptHaving();
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

    protected Relation getVariable(String variable)throws PropertyNotFoundException {
        for (Relation joinInfo:this.joinInfos) {
            if(joinInfo.getName().compareTo(variable)==0 || joinInfo.getName().compareTo("\""+variable+"\"")==0){
                return joinInfo;
            }
        }
        throw new PropertyNotFoundException(variable);
    }

    protected String getJoinWhere(String sentence)throws Exception{
        if(!UtilString.isAVariable(sentence)){
            return sentence;
        }
        String[] field=sentence.split("[.]");
        if(field.length==1){
            return field[0];
        }
        Relation joinInfo=this.getDeepJoin(field,1);
        return joinInfo.getName()+"."+field[field.length-1];
    }

    protected Relation getRelation(String sentence,int limit)throws Exception{
        String[] field=sentence.split("[.]");
        return this.getDeepJoin(field,limit);
    }

    protected Relation getDeepJoin(String[] field,int limit)throws Exception{
        try{
            Relation joinInfo=this.getVariable(field[0]);
            for(int i = 1; i < field.length - limit; i++){
                joinInfo=getRelation(field[i], joinInfo.getClasse(), joinInfo.getName());
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

    @Override
    protected Object clone() throws CloneNotSupportedException {
        try {
            QueryBuilder clone=(QueryBuilder) super.clone();
            clone.whereInfos=new ArrayList<ConditionInfo>();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    protected class ConditionInfo{
        protected String operation;
        protected String columnLeft;
        protected String columnRight;
        protected String logicOperation;
        protected String raw;
        protected ConditionBlock conditionBlock;
        protected boolean isWhere;

        public ConditionInfo(String columnLeft,String operation,String columnRight){
            this.operation=operation;
            this.columnLeft=columnLeft;
            this.columnRight=columnRight;
        }

        public ConditionInfo(String raw){
            this.raw=raw;
        }

        public ConditionInfo(ConditionBlock conditionBlock,boolean isWhere){
            this.conditionBlock=conditionBlock;
            this.isWhere=isWhere;
        }

        public ConditionInfo(ConditionBlock conditionBlock, String logicOperation,boolean isWhere){
            this.conditionBlock=conditionBlock;
            this.logicOperation=logicOperation;
            this.isWhere=isWhere;
        }

        public ConditionInfo(String columnLeft,String operation,String columnRight,String logicOperation){
            this.operation=operation;
            this.columnLeft=columnLeft;
            this.columnRight=columnRight;
            this.logicOperation=logicOperation;
        }

        protected String getScript(QueryBuilder queryBuilder)throws Exception{
            QueryBuilder clone=(QueryBuilder) queryBuilder.clone();
            clone=conditionBlock.block(clone);
            if(this.logicOperation==null){
                if(this.isWhere){
                    return "("+clone.getScriptWhere()+")";
                }
                return "("+clone.getScriptHaving()+")";
            }
            if(this.isWhere){
                return " "+this.logicOperation+" ("+clone.getScriptWhere()+")";
            }
            return " "+this.logicOperation+" ("+clone.getScriptHaving()+")";
        }

        private String getCondition(QueryBuilder queryBuilder)throws Exception{
            if(this.raw!=null){
                return " "+this.raw;
            }
            if(this.conditionBlock!=null){
                this.getScript(queryBuilder);
            }
            this.columnRight=queryBuilder.getJoinWhere(this.columnRight);
            this.columnLeft=queryBuilder.getJoinWhere(this.columnLeft);
            if(this.logicOperation==null){
                return columnLeft+" "+operation+" "+columnRight;
            }
            return " "+logicOperation+" "+columnLeft+" "+operation+" "+columnRight;
        }
    }
}