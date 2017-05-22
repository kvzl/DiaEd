package diaed.history;

/**
 * Created by ucfan on 2017/4/4.
 */

/**
 * 存放所有要備份的狀態
 * （目前只有狀態圖的狀態）
 */
public class Memento {
    DiagramState state;

    public DiagramState getState() {
        return state;
    }

    public void setState(DiagramState state) {
        this.state = state;
    }
}
