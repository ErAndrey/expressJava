package practice_12.task_1;

import java.util.Objects;

public abstract class Entity {
    private final String name;
    private final int age;
    private final boolean isActive;

    public Entity(String name, int age, boolean isActive) {
        this.name = name;
        this.age = age;
        this.isActive = isActive;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    @Override
    public String toString() {
        return "\"" + this.name + ", " + this.age + ", " + this.isActive + "\"";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity entity)) return false;
        return Objects.equals(this.name, entity.name) && this.age == entity.getAge() && this.isActive == entity.getIsActive();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.age, this.isActive);
    }
}
