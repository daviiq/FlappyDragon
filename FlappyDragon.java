import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


public class FlappyDragon extends JPanel implements ActionListener, KeyListener {
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
    int velocity = 0; // Velocidade do Dragão
    int gravity = 2; // Gravidade que afeta o Dragão

    // timer para o jogo
    Timer gameloop;

    //Carregando as imagens
    FlappyDragon() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);  
        addKeyListener(null);
        
        backgroundImg = new ImageIcon(getClass(). getResource("./Mapa_3.jpg")).getImage();
        DragonImg = new ImageIcon(getClass().getResource("./Dragão_voando1.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./bamboo.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bamboo.png")).getImage();
        
        // Inicializando o Dragão
        dragon = new Dragon(DragonImg);

        //game timer
        gameloop = new Timer(1000/60, this);
    gameloop.start(); // Inicia o loop do jogo
}

//Carregando background
public void draw(Graphics g) {
    System.out.println("Desenhando o jogo...");
    g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);

    //Dragão
    g.drawImage(dragon.img, dragon.x, dragon.y, dragon.width, dragon.height, null);
}

    public void move() {
        //dragão movimentação
        velocity += gravity; // Aplica a gravidade ao Dragão
        dragon.y += velocity; // Atualiza a posição do Dragão com base na velocidade
        dragon.y = Math.max(0, Math.min(dragon.y, boardHeight - dragon.height)); // Garante que o Dragão não saia da tela
     }



    @Override
    public void actionPerformed(ActionEvent e) {
        // Atualiza a lógica do jogo aqui
        // Por exemplo, atualizar a posição do Dragão, verificar colisões, etc.
        repaint();

    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE); {
        velocity = -9;}
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
