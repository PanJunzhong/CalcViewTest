package view;

import javax.swing.*;

/**
 * 这个是历史记录工具的窗口,
 * 就是那个程序顶部菜单栏的一个弹出窗口.
 * 暂时不用考虑这里的编写.
 */
public class HistoryToolFrame extends JFrame {
    public static final int minWidth = 400;
    public static final int minHeight = 600;
    public HistoryToolFrame() {
        super();
        initializeComponents();
    }
    public HistoryToolFrame(int x,int y){
        super();
        initializeComponents(x,y);
    }
    private void initializeComponents(){
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
    private void initializeComponents(int x, int y){
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLocation(x, y);
        setSize(minWidth,minHeight);
        JButton test = new JButton("test");
        this.add(test);
        this.setVisible(true);
    }
}
