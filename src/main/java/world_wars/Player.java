package world_wars;

import world_wars.diplomacy.RelationType;

import java.util.HashMap;
import java.util.Map;

public class Player {
    private final String name;
    private Color color;
    private Map<Player, RelationType> relations;

    public Player(String name) {
        this.name = name;
        this.relations = new HashMap<>();
    }

    public String getName() { return this.name; }
    public void setColor(Color color) { this.color = color; }

    @Override
    public String toString() { return this.color.toString(); }
}
