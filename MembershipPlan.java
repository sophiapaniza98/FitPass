class MembershipPlan {
    private final String name;
    private final int durationDays;
    private final double price;

    public MembershipPlan(String name, int durationDays, double price) {
        this.name = name;
        this.durationDays = durationDays;
        this.price = price;
    }
    public String getName() { return name; }
    public int getDurationDays() { return durationDays; }
    public double getPrice() { return price; }
    @Override
    public String toString() {
        return String.format("%s (%d days, PHP %.2f)", name, durationDays, price);
    }
}
