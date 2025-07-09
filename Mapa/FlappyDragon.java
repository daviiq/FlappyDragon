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
    Image DragãoImg1;
    Image DragãoImg2;
    Image DragãoImg3;
    Image DragãoImg4;
    Image topPipeImg;
    Image bottomPipeImg;

    
    //Carregando as imagens
    FlappyDragon() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));     
        backgroundImg = new ImageIcon(getClass(). getResource("./Mapa_3.jpg")).getImage();
        DragãoImg1 = new ImageIcon(getClass().getResource("./Dragão_voando1.png")).getImage();
        DragãoImg2 = new ImageIcon(getClass().getResource("./Dragão_voando2.png")).getImage();
        DragãoImg3 = new ImageIcon(getClass().getResource("./Dragão_voando3.png")).getImage();
        DragãoImg4 = new ImageIcon(getClass().getResource("./Dragão_voando4.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./bamboo.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bamboo.png")).getImage();
    
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
