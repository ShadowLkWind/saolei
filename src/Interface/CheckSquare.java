package Interface;
//这个类用于计算指定方块周围的炸弹总数
public class CheckSquare {
//    游戏窗口实例
    private GameBoard board;
//    实例的高
    private int boardHeight;
//    实例的宽
    private int boardWidth;

    private static final int[] distantX = {-1, 0, 1};
    private static final int[] distantY = {-1, 0, 1};
//    在游戏窗口中创建该类的实例
//    board 玩家点击的游戏窗口
    public CheckSquare(GameBoard board) {
        this.board = board;
//        长宽减去边距
        boardHeight = (board.getHeight() - 20) / 20;
        boardWidth = (board.getWidth() - 20) / 20;
    }
//    返回指定位置方块的检查结果
//    x 指定方块的x坐标
//    y 指定方块的y坐标
    private boolean hasKickedBoundary(int x, int y) {
        return x < 0 || x >= boardWidth || y < 0 || y >= boardHeight;
    }
//    返回是否通关
    protected boolean isSuccess() {
        int count = 0;
        for (int y = 0; y < boardHeight; y++) {
            for (int x = 0; x < boardWidth; x++) {
                if (((SmartSquare) board.getSquareAt(x, y)).getTraverse())
                    count++;
            }
        }
        return count == boardHeight * boardWidth;
    }
//    显示所有炸弹的位置，检验玩家猜测是否正确
    protected void showBomb(int currentX, int currentY) {
        for (int y = 0; y < boardHeight; y++) {
            for (int x = 0; x < boardWidth; x++) {
                if (currentX == x && currentY == y){}
                else if (((SmartSquare) board.getSquareAt(x, y)).getBombExist())
                    board.getSquareAt(x, y).setImage(CheckSquare.class.getResource("/bomb.png"));
                else if(((SmartSquare) board.getSquareAt(x, y)).getGuessThisSquareIsBomb())
                    board.getSquareAt(x, y).setImage(CheckSquare.class.getResource("/flagWrong.png")); // Wrong guess!
            }
        }
    }
//    这方法计算指定方块周围炸弹的总数。
//    如果格子周围没有炸弹，把这个格子画成空白，
//    然后扩大检测范围，直到发现周围的格子有炸弹
//    currentX 该方块的x坐标
//    currentY 该方块的y坐标
    protected void countBomb(int currentX, int currentY){
        int count = 0;
        SmartSquare currentObject;
        if (hasKickedBoundary(currentX, currentY))
            return;
        else if(((SmartSquare)board.getSquareAt(currentX, currentY)).getTraverse())
            return;
        else{
//            声明 SmartSquare 实例。
            SmartSquare squareObject;
//            获取当前方块对象
            currentObject = (SmartSquare)board.getSquareAt(currentX, currentY);
            currentObject.setTraverse(true);
//            检测周围 8 个方块
//            如果指定的方块位置超出游戏窗口边界，跳出本次循环
//            如果指定的方块位置是它自己，跳出本次循环
//            检查这个方块周围是否含有炸弹，并累计

            for (int x : distantX)
            {
                for (int y: distantY)
                {
                    if (hasKickedBoundary(currentX + x, currentY + y)){}
                    else if (x == 0 && y == 0){}
                    else{
                        squareObject = (SmartSquare)board.getSquareAt(currentX + x, currentY + y);
                        count = squareObject.getBombExist() ? count + 1 : count;
                    }
                }
            }
        }
//        如果循环后计数器仍为0，用该方块周围的方块们作为中心继续探测。
        if (count != 0){
            currentObject.setImage(CheckSquare.class.getResource( "/" + count + ".png"));
        } else {
            // 将当前方块填成空。
            currentObject.setImage(CheckSquare.class.getResource("/0.png"));
            countBomb(currentX - 1, currentY -1); //左上
            countBomb(currentX, currentY -1); // 上
            countBomb(currentX + 1, currentY -1); // 右上
            countBomb(currentX - 1, currentY); // 左
            countBomb(currentX + 1, currentY); // 右
            countBomb(currentX - 1, currentY + 1); // 左下
            countBomb(currentX, currentY + 1); // 下
            countBomb(currentX + 1, currentY + 1); // 右下
        }
    }
}
