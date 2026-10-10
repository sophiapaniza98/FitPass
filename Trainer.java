class Trainer extends Person {
    private final String specialty;

    public Trainer(int id, String name, int age, String specialty) {
        super(id, name, age);
        this.specialty = specialty;
    }
    @Override
    public void displayInfo() {
        System.out.printf("[TRAINER] ID: %d | %s | Age: %d | Specialty: %s%n",
                getId(), getName(), getAge(), specialty);
    }
}
