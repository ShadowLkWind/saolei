package Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*
这个类游戏窗口提供图形模型
创建了可点击的矩形面板
如果玩家点击了小方块，会在响应的 SmartSquare

 */
public class GameBoard extends JFrame implements ActionListener {
     private JPanel boardPanel = new JPanel();
     private int boardHeight;
     private int boardWidth;
     private GameSquare[][] board;
//     创建给定大小的游戏窗口
//     title  标题
//     width  以方块作单位的窗口的宽
//     height 以方块作单位的窗口的高
    public GameBoard(String title, int width, int height) {
        this.boardWidth = width;
        this.boardHeight = height;

//        创建游戏初始方块
        this.board = new GameSquare[width][height];
//        新建窗口
        setTitle(title);
        setSize(20 + width * 20, 20 + height * 20);
        setContentPane(boardPanel);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        boardPanel.setLayout(new GridLayout(height, width));
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                board[x][y] = new SmartSquare(x, y, this);
                board[x][y].addActionListener(this);

                boardPanel.add(board[x][y]);
            }
        }
//        窗口可视化
        setVisible(true);
    }

//    返回给定位置的方块
//    x 给定方块的 x 的坐标
//    y 给定方块的 y 的坐标
//    如果 x 和 y 的位置都在边界范围内，则给出响应的方块对象，否则返回 null.
    public GameSquare getSquareAt(int x, int y) {
        if (x < 0 || x >= boardWidth || y < 0 || y >= boardHeight){
            return null;
        }
        return board[x][y];
    }

    // 点击事件
    public void actionPerformed(ActionEvent e) {
        GameSquare b = (GameSquare) e.getSource();
        b.clicked();
    }
}
