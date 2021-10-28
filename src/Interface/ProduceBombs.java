package Interface;

import library.Bomb;

import java.util.Random;

/*
该类用于在游戏窗口生成炸弹
 */
public class ProduceBombs extends Bomb {
//    在给定游戏窗口创建该类的实例
//    board 用户点击的游戏窗口
//    number 炸弹数量
    public ProduceBombs(GameBoard board, int number) {

        super(board);

        int count = 0;

        do {
            reproduceBomb();
            count++;
        } while (count < number);
    }

//    该类用于在游戏窗口随机生成炸弹的位置。如果已有，则重新生成
    protected void reproduceBomb() {
        Random r = new Random();

        int xLocation = r.nextInt(boardWidth);
        int yLocation = r.nextInt(boardHeight);
        SmartSquare square = (SmartSquare) board.getSquareAt(xLocation, yLocation);

        if (!square.getBombExist()) {
//            标记该方块含有炸弹并被遍历了
            square.setBombExist(true);
            square.setTraverse(true);
        } else {
            reproduceBomb();
        }

    }
}
