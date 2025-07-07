import javax.swing.JFrame; // Cria a janela principal
import javax.swing.JPanel; // Cria o painel de desenho
import java.awt.color; // Importa a classe de cores
import java.awt.Graphics; // Importa a classe de gráficos



public class FlappyDragon {
  public static void main(String[] args) {

    int boardWidth = 800; // Largura do tabuleiro
    int boardHeight = 600; // Altura do tabuleiro

    JFrame frame = new JFrame("Flappy Dragon"); // Cria a janela principal
    frame.setVisible(true); // Torna a janela visível
    frame.setLocationRelativeTo(null); // Centraliza a janela na tela
    frame.setResizable(false); // Impede o redimensionamento da janela
  }
}
