package tsk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * className: DieMatrix
 * package: tsk
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2025/7/24 20:41
 */
class DieMatrix {
    private int xmax; // X方向最大值(列数)
    private int ymax; // Y方向最大值(行数)
    private List<DieData> items;

    public int getXMax() {
        return xmax;
    }

    public int getYMax() {
        return ymax;
    }

    public int getCount() {
        return items.size();
    }

    public Collection<DieData> getItems() {
        return items;
    }

    public DieData get(int index) {
        return items.get(index);
    }

    public DieData get(int x, int y) {
        if (x >= xmax) {
            throw new IndexOutOfBoundsException("X坐标超出范围");
        }
        if (y >= ymax) {
            throw new IndexOutOfBoundsException("Y坐标超出范围");
        }
        return items.get(y * xmax + x);
    }

    public void set(int x, int y, DieData value) {
        if (x >= xmax) {
            throw new IndexOutOfBoundsException("X坐标超出范围");
        }
        if (y >= ymax) {
            throw new IndexOutOfBoundsException("Y坐标超出范围");
        }
        items.set(y * xmax + x, value);
    }

    private DieMatrix() {
        this.xmax = -1;
        this.ymax = -1;
        this.items = new ArrayList<>();
    }

    public DieMatrix(int xmax, int ymax) {
        this.xmax = xmax;
        this.ymax = ymax;
        this.items = new ArrayList<>();
        int count = xmax * ymax;
        for (int i = 0; i < count; i++) {
            DieData d = new DieData();
            d.setAttribute(DieCategory.NoneDie);
            items.add(d);
        }
    }

    public DieMatrix(List<DieData> dies, int xmax, int ymax) {
        this.xmax = xmax;
        this.ymax = ymax;
        this.items = new ArrayList<>();
        this.items.addAll(dies);
    }

    public void setValue(int x, int y, DieData die) {
        set(x, y, die);
    }

    public void deasilRotate(int degree) {
        switch (degree) {
            case 0:
                break;
            case 90:
                r90();
                break;
            case 270:
            case -90:
                r270();
                break;
            case 180:
                r180();
                break;
            default:
                throw new UnsupportedOperationException("不支持 " + degree + " 度的旋转");
        }
    }

    private void r90() {
        int count = items.size();
        DieData[] dies = new DieData[count];

        for (int i = 0; i < count; i++) {
            int x = i % xmax;
            int y = i / xmax;

            int xr = (ymax - 1) - y;
            int yr = x;

            dies[yr * ymax + xr] = items.get(i);
        }

        int temp = xmax;
        xmax = ymax;
        ymax = temp;

        items.clear();
        for (DieData die : dies) {
            items.add(die);
        }
    }

    private void r270() {
        int count = items.size();
        DieData[] dies = new DieData[count];

        for (int i = 0; i < count; i++) {
            int x = i % xmax;
            int y = i / xmax;

            int xr = y;
            int yr = (xmax - 1) - x;

            dies[yr * ymax + xr] = items.get(i);
        }

        int temp = xmax;
        xmax = ymax;
        ymax = temp;

        items.clear();
        for (DieData die : dies) {
            items.add(die);
        }
    }

    private void r180() {
        int count = items.size();
        DieData[] dies = new DieData[count];

        for (int i = 0; i < items.size(); i++) {
            try {
                int x = i % xmax;
                int y = i / xmax;

                int xr = (xmax) - 1 - x;
                int yr = (ymax) - 1 - y;

                dies[yr * xmax + xr] = items.get(i);
            } catch (Exception e) {
                String msg = e.getMessage();
            }
        }

        items.clear();
        for (DieData die : dies) {
            items.add(die);
        }
    }

    public void offset(OffsetDir dir, int qty) {
        if (dir == OffsetDir.X) {
            offsetX(qty);
        } else if (dir == OffsetDir.Y) {
            offsetY(qty);
        }
    }

