package practice_2;

public class StudentGroup {
    String groupName;
    int studentCount;

    public StudentGroup(String groupName, int defaultStudentCount) {
        this.groupName = groupName;
        if (defaultStudentCount > 0) {
            this.studentCount = defaultStudentCount;
        } else {
            this.studentCount = 0;
        }
    }

    public String getGroupName() { return this.groupName; }
    public int getStudentCount() { return this.studentCount; }

    public void setGroupName(String newGroupName) { this.groupName = newGroupName; }
    public void setStudentCount(int newStudentCount) { this.studentCount = newStudentCount; }

    public void printInfo () {
        System.out.println("Название группы: " + this.getGroupName() + "\nКоличество студентов: " + this.getStudentCount());
    }
}
