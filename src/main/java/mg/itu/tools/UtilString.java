package mg.itu.tools;

public final class UtilString {
    public static String makePascalCase(String str){
        return str.substring(0,1).toUpperCase()+str.substring(1);
    }
}