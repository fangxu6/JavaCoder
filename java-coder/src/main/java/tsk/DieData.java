package tsk;

public class DieData {
    private DieCategory attribute;
    private int bin;
    private int site;
    private int x;
    private int y;

    public DieCategory getAttribute() {
        return attribute;
    }

    public void setAttribute(DieCategory attribute) {
        this.attribute = attribute;
    }

    public int getBin() {
        return bin;
    }

    public void setBin(int bin) {
        this.bin = bin;
    }

    public int getSite() {
        return site;
    }

    public void setSite(int site) {
        this.site = site;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
