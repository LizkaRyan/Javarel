package mg.itu.tools;

import mg.itu.relation.Relation;
import mg.itu.request.Entity;

import java.lang.reflect.Method;

public final class ReflectionTools {
    public static String getEntityName(Class<? extends Entity> classe){
        String[] classeName=classe.getName().toLowerCase().split("[.]");
        return classeName[classeName.length-1];
    }

    public static Relation getRelationEntity(Class<? extends Entity> classe, String liaison)throws Exception{
        Object object=null;
        try{
            object=classe.getConstructor().newInstance();
            Method method=classe.getMethod(liaison);
            return (Relation)method.invoke(object);
        }
        catch (NoSuchMethodException ex){
            throw new NoSuchMethodException("The entity "+getEntityName(classe)+" should have a constructor and a function "+liaison+" with no parameters");
        }
        catch (InstantiationException ex){
            throw ex;
        }
    }
}
