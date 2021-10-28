package library;

import Interface.GameBoard;

public abstract class Bomb{
//    游戏窗口实例
    protected GameBoard board;
//    实例的高
    protected int boardHeight;
//    实例的宽
    protected int boardWidth;
//    创建炸弹
//    board 玩家点击的游戏窗口
    public Bomb(GameBoard board) {
        this.board = board;
        // 加入计算的高和宽去需要减去填充边距的长度
        boardHeight = (board.getHeight() - 20) / 20;
        boardWidth = (board.getWidth() - 20) / 20;
    }
//    该方法将会被用于分布炸弹的位置
    protected abstract void reproduceBomb();
}
