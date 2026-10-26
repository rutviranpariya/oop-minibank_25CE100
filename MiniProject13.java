import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class MiniProject13 {
    @Retention(RetentionPolicy.RUNTIME) @Target(ElementType.FIELD) @interface Id {}
    static class Account {
        @Id private String accountNumber; private String owner; private long balance;
        Account(String n,String o,long b){accountNumber=n;owner=o;balance=b;}
        @Override public String toString(){return accountNumber+" | "+owner+" | Rs."+balance;}
    }
    static class Repository<T> {
        private final HashMap<String,T> data=new HashMap<>();
        String idOf(T entity){try{for(Field f:entity.getClass().getDeclaredFields())if(f.isAnnotationPresent(Id.class)){f.setAccessible(true);return String.valueOf(f.get(entity));}throw new IllegalArgumentException("No @Id field");}catch(IllegalAccessException e){throw new RuntimeException(e);}}
        T save(T entity){data.put(idOf(entity),entity);return entity;}
        @SafeVarargs final void saveAll(T... entities){for(T e:entities)save(e);}
        T findById(String id){return data.get(id);}
        ArrayList<T> findAll(){return new ArrayList<>(data.values());}
        boolean delete(String id){return data.remove(id)!=null;}
        int size(){return data.size();}
        static <N extends Number> double average(N[] values){if(values.length==0)return 0;double sum=0;for(N n:values)sum+=n.doubleValue();return sum/values.length;}
    }
    public static void main(String[] args) {
        Repository<Account> repo=new Repository<>();repo.saveAll(new Account("AC01","Riya",5000),new Account("AC02","Amit",2500));
        System.out.println("Find: "+repo.findById("AC01"));for(Account a:repo.findAll())System.out.println(a);
        System.out.println("Count: "+repo.size());System.out.println("Deleted AC02: "+repo.delete("AC02"));
        System.out.println("Average: "+Repository.average(new Integer[]{10,20,30}));
    }
}
