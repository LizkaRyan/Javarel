package mg.itu.tools;

import mg.itu.relation.Relation;
import mg.itu.request.Entity;

import java.lang.reflect.Method;

public final class ReflectionTools {
    public static String getEntityName(Class<? extends Entity> classe){
        String[] classeName=classe.getName().toLowerCase().split("[.]");
        return classeName[classeName.length-1];
    }

    public static Relation getRelationEntity(Class<? extends Entity> classe, String link)throws Exception{
        Object object=null;
        try{
            object=classe.getConstructor().newInstance();
            Method method=classe.getMethod(link);
            return (Relation)method.invoke(object);
        }
        catch (NoSuchMethodException ex){
            throw new NoSuchMethodException("The entity "+getEntityName(classe)+" should have a constructor and a function "+link+" with no parameters");
        }
        catch (InstantiationException ex){
            throw ex;
        }
    }

    public static String getIdByDefault(Class<? extends Entity> classe){
        String entityName=getEntityName(classe);
        return "id"+entityName.substring(0,1).toUpperCase()+entityName.substring(1);
    }
}
