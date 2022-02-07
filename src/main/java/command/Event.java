package command;

public enum Event {
    GOT_DIAMOND(0),
    GOT_STAR(1),
    HIT_OBSTACLE(2),
    ARCHIVE(3);

    private int value;

    private Event(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