    private void offsetX(int qty) {
        if (qty == 0) {
            return;
        }

        if (Math.abs(qty) >= xmax) {
            throw new IllegalArgumentException("X方向偏移的数量必须小于总长度");
        }

        if (qty > 0) {
            for (int i = xmax - 1; i >= qty; i--) {
                for (int j = 0; j < ymax; j++) {
                    get(i, j).setAttribute(get(i - qty, j).getAttribute());
                }
            }

            for (int i = 0; i < qty; i++) {
                for (int j = 0; j < ymax; j++) {
                    get(i, j).setAttribute(DieCategory.NoneDie);
                }
            }
        } else if (qty < 0) {
            for (int i = 0; i < xmax - qty; i++) {
                for (int j = 0; j < ymax; j++) {
                    get(i, j).setAttribute(get(i + qty, j).getAttribute());
                }
            }

            for (int i = qty; i < xmax; i++) {
                for (int j = 0; j < ymax; j++) {
                    get(i, j).setAttribute(DieCategory.NoneDie);
                }
            }
        }
    }

    private void offsetY(int qty) {
        if (qty == 0) {
            return;
        }

        if (Math.abs(qty) >= ymax) {
            throw new IllegalArgumentException("Y方向偏移的数量必须小于总高度");
        }

        if (qty > 0) {
            for (int i = ymax - 1; i >= qty; i--) {
                for (int j = 0; j < xmax; j++) {
                    get(j, i).setAttribute(get(j, i - qty).getAttribute());
                }
            }

            for (int i = 0; i < qty; i++) {
                for (int j = 0; j < xmax; j++) {
                    get(j, i).setAttribute(DieCategory.NoneDie);
                }
            }
        } else if (qty < 0) {
            for (int i = 0; i < ymax - qty; i++) {
                for (int j = 0; j < xmax; j++) {
                    get(j, i).setAttribute(get(j, i + qty).getAttribute());
                }
            }

            for (int i = qty; i < ymax; i++) {
                for (int j = 0; j < xmax; j++) {
                    get(j, i).setAttribute(DieCategory.NoneDie);
                }
            }
        }
    }

    public void expand(ExpandDir dir, int qty) {
        if (qty <= 0) {
            throw new IllegalArgumentException("扩展数量必须大于0");
        }

        if (Math.abs(qty) >= xmax || Math.abs(qty) >= ymax) {
            throw new IllegalArgumentException("扩展数量必须小于矩阵尺寸");
        }

        int x = xmax, xi = 0;
        int y = ymax, yi = 0;

        switch (dir) {
            case Left:
                x += qty;
                break;
            case Right:
                xi = qty;
                x += qty;
                break;
            case Up:
                yi = qty;
                y += qty;
                break;
            case Down:
                y += qty;
                break;
        }

        List<DieData> arr = new ArrayList<>();
        int count = x * y;
        for (int i = 0; i < count; i++) {
            DieData d = new DieData();
            d.setAttribute(DieCategory.NoneDie);
            arr.add(d);
        }

        for (int i = 0; i < ymax; i++) {
            for (int j = 0; j < xmax; j++) {
                arr.set((i + yi) * x + (j + xi), get(j, i).clone());
            }
        }

        this.items = arr;
        this.xmax = x;
        this.ymax = y;
    }

    public void collapse(ExpandDir dir, int qty) {
        if (qty <= 0) {
            throw new IllegalArgumentException("收缩数量必须大于0");
        }

        if (Math.abs(qty) >= xmax || Math.abs(qty) >= ymax) {
            throw new IllegalArgumentException("收缩数量必须小于矩阵尺寸");
        }

        int x = xmax, xi = 0;
        int y = ymax, yi = 0;

        switch (dir) {
            case Left:
                xi = qty;
                x -= qty;
                break;
            case Right:
                x -= qty;
                break;
            case Up:
                yi = qty;
                y -= qty;
                break;
            case Down:
                y -= qty;
                break;
        }

        List<DieData> arr = new ArrayList<>();
        int count = x * y;
        for (int i = 0; i < count; i++) {
            DieData d = new DieData();
            d.setAttribute(DieCategory.NoneDie);
            arr.add(d);
        }

        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                arr.set(i * x + j, get(j + xi, i + yi).clone());
            }
        }

        this.items = arr;
        this.xmax = x;
        this.ymax = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DieMatrix dieMatrix = (DieMatrix) o;
        if (xmax != dieMatrix.xmax || ymax != dieMatrix.ymax) {
            return false;
        }
        return items.equals(dieMatrix.items);
    }

    @Override
    public int hashCode() {
        int result = xmax;
        result = 31 * result + ymax;
        result = 31 * result + items.hashCode();
        return result;
    }
}
