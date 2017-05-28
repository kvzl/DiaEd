package diaed.builder;

import diaed.model.StateDiagram;

/**
 * Created by Administrator on 2017/5/26.
 */
public interface DiagramTemplate {
    void construct();
    StateDiagram getDiagram();
    String getName();
}
