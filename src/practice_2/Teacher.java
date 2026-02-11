package practice_2;

public class Teacher {
    String name, subject;

    public Teacher (String name, String subject) {
        this.name = name;
        this.subject = subject;
    }

    public void setName (String name) {
        this.name = name;
    }

    public void setSubject (String subject) {
        this.subject = subject;
    }

    public String getName() {return this.name;}
    public String getSubject() {return this.subject;}

    public String getInfo () {
        return "Имя: " + this.name + ", Предмет: " + this.subject;
    }


}
