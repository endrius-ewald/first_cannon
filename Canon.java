import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JFrame;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.Dimension;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
/**
 * Classe para controlar o jogo do canhão, ela é responsável por criar
 * uma janela, desenhar o canhão, a barra de intesidade do tiro, o alvo,
 * e também a bala do canhão. Essa classe também fornece mecanismos para
 * indicar uma mensagem de game over ou de sucesso caso o jogador consiga
 * atingir os objetivos do jogo.
 * 
 * O canhão deve ser desenhado seguindo um ângulo, este ângulo é usado
 * para determinar a posição do canhão. 
 * 
 * @author Pedro Velho
 * @date 22/04/2013
 */
public class Canon extends JPanel{
  
 /*
  * Variável necessária para transformar o programa em um jar, posteriormente.
  */
 private static final long serialVersionUID = 1L;

 /*
  * Guarda imagem como matrizes, pixel a pixel
  */
 static int pixelSize=6;
 
 /*
  * Variável que guarda a potência do tiro.
  */
 static int potencia=50;
 
 /*
  * Imagem para mostrar a palavra game.
  */
 int [ ] [ ] game = {   
    { 1,1,1,1,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1 },
    { 1,1,1,1,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1 },
    { 1,0,0,0,0,1,0,0,0,1,0,1,1,0,0,1,0,0,1,1,0,1,0,0,0,0 },
    { 1,0,1,1,0,1,1,1,1,1,0,1,1,0,0,1,0,0,1,1,0,1,1,1,1,0 },
    { 1,0,0,1,0,1,1,1,1,1,0,1,1,0,0,1,0,0,1,1,0,1,0,0,0,0 },
    { 1,1,1,1,0,1,0,0,0,1,0,1,1,0,0,1,0,0,1,1,0,1,1,1,1,1 },
    { 1,1,1,1,0,1,0,0,0,1,0,1,1,0,0,1,0,0,1,1,0,1,1,1,1,1 }
  };
  
 /*
  * Imagem para mostrar a palavra over.
  */
  int [ ] [ ] over = {   
    { 1,1,1,1,1,0,1,0,0,0,1,0,1,1,1,1,1,0,1,1,1,1,1 },
    { 1,1,1,1,1,0,1,0,0,0,1,0,1,1,1,1,1,0,1,1,1,1,1 },
    { 1,0,0,0,1,0,1,0,0,0,1,0,1,0,0,0,0,0,1,0,0,0,1 },
    { 1,0,0,0,1,0,1,0,0,0,1,0,1,1,1,1,0,0,1,1,1,1,0 },
    { 1,0,0,0,1,0,1,1,0,1,1,0,1,0,0,0,0,0,1,1,1,1,1 },
    { 1,1,1,1,1,0,1,1,0,1,1,0,1,1,1,1,1,0,1,0,0,0,1 },
    { 1,1,1,1,1,0,0,1,1,1,0,0,1,1,1,1,1,0,1,0,0,0,1 }
  };
  

  /*
   * Imagem para mostrar a palavra you.
   */
  int [ ] [ ] you = {   
      { 1,1,0,1,1,0,0,1,1,1,0,0,1,0,0,0,1 },
      { 1,1,0,1,1,0,1,1,1,1,1,0,1,1,0,1,1 },
      { 0,1,0,1,0,0,1,1,0,1,1,0,1,1,0,1,1 },
      { 0,1,1,1,0,0,1,0,0,0,1,0,1,1,0,1,1 },
      { 0,0,1,1,0,0,1,1,0,1,1,0,1,1,0,1,1 },
      { 0,0,1,1,0,0,1,1,1,1,1,0,1,1,1,1,1 },
      { 0,0,1,1,0,0,0,1,1,1,0,0,0,1,1,1,0 }
    };
    
  /*
   * Imagem para mostrar a palavra win.
   */
    int [ ] [ ] win = {   
      { 1,0,1,0,1,0,1,0,1,1,0,0,0,1 },
      { 1,0,1,0,1,0,1,0,1,1,0,0,0,1 },
      { 1,0,1,0,1,0,1,0,1,1,1,1,0,1 },
      { 1,0,1,0,1,0,1,0,1,1,0,1,1,1 },
      { 1,0,1,0,1,0,1,0,1,1,0,0,1,1 },
      { 1,0,1,0,1,0,1,0,1,1,0,0,1,1 },
      { 0,1,0,1,0,0,1,0,1,1,0,0,1,1 }
    };

    /*
     * Imagem para mostrar a bala de canhão.
     */
    int [ ] [ ] ball = {   
        { 1 }
    };

    /*
     * Imagem do canhão, vertical.
     */
    static BufferedImage canon;
    static BufferedImage canonBase;
    static BufferedImage alvo;
    
  /*
   * Posição do canhão na tela.
   */
  private static int canonX=1;
  private static int canonY=400;
  
