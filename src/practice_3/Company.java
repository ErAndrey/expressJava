package practice_3;

public class Company {
    static String companyName;
    final int employeeId;
    String employeeName;

    Company(int employeeId, String employeeName) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
    }

    public String getEmployeeName() {
        return this.employeeName;
    }

    public void setEmployeeName(String newName) {
        this.employeeName = newName;
    }

    public void printCompanyName() {
        System.out.println(companyName);
    }
}
