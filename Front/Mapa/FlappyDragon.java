import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


public class FlappyDragon extends JPanel{
    int boardWidth = 736;
    int boardHeight = 414;

    //Imagens

    Image backgroundImg;
    Image DragonImg;
    Image topPipeImg;
    Image bottomPipeImg;

    //Dragão
    int DragonX = boardWidth / 8; // Posição inicial do Dragão
    int DragonY = boardHeight / 2; // Posição inicial do Dragão
    int DragonWidth = 50; // Largura do Dragão
    int DragonHeight = 50; // Altura do Dragão

    class Dragon {
        int x = DragonX;
        int y = DragonY;
        int width = DragonWidth;
        int height = DragonHeight;
        Image img;

        Dragon(Image img) {
            this.img = img;
        }
    } 
     
    // lógica do jogo
     Dragon dragon;


     //Carregando as imagens
     FlappyDragon() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));     
        backgroundImg = new ImageIcon(getClass(). getResource("./Mapa_3.jpg")).getImage();
        DragonImg = new ImageIcon(getClass().getResource("./Dragão_voando1.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./bamboo.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bamboo.png")).getImage();
        
        // Inicializando o Dragão
        dragon = new Dragon(DragonImg);
    

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    //Carregando background
    public void draw(Graphics g ) {
        g.drawImage(backgroundImg, 0,0, boardWidth, boardHeight, null);
    }
}