  /*
   * Posição do alvo na tela.
   * O alvo tem tamnho 80x78 pixels.
   */
  private static int alvoX=600;
  private static int alvoY=100;

  /*
   * Posição bala na tela.
   * A bala tem tamanho 10x10 pixels.
   */
  private static int balaX=600;
  private static int balaY=100;

  /*
   * Variável para rotacionar o canhão.
   */
  static AffineTransformOp op;
  
  /*
   * Tamanho, em pixels do sistema.
   */
  private static int boardWidth;
  private static int boardHeight;
  
  /*
   * Teclas que serão usadas durante o jogo.
   */
  private static boolean esquerda=false;
  private static boolean direita=false;
  private static boolean cima=false;
  private static boolean baixo=false;
  private static boolean espaco=false;
  private static boolean esc=false;

  /*
   * Variável booleana final de jogo.
   */
  private static boolean gameOver=false;

  /*
   * Variável booleana para indicar que venceu.
   */
  private static boolean youWin=false;
  
  /*
   * Inicializa a janela em estático.
   */
  private final static JFrame frame = new JFrame("Canon 1.0b");
  
  public static int larguraJanela(){
   return boardWidth*pixelSize;
  }

  public static int alturaJanela(){
   return boardHeight*pixelSize;
  }
  
  /**
   * Desenha na tela o jogo, desenha() é um comando para
   * desenhar na tela o jogo. O desenho deve ser explicitamente
   * chamado pelo desenvolvedor quando for necessário mostrar
   * algo na tela.
   */
  public static void desenha(){
    frame.repaint();
    try{
      Thread.sleep(80);
    }catch(java.lang.InterruptedException e){
    }
  }
  
