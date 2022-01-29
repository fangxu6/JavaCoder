package singletonlock;

public class Demo {
    public static void main(String[] args) {
        IdGenerator5 idGen = IdGenerator5.INSTANCE;
        long id = idGen.getId();
        System.out.println(id);
        id = idGen.getId();
        System.out.println(id);
    }
}
