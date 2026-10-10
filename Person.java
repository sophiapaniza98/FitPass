abstract class Person {
    private final int id;
    private String name;
    private int age;

    public Person(int id, String name, int age) {
        this.id = id;
        setName(name);
        setAge(age);
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be blank.");
        this.name = name.trim();
    }
    public void setAge(int age) {
        if (age < 1 || age > 120) throw new IllegalArgumentException("Age must be between 1 and 120.");
        this.age = age;
    }
    public abstract void displayInfo();
}
