package practice_12.task_1;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class EntityManager<T extends Entity> {
    private final CopyOnWriteArrayList<T> entities;

    public EntityManager() {
        this.entities = new CopyOnWriteArrayList<>();
    }

    public void addEntity(T entity) {
        if (entity == null) throw new IllegalArgumentException("Cant add null");
        if (!this.entities.contains(entity)) {
            this.entities.add(entity);
        }
    }

    public boolean removeEntity(T entity) {
        if (entity == null) throw new IllegalArgumentException("Cant remove null");
        if (this.entities.contains(entity)) {
            return this.entities.remove(entity);
        }
        return false;
    }

    public List<T> getEntities() {
        return List.copyOf(this.entities);
    }

    public List<T> filterByAge(int from, int to) {
        return this.entities
                .stream()
                .filter(e -> e.getAge() >= from && e.getAge() <= to)
                .toList();
    }

    public List<T> filterByName(String name) {
        if (name == null) throw new IllegalArgumentException("Cant search for null");
        return this.entities
                .stream()
                .filter(e -> e.getName().equalsIgnoreCase(name))
                .toList();
    }

    public List<T> filterByActive(boolean isActive) {
        return this.entities
                .stream()
                .filter(e -> e.isActive() == isActive)
                .toList();
    }
}
