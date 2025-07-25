package tsk;

import java.util.ArrayList;
import java.util.List;

public class DieMatrix {
    private List<DieData> dieList;
    private int rows;
    private int cols;

    public DieMatrix(ArrayList<DieData> dieList, int rows, int cols) {
        this.dieList = dieList;
        this.rows = rows;
        this.cols = cols;
    }

    public DieData getDie(int index) {
        return dieList.get(index);
    }

    public DieData getDie(int x, int y) {
        // Assuming row-major order
        return dieList.get(y * cols + x);
    }

    public int getXMax() {
        return cols;
    }

    public int getYMax() {
        return rows;
    }
}
