package tsk;

/**
 * className: DieData
 * package: tsk
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2025/7/24 20:40
 */
class DieData {
    private DieCategory attribute = DieCategory.Unknow;
    private int bin = -1;
    private int x = 0;
    private int y = 0;
    private int site = -1;

    @Override
    public DieData clone() {
        DieData data = new DieData();
        data.attribute = this.attribute;
        data.bin = this.bin;
        data.x = this.x;
        data.y = this.y;
        data.site = this.site;
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DieData dieData = (DieData) o;
        return bin == dieData.bin &&
                x == dieData.x &&
                y == dieData.y &&
                attribute == dieData.attribute;
    }

    @Override
    public int hashCode() {
        return attribute.hashCode();
    }

    public static DieData add(DieData item1, DieData item2) {
        DieData data = new DieData();
        if (item1.attribute == DieCategory.PassDie && item2.attribute == DieCategory.PassDie) {
            data.attribute = DieCategory.PassDie;
            return data;
        }
        if (item1.attribute == DieCategory.MarkDie || item2.attribute == DieCategory.MarkDie) {
            data.attribute = DieCategory.MarkDie;
            return data;
        }
        if (item1.attribute == DieCategory.NoneDie || item2.attribute == DieCategory.NoneDie) {
            data.attribute = DieCategory.NoneDie;
            return data;
        }
        if (item1.attribute == DieCategory.FailDie || item2.attribute == DieCategory.FailDie) {
            data.attribute = DieCategory.FailDie;
            return data;
        }
        if (item1.attribute == DieCategory.Unknow || item2.attribute == DieCategory.Unknow) {
            data.attribute = DieCategory.Unknow;
            return data;
        }
        if (item1.attribute == DieCategory.SkipDie || item2.attribute == DieCategory.SkipDie) {
            data.attribute = DieCategory.SkipDie;
            return data;
        }
        if (item1.attribute == DieCategory.SkipDie2 || item2.attribute == DieCategory.SkipDie2) {
            data.attribute = DieCategory.SkipDie2;
            return data;
        }
        data.attribute = DieCategory.Unknow;
        return data;
    }

    // Getters and Setters
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

    public int getSite() {
        return site;
    }

    public void setSite(int site) {
        this.site = site;
    }
}
