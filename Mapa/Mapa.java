import javax.swing.*;

public class Mapa{
  public static void main(String[] args) {

    int boardWidth = 713; // Largura do tabuleiro
    int boardHeight = 414; // Altura do tabuleiro

    JFrame frame = new JFrame("Flappy Dragon"); // Cria a janela principal
    frame.setVisible(true); // Torna a janela visível
    frame.setSize(boardWidth, boardHeight);
    frame.setLocationRelativeTo(null); // Centraliza a janela na tela
    frame.setResizable(false); // Impede o redimensionamento da janela
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Encerra o jogo por completo 
    //ao sair da janela
  
  
  }
}
