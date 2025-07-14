import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FlappyDragon extends JPanel implements ActionListener, KeyListener {
    int boardWidth = 360;
    int boardHeight = 640;

    // images
    Image backgroundImg;
    Image DragonImg;
    Image topPipeImg;
    Image bottomPipeImg;

    // Dragon class
    int DragonX = boardWidth / 8;
    int DragonY = boardHeight / 2;
    int DragonWidth = 34;
    int DragonHeight = 24;

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

    // pipe class
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64; // scaled by 1/6
    int pipeHeight = 512;

    class Pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;

        Pipe(Image img) {
            this.img = img;
        }
    }

    // game logic
    Dragon Dragon;
    int velocityX = -4; // move pipes to the left speed (simulates Dragon moving right)
    int velocityY = 0; // move Dragon up/down speed.
    int gravity = 1;

    ArrayList<Pipe> pipes;
    Random random = new Random();

    Timer gameLoop;
    Timer placePipeTimer;
    boolean gameOver = false;
    double score = 0;

    FlappyDragon() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        // setBackground(Color.blue);
        setFocusable(true);
        addKeyListener(this);

        // load images
        backgroundImg = new ImageIcon(getClass().getResource("./Mapa_3.jpg")).getImage();
        DragonImg = new ImageIcon(getClass().getResource("./flappyDragon.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./bamboo.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bamboo.png")).getImage();

        // Dragon
        Dragon = new Dragon(DragonImg);
        pipes = new ArrayList<Pipe>();

        // place pipes timer
        placePipeTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to be executed
                placePipes();
            }
        });
        placePipeTimer.start();

        // game timer
        gameLoop = new Timer(1000 / 60, this); // how long it takes to start timer, milliseconds gone between frames
        gameLoop.start();
    }

    void placePipes() {
        // (0-1) * pipeHeight/2.
        // 0 -> -128 (pipeHeight/4)
        // 1 -> -128 - 256 (pipeHeight/4 - pipeHeight/2) = -3/4 pipeHeight
        int randomPipeY = (int) (pipeY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        int openingSpace = boardHeight / 4;

        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // background
        g.drawImage(backgroundImg, 0, 0, this.boardWidth, this.boardHeight, null);

        // Dragon
        g.drawImage(DragonImg, Dragon.x, Dragon.y, Dragon.width, Dragon.height, null);

        // pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        // score
        g.setColor(Color.white);

        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (gameOver) {
            g.drawString("Game Over: " + String.valueOf((int) score), 10, 35);
        } else {
            g.drawString(String.valueOf((int) score), 10, 35);
        }

    }

    public void move() {
        // Dragon
        velocityY += gravity;
        Dragon.y += velocityY;
        Dragon.y = Math.max(Dragon.y, 0); // apply gravity to current Dragon.y, limit the Dragon.y to top of the canvas

        // pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            if (!pipe.passed && Dragon.x > pipe.x + pipe.width) {
                score += 0.5; // 0.5 because there are 2 pipes! so 0.5*2 = 1, 1 for each set of pipes
                pipe.passed = true;
            }

            if (collision(Dragon, pipe)) {
                gameOver = true;
            }
        }

        if (Dragon.y > boardHeight) {
            gameOver = true;
        }
    }

    boolean collision(Dragon a, Pipe b) {
        return a.x < b.x + b.width && // a's top left corner doesn't reach b's top right corner
                a.x + a.width > b.x && // a's top right corner passes b's top left corner
                a.y < b.y + b.height && // a's top left corner doesn't reach b's bottom left corner
                a.y + a.height > b.y; // a's bottom left corner passes b's top left corner
    }

    @Override
    public void actionPerformed(ActionEvent e) { // called every x milliseconds by gameLoop timer
        move();
        repaint();
        if (gameOver) {
            placePipeTimer.stop();
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // System.out.println("JUMP!");
            velocityY = -9;

            if (gameOver) {
                // restart game by resetting conditions
                Dragon.y = DragonY;
                velocityY = 0;
                pipes.clear();
                gameOver = false;
                score = 0;
                gameLoop.start();
                placePipeTimer.start();
            }
        }
    }

    // not needed
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
