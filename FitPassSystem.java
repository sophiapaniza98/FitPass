import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class FitPassSystem {
    private final Scanner scanner = new Scanner(System.in);
    private final List<Member> members = new ArrayList<>();
    private final List<AttendanceRecord> attendance = new ArrayList<>();
    private final List<MembershipPlan> plans = new ArrayList<>();
    private final List<Trainer> trainers = new ArrayList<>();
    private int nextMemberId = 1001;

    public FitPassSystem() {
        plans.add(new MembershipPlan("Daily", 1, 100));
        plans.add(new MembershipPlan("Monthly", 30, 1000));
        plans.add(new MembershipPlan("Quarterly", 90, 2700));
        trainers.add(new Trainer(501, "Alex Cruz", 28, "Strength Training"));
        trainers.add(new Trainer(502, "Jamie Reyes", 31, "Cardio"));
    }

    public void run() {
        while (true) {
            System.out.println("\n========== FitPASS ==========");
            System.out.println("1. Register member");
            System.out.println("2. View all members");
            System.out.println("3. Search member");
            System.out.println("4. Update member");
            System.out.println("5. Renew/change membership");
            System.out.println("6. Member check-in");
            System.out.println("7. View attendance history");
            System.out.println("8. Remove member");
            System.out.println("9. View plans and trainers");
            System.out.println("0. Exit");
            int choice = readInt("Choose an option: ", 0, 9);
            switch (choice) {
                case 1: registerMember(); break;
                case 2: viewMembers(); break;
                case 3: searchMember(); break;
                case 4: updateMember(); break;
                case 5: renewMembership(); break;
                case 6: checkIn(); break;
                case 7: viewAttendance(); break;
                case 8: removeMember(); break;
                case 9: viewPlansAndTrainers(); break;
                case 0: System.out.println("Thank you for using FitPASS!"); return;
                default: break;
            }
        }
    }

    private void registerMember() {
        String name = readNonBlank("Full name: ");
        int age = readInt("Age: ", 1, 120);
        MembershipPlan plan = choosePlan();
        Member member = new Member(nextMemberId++, name, age, plan);
        members.add(member);
        System.out.println("Registered successfully! Member ID: " + member.getId());
        member.displayInfo();
    }

    private void viewMembers() {
        if (members.isEmpty()) { System.out.println("No registered members."); return; }
        for (Person person : members) person.displayInfo();
    }

    private void searchMember() {
        String query = readNonBlank("Enter member ID or part of name: ");
        boolean found = false;
        for (Member member : members) {
            if (String.valueOf(member.getId()).equals(query)
                    || member.getName().toLowerCase().contains(query.toLowerCase())) {
                member.displayInfo();
                found = true;
            }
        }
        if (!found) System.out.println("No matching member found.");
    }

    private void updateMember() {
        Member member = requestMember();
        if (member == null) return;
        String name = readNonBlank("New full name: ");
        int age = readInt("New age: ", 1, 120);
        member.setName(name);
        member.setAge(age);
        System.out.println("Member updated.");
        member.displayInfo();
    }

    private void renewMembership() {
        Member member = requestMember();
        if (member == null) return;
        member.assignPlan(choosePlan());
        System.out.println("Membership updated. New expiration: " + member.getExpirationDate());
    }

    private void checkIn() {
        Member member = requestMember();
        if (member == null) return;
        if (!member.canCheckIn()) {
            System.out.println("Check-in denied: membership expired. Please renew.");
            return;
        }
        for (AttendanceRecord record : attendance) {
            if (record.getMemberId() == member.getId() && record.getDate().equals(LocalDate.now())) {
                System.out.println("Already checked in today.");
                return;
            }
        }
        AttendanceRecord record = new AttendanceRecord(member);
        attendance.add(record);
        System.out.println("Check-in successful: " + record);
    }

    private void viewAttendance() {
        if (attendance.isEmpty()) { System.out.println("No attendance records yet."); return; }
        System.out.println("1. All records  2. By member ID");
        int choice = readInt("Choose: ", 1, 2);
        int id = choice == 2 ? readInt("Member ID: ", 1, Integer.MAX_VALUE) : -1;
        boolean found = false;
        for (AttendanceRecord record : attendance) {
            if (choice == 1 || record.getMemberId() == id) {
                System.out.println(record);
                found = true;
            }
        }
        if (!found) System.out.println("No matching attendance records.");
    }

    private void removeMember() {
        Member member = requestMember();
        if (member == null) return;
        String confirmation = readNonBlank("Type YES to remove this member: ");
        if (confirmation.equalsIgnoreCase("YES")) {
            members.remove(member);
            System.out.println("Member removed. Historical attendance records are retained.");
        } else {
            System.out.println("Removal cancelled.");
        }
    }

    private void viewPlansAndTrainers() {
        System.out.println("Membership plans:");
        for (MembershipPlan plan : plans) System.out.println("- " + plan);
        System.out.println("Trainers:");
        List<Person> people = new ArrayList<>();
        people.addAll(trainers);
        people.addAll(members);
        for (Person person : people) person.displayInfo();
    }

    private MembershipPlan choosePlan() {
        System.out.println("Available plans:");
        for (int i = 0; i < plans.size(); i++) {
            System.out.println((i + 1) + ". " + plans.get(i));
        }
        int choice = readInt("Choose plan: ", 1, plans.size());
        return plans.get(choice - 1);
    }

    private Member requestMember() {
        int id = readInt("Member ID: ", 1, Integer.MAX_VALUE);
        for (Member member : members) {
            if (member.getId() == id) return member;
        }
        System.out.println("Member not found.");
        return null;
    }

    private String readNonBlank(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("Input cannot be empty.");
        }
    }

    private int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) return value;
            } catch (NumberFormatException ignored) {
            }
            System.out.printf("Enter a whole number between %d and %d.%n", min, max);
        }
    }
}
