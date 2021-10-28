package Interface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

/*
游戏的开始菜单
 */
public class GameMenu extends JFrame implements ActionListener {
    private JButton start;
    private JRadioButton beginner, intermediate, advanced, custom;
    private JTextField width, height, mines;

    //    创建菜单
    public GameMenu(String title) {
//        创建标题
        setTitle(title);
//        创建副标题
        JLabel subtitle = new JLabel("扫雷?");
        subtitle.setBounds(125, 10, 100, 20);
        add(subtitle);
//        选项1
        beginner = new JRadioButton("菜鸡版");
        beginner.setBounds(40, 40, 150, 20);
        add(beginner);
//        描述
        JLabel bDescFirstLine = new JLabel("1个地雷");
        bDescFirstLine.setBounds(70, 60, 100, 20);
        JLabel bDescSecondLine = new JLabel("30x30格子");
        bDescSecondLine.setBounds(70, 80, 100, 20);
        add(bDescFirstLine);
        add(bDescSecondLine);
//        选项2
        intermediate = new JRadioButton("普通版");
        intermediate.setBounds(40, 100, 150, 20);
        add(intermediate);
//        描述
        JLabel iDescFirstLine = new JLabel("20个地雷");
        iDescFirstLine.setBounds(70, 120, 100, 20);
        JLabel iDescSecondLine = new JLabel("20x20格子");
        iDescSecondLine.setBounds(70, 140, 100, 20);
        add(iDescFirstLine);
        add(iDescSecondLine);
//        选项3
        advanced = new JRadioButton("无敌版");
        advanced.setBounds(40, 160, 160, 20);
        add(advanced);
//        描述
        JLabel aDescFirstLine = new JLabel("???个地雷");
        aDescFirstLine.setBounds(70, 180, 100, 20);
        JLabel aDescSecondLine = new JLabel("30x30格子");
        aDescSecondLine.setBounds(70, 200, 100, 20);
        add(aDescFirstLine);
        add(aDescSecondLine);
//        选项4
        custom = new JRadioButton("自定义");
        custom.setBounds(40, 220, 100, 20);
        add(custom);
//        描述和文本框
        JLabel widthLabel = new JLabel("宽 (10-40):");
        widthLabel.setBounds(70, 240, 80, 20);
        add(widthLabel);

        width = new JTextField();
        width.setBounds(170, 240, 40, 20);
        add(width);

        JLabel heightLabel = new JLabel("高 (10-40):");
        heightLabel.setBounds(70, 260, 90, 20);
        add(heightLabel);

        height = new JTextField();
        height.setBounds(170, 260, 40, 20);
        add(height);

        JLabel mineLabel = new JLabel("地雷 (1-1599):");
        mineLabel.setBounds(70, 280, 90, 20);
        add(mineLabel);

        mines = new JTextField();
        mines.setBounds(170, 280, 40, 20);
        add(mines);
//        开始游戏按钮
        start = new JButton("开始游戏");
        start.setBounds(80, 320, 100, 20);
        add(start);
//        初始化文本框编辑状态
        width.setEditable(false);
        height.setEditable(false);
        mines.setEditable(false);
//        在每个按键上添加监听事件
        custom.addActionListener(this);
        beginner.addActionListener(this);
        intermediate.addActionListener(this);
        advanced.addActionListener(this);
        start.addActionListener(this);
//        只能单选
        ButtonGroup group = new ButtonGroup();
        group.add(beginner);
        group.add(intermediate);
        group.add(advanced);
        group.add(custom);
//        初始化菜单实例
        beginner.setSelected(true);
        setSize(280, 400);
        setLayout(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    //    e 点击事件
    public void actionPerformed(ActionEvent e) {
//        如果选择自定义，设置文本框为可编辑状态
        if (e.getSource() == custom) {
            width.setEditable(true);
            height.setEditable(true);
            mines.setEditable(true);
        } else if (e.getSource() == start) {
            // 点击开始游戏后，获得炸弹数、宽、高
            int boardWidth = 0;
            int boardHeight = 0;
            int bombs = 0;
            boolean errorFlag = false;
            if (beginner.isSelected()) {
                boardWidth = 30;
                boardHeight = 30;
                bombs = 1;
            } else if (intermediate.isSelected()) {
                boardWidth = 20;
                boardHeight = 20;
                bombs = 20;
            } else if (advanced.isSelected()) {
                boardWidth = 30;
                boardHeight = 30;
                bombs = 899;
            } else {
                if (!checkValid(width.getText(), height.getText(), mines.getText())) {
//                  不符和要求则提示
                    errorFlag = true;
                    JOptionPane.showMessageDialog(null, "请输入正确的数字!");

                } else {
                    boardWidth = Integer.parseInt(width.getText());
                    boardHeight = Integer.parseInt(height.getText());
                    bombs = Integer.parseInt(mines.getText());
                }
            }

            if(!errorFlag)
            {
//                关闭当前窗口，开始游戏
                this.dispose();
                GameBoard b = new GameBoard("扫雷", boardWidth, boardHeight);
                new ProduceBombs(b, bombs);
                ((SmartSquare) b.getSquareAt(0, 0)).setStartTime(System.currentTimeMillis());
            }

        }else{
            // 如果没选择也没有开始游戏，则文本框不能编辑
            width.setEditable(false);
            height.setEditable(false);
            mines.setEditable(false);
        }
    }
//    检查输入的字段是否符合要求。
//    bWidth 宽
//    bHeight 高
//    bomb 炸弹数量
    private boolean checkValid(String bWidth, String bHeight, String bomb) {
        Pattern pattern = Pattern.compile("[0-9]*");
        if (bWidth == null || bHeight== null || bomb == null)
            return false;
        else if (bWidth.isEmpty() || bHeight.isEmpty() || bomb.isEmpty())
            return false;
        else if (!pattern.matcher(bWidth).matches() || !pattern.matcher(bHeight).matches() || !pattern.matcher(bomb).matches())
            return false;
        else if (Integer.parseInt(bWidth) < 10 || Integer.parseInt(bWidth) > 40 || Integer.parseInt(bHeight) < 10 || Integer.parseInt(bHeight) > 40
                || Integer.parseInt(bomb) < 1 || Integer.parseInt(bomb) > 1599)
            return false;
        else
            return Integer.parseInt(bWidth) * Integer.parseInt(bHeight) >= Integer.parseInt(bomb);
    }
}
