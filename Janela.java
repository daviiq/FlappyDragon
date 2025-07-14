import javax.swing.*;

public class Janela {
  public static void main(String[] args) throws Exception {
    int boardWidth = 736;
    int boardHeight = 414;

    JFrame frame = new JFrame("Flappy Dragon");
    // frame.setVisible(true);
    frame.setSize(boardWidth, boardHeight);
    frame.setLocationRelativeTo(null);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    FlappyDragon flappyDragon = new FlappyDragon();
    frame.add(flappyDragon);
    frame.pack();
    flappyDragon.requestFocus();
    frame.setVisible(true);
  }
}
