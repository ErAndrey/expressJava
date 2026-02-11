package practice_3;

public class University {
    static String universityName;
    static int studentCount;

    static {
        studentCount = 0;
    }

    final int studentId;
    String studentName;

    public University(String studentName) {
        this.studentId = studentCount;
        this.studentName = studentName;
        studentCount++;
    }

    public static void changeUniversityName(String newName) {
        universityName = newName;
    }

    public String getStudentName() {
        return this.studentName;
    }

    public void printStudentInfo() {
        System.out.println("Имя: " + this.studentName + ", Id студента: " + this.studentId + ", Университет: " + universityName);
    }
}
