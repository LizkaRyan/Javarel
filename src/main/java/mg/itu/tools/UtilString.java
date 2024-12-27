package mg.itu.tools;

public final class UtilString {
    public static String makePascalCase(String str){
        return str.substring(0,1).toUpperCase()+str.substring(1);
    }
    public static boolean isAVariable(String sentence){
        if(sentence.charAt(0) == ':' || sentence.charAt(0) == '\''){
            return false;
        }
        try{
            Integer.parseInt(sentence);
            return false;
        }
        catch (Exception exception){
            return true;
        }
    }
}