  /**
   * Rotaciona o canhão de alguns graus dados como parâmetros.
   * Os graus variam entre -1 e -90.
   *
   * @param graus um inteiro com o angulo do canhão.
   */
  public static void rotate(int graus){

   double rotationRequired = Math.toRadians(graus);
   double locationX = canon.getWidth()/2;
   double locationY = canon.getHeight()/2;
   AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
   op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);   
  }
  

  /**
   * Configura uma posição x e y para o alvo ficar.
   * Deslocamento relativo ao canto superior
   * esquerdo da tela. Lembrem-se que o y cresce
   * para baixo na janela.
   * 
   * @param x deslocamento horizontal.
   * @param y deslocamento vertical.
   */
  public static void fixaAlvo(int x, int y){
   alvoX = x;
   alvoY = y;
  }

  /**
   * Fixa a posição da bala de canhão na tela.
   * Deslocamento relativo ao canto superior 
   * esquerdo da tela.Lembrem-se que o y cresce
   * para baixo na janela.
   * 
   * @param x deslocamento horizontal.
   * @param y deslocamento vertical.
   */
  public static void fixaBala(int x, int y){
   balaY = y;
   balaX = x;
  }
  
  /**
   * Configura a potência entre 0 e 100 %, este
   * valor deve ser traduzido para uma velocidade.
   * 
   * @param p nova potência a ser configurada.
   */
  public static void fixaPotencia(int p){
   potencia = p;
  }
  
  /**
   * Deve desenhar os elementos do jogo na tela, canhão, bala, 
   * alvo. Esse comando é chamado indiretamente através do desenha.
   * 
   * @param g um elemento gráfico para desenhar.
   * 
   * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
   */
  public void paintComponent(Graphics g) {
    g.setColor(Color.green);

    if(op == null){
     g.drawImage(canon, canonX, canonY, null); 
    }else{
     g.drawImage(op.filter(canon,null), canonX, canonY, null);
    }
 g.drawImage(canonBase, canonX+4, canonY+48, null); 
 g.drawImage(alvo, alvoX, alvoY, null); 
    
    if(gameOver){
      drawMsg(g, 10, 10, game); 
      drawMsg(g, 26, 20, over); 
    }
    
    if(youWin){
        drawMsg(g, 10, 10, you); 
        drawMsg(g, 26, 20, win); 
    }    
    
    //desenha a bala do canhão
    g.setColor(Color.black);
    g.fillOval(balaX, balaY, 10, 10);

    //desenha a barra de potência
    g.setColor(Color.gray);
    g.drawRect(10, 10, 100, 20);
    g.setColor(Color.blue);
    g.fillRect(10, 10, potencia, 20);
    g.drawString(""+potencia+" %", 125, 30);
  }
  
  /** 
   * Desenha uma mensagem na tela na posição x, y.
   *
   * @param g um elemento gráfico.
   * @param x coordenada x para fazer translação.
   * @param y coordenada y para fazer translação.
   * @param msg matriz com um mapa dos pixels a serem desenhados. 
   */
  private void drawMsg(Graphics g, int x, int y, int[][] msg){
   for(int i=0; i<msg[0].length; i++){
         for(int j=0; j < msg.length; j++){
           if(msg[j][i] == 1){
             g.drawOval((i+x)*pixelSize - pixelSize, (j+y)*pixelSize - pixelSize, pixelSize, pixelSize);
             g.fillOval((i+x)*pixelSize - pixelSize, (j+y)*pixelSize - pixelSize, pixelSize, pixelSize);
           }
         }
    }
  }
  
  /**
   * Desenha uma mensagem de game over na próxima
   * vez que chamar desenha(). 
   */
  public static void gameOver(){
    gameOver = true;
  }

  /**
   * Desenha uma mensagem de you win na próxima
   * vez que chamar desenha(). 
   */
  public static void youWin(){
    youWin = true;
  }
 
  /**
   * Retorna verdadeiro se a tecla ESC estiver sendo
   * pressionada pelo usuário.
   * 
   * @return verdadeiro (true) caso a ESC estiver
   * sendo precionada, falso (false) caso contrário.
   */
  public static boolean apertouEsc(){
    return esc;
  }
  
  /**
   * Retorna verdadeiro se a SETA ESQUERDA estiver sendo
   * pressionada pelo usuário.
   * 
   * @return verdadeiro (true) caso a seta para a esquerda estiver
   * sendo precionada, falso (false) caso contrário.
   */
  public static boolean apertouEsquerda(){
    return esquerda;
  }
  
  /**
   * Retorna verdadeiro se a SETA DIREITA estiver sendo
   * pressionada pelo usuário.
   * 
   * @return verdadeiro (true) caso a seta para a direita estiver
   * sendo precionada, falso (false) caso contrário.
   */
  public static boolean apertouDireita(){
    return direita;
  }

  /**
   * Retorna verdadeiro se SETA CIMA estiver sendo
   * pressionada pelo usuário.
   * 
   * @return verdadeiro (true) caso a seta para a cima estiver
   * sendo precionada, falso (false) caso contrário.
   */
  public static boolean apertouCima(){
    return cima;
  }

  /**
   * Retorna verdadeiro se SETA BAIXO estiver sendo
   * pressionada pelo usuário.
   * 
   * @return verdadeiro (true) caso a seta para a baixo estiver
   * sendo precionada, falso (false) caso contrário.
   */
  public static boolean apertouBaixo(){
    return baixo;
  }

  /**
   * Retorna verdadeiro se ESPAÇO estiver sendo
   * pressionado pelo usuário.
   * 
   * @return verdadeiro (true) caso espaço estiver
   * sendo precionada, falso (false) caso contrário.
   */
  public static boolean apertouEspaco(){
    return espaco;
  }
  
  /**
   * Inicia a janela do jogo. Este método tem que ser chamado
   * somente uma vez em todo o programa.
   */
  public static void init(){
    boardWidth = 120;
    boardHeight = 80;
    
    canon = null;
    try {
        canon = ImageIO.read(new File("canon.png"));
    } catch (IOException e) {
     System.err.println("Erro ao carregar imagem!");
    }
    rotate(0);
    
    canonBase = null;
    try {
        canonBase = ImageIO.read(new File("canon_base.png"));
    } catch (IOException e) {
     System.err.println("Erro ao carregar imagem!");
    }
    
    alvo = null;
    try {
        alvo = ImageIO.read(new File("target.png"));
    } catch (IOException e) {
     System.err.println("Erro ao carregar imagem!");
    }

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Canon a = new Canon();
    a.setPreferredSize(new Dimension(boardWidth*pixelSize,boardHeight*pixelSize));
    frame.getContentPane().add(a);
    frame.pack();
    frame.setResizable(false);
    frame.setVisible(true);
    frame.setBackground(Color.WHITE);
    frame.addKeyListener(new KeyListener() {    
      public void keyPressed(KeyEvent e) { 
        if (e.getKeyCode() == KeyEvent.VK_RIGHT){
          //seta para a direita
          direita = true;
          esquerda = false;
        }else if (e.getKeyCode() == KeyEvent.VK_LEFT){
          //seta para a esquerda
          direita = false;
          esquerda = true;          
        }else if (e.getKeyCode() == KeyEvent.VK_UP){
          cima = true;          
          baixo = false;
        }else if (e.getKeyCode() == KeyEvent.VK_DOWN){
          cima = false;          
          baixo = true;
        }else if (e.getKeyCode() == KeyEvent.VK_SPACE){
         System.out.println("Apertou space");
         espaco = true;          
        }else if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
            esc = true;          
        }
      }
      
      //quando solta a tecla todas deixam de estar precionadas
      public void keyReleased(KeyEvent e) {
              direita = false;
              esquerda = false;          
              cima = false;
              baixo = false;
              espaco = false;
              esc = false;                
      }
      
      public void keyTyped(KeyEvent e) {
      }
      
    });
    
  }
  
}
