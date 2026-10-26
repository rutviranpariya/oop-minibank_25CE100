import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class MiniProject7 {
    @Retention(RetentionPolicy.RUNTIME) @Target(ElementType.FIELD) @interface Id {}
    @Retention(RetentionPolicy.RUNTIME) @Target(ElementType.FIELD) @interface Positive { String message() default "must be > 0"; }
    @Retention(RetentionPolicy.RUNTIME) @Target(ElementType.FIELD) @interface MaxLength { int value(); }
    static class Account {
        @Id private String accountNumber;
        @MaxLength(30) private String ownerName;
        @Positive private long balance;
        Account(String n,String o,long b){accountNumber=n;ownerName=o;balance=b;}
    }
    static class AnnotationValidator {
        static String[] validate(Object obj) {
            ArrayList<String> errors=new ArrayList<>();
            for(Field f:obj.getClass().getDeclaredFields())try{
                f.setAccessible(true); Object v=f.get(obj);
                if(f.isAnnotationPresent(Positive.class) && v instanceof Number n && n.doubleValue()<=0)errors.add(f.getName()+" "+f.getAnnotation(Positive.class).message());
                if(f.isAnnotationPresent(MaxLength.class) && v instanceof String s && s.length()>f.getAnnotation(MaxLength.class).value())errors.add(f.getName()+" is too long");
            }catch(IllegalAccessException e){errors.add("Cannot read "+f.getName());}
            return errors.toArray(new String[0]);
        }
    }
    public static void main(String[] args) {
        Account a=new Account("AC0001","A very long customer name that exceeds thirty",-100);
        String[] errors=AnnotationValidator.validate(a);
        if(errors.length==0)System.out.println("Account is valid."); else for(String e:errors)System.out.println("Validation error: "+e);
    }
}
