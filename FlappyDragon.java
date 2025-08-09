import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FlappyDragon extends JPanel implements ActionListener, KeyListener {
    // Dimensões da tela do jogo
    int boardWidth = 360;
    int boardHeight = 640;

    // Imagens do jogo
    Image backgroundImg;             // Fundo do cenário
    Image[] dragonFrames;            // Frames de animação do dragão
    Image topPipeImg;                // Cano de cima
    Image bottomPipeImg;             // Cano de baixo

    // Posição e tamanho do dragão
    int dragonX = boardWidth / 6;    // Posição X fixa (ele "não anda" horizontalmente)
    int dragonY = boardHeight / 2;   // Posição inicial Y
    int dragonWidth = 48;            // Largura do dragão
    int dragonHeight = 48;           // Altura do dragão

    // Classe que representa o dragão
    class Dragon {
        int x = dragonX;
        int y = dragonY;
        int width = dragonWidth;
        int height = dragonHeight;
    }

    // Propriedades dos canos
    int pipeX = boardWidth;  // Cano começa à direita da tela
    int pipeY = 0;
    int pipeWidth = 64;      // Largura dos canos
    int pipeHeight = 512;    // Altura dos canos

    // Classe que representa um cano
    class Pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false; // Marca se o dragão já passou por esse cano

        Pipe(Image img) {
            this.img = img;
        }
    }

    // Lógica do jogo
    Dragon dragon;                   // Instância do dragão
    int velocityX = -5;              // Velocidade com que os canos andam para a esquerda
    int velocityY = 0;               // Velocidade vertical do dragão
    int gravity = 1;                 // Gravidade que puxa o dragão para baixo

    ArrayList<Pipe> pipes;           // Lista de canos atuais na tela
    Random random = new Random();    // Gerador de números aleatórios

    Timer gameLoop;                  // Loop principal do jogo (60 FPS)
    Timer placePipeTimer;            // Timer para adicionar novos canos periodicamente
    boolean gameOver = false;        // Flag para saber se o jogo acabou
    double score = 0;                // Pontuação do jogador

    // Controle da animação do dragão
    int currentFrame = 0;            // Qual frame está sendo exibido
    int animationCounter = 0;        // Contador para trocar de frame
    int animationSpeed = 8;          // A cada 5 ciclos troca de frame

    FlappyDragon() {
        // Configurações básicas do painel do jogo
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);

        // Carrega as imagens
        try {
            backgroundImg = new ImageIcon(getClass().getResource("./Mapa_3.jpg")).getImage();
            topPipeImg = new ImageIcon(getClass().getResource("./bamboo.png")).getImage();
            bottomPipeImg = new ImageIcon(getClass().getResource("./bamboo.png")).getImage();

            // Carrega os 4 frames da animação do dragão
            dragonFrames = new Image[]{
                new ImageIcon(getClass().getResource("./dragao1.png")).getImage(),
                new ImageIcon(getClass().getResource("./dragao2.png")).getImage(),
                new ImageIcon(getClass().getResource("./dragao3.png")).getImage(),
                new ImageIcon(getClass().getResource("./dragao4.png")).getImage()
            };
        } catch (Exception e) {
            System.err.println("ERRO: Não foi possível carregar as imagens");
            e.printStackTrace();
        }

        // Cria o dragão e a lista de canos
        dragon = new Dragon();
        pipes = new ArrayList<>();

        // Timer que cria novos canos a cada 1.5 segundos
        placePipeTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });
        placePipeTimer.start();

        // Loop principal do jogo, rodando a 60 FPS
        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
    }

    // Adiciona um par de canos (superior e inferior)
    void placePipes() {
        // Define a posição vertical aleatória para criar abertura
        int randomPipeY = (int) (pipeY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        int openingSpace = boardHeight / 4; // Espaço entre os canos

        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
    }

    // Método padrão do Swing para desenhar na tela
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    // Desenha todos os elementos do jogo
    public void draw(Graphics g) {
        // Desenha o fundo
        if (backgroundImg != null) {
            g.drawImage(backgroundImg, 0, 0, this.boardWidth, this.boardHeight, null);
        } else {
            g.setColor(new Color(135, 206, 235)); // Cor azul do céu
            g.fillRect(0, 0, boardWidth, boardHeight);
        }

        // Desenha o dragão com animação
        if (dragonFrames != null) {
            g.drawImage(dragonFrames[currentFrame], dragon.x, dragon.y, dragon.width, dragon.height, null);
        } else {
            g.setColor(Color.RED);
            g.fillOval(dragon.x, dragon.y, dragon.width, dragon.height);
        }

        // Desenha todos os canos na tela
        for (Pipe pipe : pipes) {
            if (pipe.img != null) {
                g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
            } else {
                g.setColor(Color.GREEN);
                g.fillRect(pipe.x, pipe.y, pipe.width, pipe.height);
            }
        }

        // Desenha a pontuação
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (gameOver) {
            g.drawString("Game Over: " + (int) score, 10, 35);
        } else {
            g.drawString(String.valueOf((int) score), 10, 35);
        }
    }

    // Atualiza a lógica do jogo a cada frame
    public void move() {
        // Física do dragão: aplica gravidade e move
        velocityY += gravity;
        dragon.y += velocityY;
        dragon.y = Math.max(dragon.y, 0); // Não deixa sair pela parte de cima

        // Animação do dragão: troca o frame periodicamente
        animationCounter++;
        if (animationCounter >= animationSpeed) {
            currentFrame = (currentFrame + 1) % dragonFrames.length;
            animationCounter = 0;
        }

        // Movimenta todos os canos para a esquerda
        for (Pipe pipe : pipes) {
            pipe.x += velocityX;

            // Se o dragão passou pelo cano, soma 0.5 pontos (2 canos = 1 ponto inteiro)
            if (!pipe.passed && dragon.x > pipe.x + pipe.width) {
                score += 0.5;
                pipe.passed = true;
            }

            // Se colidir com o cano, game over
            if (collision(dragon, pipe)) {
                gameOver = true;
            }
        }

        // Se o dragão cair fora da tela, game over
        if (dragon.y > boardHeight) {
            gameOver = true;
        }
    }

    // Verifica colisão entre o dragão e um cano
    boolean collision(Dragon a, Pipe b) {
        return a.x < b.x + b.width &&
               a.x + a.width > b.x &&
               a.y < b.y + b.height &&
               a.y + a.height > b.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();      // Atualiza a lógica
        repaint();   // Redesenha a tela
        if (gameOver) {
            placePipeTimer.stop(); // Para de gerar novos canos
            gameLoop.stop();       // Para o loop principal
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityY = -9; // Pulo do dragão

            if (gameOver) {
                // Reinicia o jogo se apertar espaço após o game over
                dragon.y = dragonY;
                velocityY = 0;
                pipes.clear();
                gameOver = false;
                score = 0;
                currentFrame = 0;
                gameLoop.start();
                placePipeTimer.start();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    // Método principal para iniciar o jogo
    public static void main(String[] args) {
        JFrame frame = new JFrame("Flappy Dragon"); // Janela do jogo
        FlappyDragon game = new FlappyDragon();      // Cria instância do jogo

        frame.add(game);                             // Adiciona o painel do jogo
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);           // Centraliza a janela
    }
}
