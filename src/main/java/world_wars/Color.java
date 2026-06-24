package world_wars;

public enum Color {
    PURPLE(Icon.PURPLE),
    ORANGE(Icon.ORANGE),
    YELLOW(Icon.YELLOW),
    GREEN(Icon.GREEN),
    BROWN(Icon.BROWN),
    WHITE(Icon.WHITE),
    BLACK(Icon.BLACK),
    BLUE(Icon.BLUE),
    RED(Icon.RED);

    private Icon icon;
    Color(Icon icon) { this.icon = icon; }
    @Override
    public String toString() { return this.icon.toString(); }
}
