package q002;

public class Person {
    private int id;
    private String name;

    Person(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    public String toString(){
        return String.format("%d,%s", id, name);
    }
}
