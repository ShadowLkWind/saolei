package Interface;

import javax.swing.*;
import java.net.URL;

/*
这个抽象类描述了方块对象中主要的属性和方法。
 */
public abstract class GameSquare extends JButton {

//    方格的x坐标
    protected int xLocation;
//    方格的y坐标
    protected int yLocation;
//    方块所在的游戏窗口
    protected GameBoard board;

//     创建一个会被放在游戏窗口的方块对象。
//	   x 方块相对于游戏窗口的 x 坐标。
//	   y 方块相对于游戏窗口的 y 坐标。
//	   filename 图片文件所在位置。
//	   board 游戏窗口。
    public GameSquare(int x, int y, URL filename, GameBoard board){
        super(new ImageIcon(filename));
        this.board = board;
        xLocation = x;
        yLocation = y;
    }
//    根据所给的文件地址更改当前方块渲染的图像。
//    filename 需要更新的图片的地址
    public void setImage(URL filename){
        this.setIcon(new ImageIcon(filename));
    }
//    点击时调用方法
    public abstract void clicked();

}
