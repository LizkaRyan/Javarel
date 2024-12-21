package mg.itu.tools;

public final class ReflectionTools {
    public static String getEntityName(Class<?> classe){
        String[] classeName=classe.getName().toLowerCase().split("[.]");
        return classeName[classeName.length-1];
    }
}
