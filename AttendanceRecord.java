import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class AttendanceRecord {
    private final int memberId;
    private final String memberName;
    private final LocalDateTime checkedInAt;

    public AttendanceRecord(Member member) {
        this.memberId = member.getId();
        this.memberName = member.getName();
        this.checkedInAt = LocalDateTime.now();
    }
    public int getMemberId() { return memberId; }
    public LocalDate getDate() { return checkedInAt.toLocalDate(); }
    @Override
    public String toString() {
        return String.format("Member #%d (%s) | %s", memberId, memberName,
                checkedInAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
