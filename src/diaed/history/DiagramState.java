package diaed.history;

import diaed.model.DiagramElement;

import java.util.ArrayList;

/**
 * Created by ucfan on 2017/4/3.
 */

/**
 * 用來儲存狀態圖的狀態
 *
 * 是以 snapshot 的方式儲存，也就是完整複製
 * （我也知道不太好，以後再改）
 *
 * 其實就是 ArrayList<DiagramElement> 的 type alias
 */

public class DiagramState extends ArrayList<DiagramElement> {
}
