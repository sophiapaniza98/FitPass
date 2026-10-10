import java.time.LocalDate;

class Member extends Person implements CheckInEligible {
    private MembershipPlan plan;
    private LocalDate expirationDate;

    public Member(int id, String name, int age, MembershipPlan plan) {
        super(id, name, age);
        assignPlan(plan);
    }
    public MembershipPlan getPlan() { return plan; }
    public LocalDate getExpirationDate() { return expirationDate; }
    public void assignPlan(MembershipPlan plan) {
        if (plan == null) throw new IllegalArgumentException("Plan is required.");
        this.plan = plan;
        LocalDate start = expirationDate != null && !expirationDate.isBefore(LocalDate.now())
                ? expirationDate : LocalDate.now();
        this.expirationDate = start.plusDays(plan.getDurationDays());
    }
    @Override
    public boolean canCheckIn() {
        return !LocalDate.now().isAfter(expirationDate);
    }
    @Override
    public void displayInfo() {
        System.out.printf("[MEMBER] ID: %d | %s | Age: %d | Plan: %s | Expires: %s | %s%n",
                getId(), getName(), getAge(), plan.getName(), expirationDate,
                canCheckIn() ? "ACTIVE" : "EXPIRED");
    }
}
