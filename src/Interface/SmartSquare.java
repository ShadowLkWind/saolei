package Interface;

import library.TimeChecker;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/*
每一个方块有自己独一无二的二维坐标和属性值
一旦玩家鼠标左击点击该类的实例，会显示出该方块周围存在几个炸弹
提供一个弹出窗口无论玩家挑战成功或失败
 */
public class SmartSquare extends GameSquare implements MouseListener, TimeChecker{
//    方块里炸弹存在与否
    private boolean thisSquareHasBomb;
//    方块上旗子设置与否
    private boolean guessThisSquareIsBomb;
//    是否被遍历过
    private boolean thisSquareHasTraversed;
//    方块的x坐标
    private int xLocation;
//    方块的y坐标
    private int yLocation;
//    记录时间
    private long startTime;
//    创建该类的新实例并放到游戏窗口上去。
//	  x 该方块相对于游戏窗口的 x 的坐标。
//	  y 该方块相对于游戏窗口的 y 的坐标。
//	  board 该方块所在的游戏窗口。
    public SmartSquare(int x, int y, GameBoard board) {
        // 初始化时将方块变成灰色。
        super(x, y, SmartSquare.class.getResource("/block.png"), board);

        // 分配坐标
        xLocation = x;
        yLocation = y;

        // 初始化属性
        thisSquareHasBomb = false;
        thisSquareHasTraversed = false;
        guessThisSquareIsBomb = false;
        startTime = 0;

        // 添加鼠标右键监听器
        addMouseListener(this);
    }
//    为炸弹是否存在于该方块设定值
    protected void setBombExist(boolean result) {
        thisSquareHasBomb = result;
    }
//    获取炸弹是否存在于该方块的结果。
    protected boolean getBombExist() {
        return thisSquareHasBomb;
    }
//    根据给定值设置该方块当前的状态。
    protected void setTraverse(boolean result) {
        thisSquareHasTraversed = result;
    }
//    返回该方块是否遍历过的状态
    protected boolean getTraverse() {
        return thisSquareHasTraversed;
    }
//    记录游戏开始的时间戳
    protected void setStartTime(long time) {
        startTime = time;
    }
//    返回游戏刚开始的时间
    protected long getStartTime() {
        return startTime;
    }
//    返回该方块是否插上小红旗的查询结果
    protected boolean getGuessThisSquareIsBomb() {
        return guessThisSquareIsBomb;
    }

//    获取点击事件，检查炸弹扩大空白的面积
    public void clicked(){
        CheckSquare cq = new CheckSquare(board);
        guessThisSquareIsBomb = false;
//        如果踩雷，显示炸弹和弹出失败窗口
        if(thisSquareHasBomb) {
            setImage(SmartSquare.class.getResource("/bombReveal.png"));
            long costTime = System.currentTimeMillis() - ((SmartSquare) board.getSquareAt(0, 0)).getStartTime();
            cq.showBomb(xLocation, yLocation);
            window("你居然花了 " + TimeChecker.calculateTime(costTime) +"就死了，要再试试吗?", "就  这",
                    new ImageIcon(SmartSquare.class.getResource("/failFace.png")));

        }else{
            thisSquareHasTraversed = false;
//            如果该方块不包含炸弹，计算它周围8个格子里炸弹的总数。
//            周围也没有炸弹，扩大空白区域直到检测到炸弹或者越界
            cq.countBomb(xLocation, yLocation);
            if (cq.isSuccess()) {
                long costTime = System.currentTimeMillis() - ((SmartSquare) board.getSquareAt(0, 0)).getStartTime();
                cq.showBomb(xLocation, yLocation);
                window("你赢了，只花了" + TimeChecker.calculateTime(costTime) +
                                "! 要再试试吗，建议玩玩无敌版","行  吧",
                        new ImageIcon(SmartSquare.class.getResource("/passFace.jpg")));
            }
        }
    }

//    实现弹出窗口方法
//    msg 要显示在窗口上的信息。
//	  title 窗口的标题。
//	  img 窗口的图标。
    private void window(String msg, String title, Icon img) {
        int choose = JOptionPane.showConfirmDialog(board, msg, title,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,img);

        if (choose == JOptionPane.YES_OPTION)
        {
            new GameMenu("扫雷");
        }

        // 关闭弹出窗口并返回游戏菜单
        board.dispose();
    }
//    右键点击事件
//    e 玩家点击方块的事件
    public void mouseClicked(MouseEvent e) {
        // 如果右键点击了
        if (e.getButton() == MouseEvent.BUTTON3)
        {
            int clickCount = e.getClickCount();

            // 显示红旗
            if (clickCount == 1)
            {
                setImage(SmartSquare.class.getResource("/redFlag.png"));
                guessThisSquareIsBomb = true;
            }

            // 显示问号
            if (clickCount == 2)
            {
                setImage(SmartSquare.class.getResource("/questionMark.png"));
                guessThisSquareIsBomb = false;
            }
        }
    }
//    下列鼠标事件不处理。
    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}
}
