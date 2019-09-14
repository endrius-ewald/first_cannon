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
 * Classe para controlar o jogo do canh�o, ela � respons�vel por criar
 * uma janela, desenhar o canh�o, a barra de intesidade do tiro, o alvo,
 * e tamb�m a bala do canh�o. Essa classe tamb�m fornece mecanismos para
 * indicar uma mensagem de game over ou de sucesso caso o jogador consiga
 * atingir os objetivos do jogo.
 * 
 * O canh�o deve ser desenhado seguindo um �ngulo, este �ngulo � usado
 * para determinar a posi��o do canh�o. 
 * 
 * @author Pedro Velho
 * @date 22/04/2013
 */
public class Canon extends JPanel{
  
 /*
  * Vari�vel necess�ria para transformar o programa em um jar, posteriormente.
  */
 private static final long serialVersionUID = 1L;

 /*
  * Guarda imagem como matrizes, pixel a pixel
  */
 static int pixelSize=6;
 
 /*
  * Vari�vel que guarda a pot�ncia do tiro.
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
     * Imagem para mostrar a bala de canh�o.
     */
    int [ ] [ ] ball = {   
        { 1 }
    };

    /*
     * Imagem do canh�o, vertical.
     */
    static BufferedImage canon;
    static BufferedImage canonBase;
    static BufferedImage alvo;
    
  /*
   * Posi��o do canh�o na tela.
   */
  private static int canonX=1;
  private static int canonY=400;
  
  /*
   * Posi��o do alvo na tela.
   * O alvo tem tamnho 80x78 pixels.
   */
  private static int alvoX=600;
  private static int alvoY=100;

  /*
   * Posi��o bala na tela.
   * A bala tem tamanho 10x10 pixels.
   */
  private static int balaX=600;
  private static int balaY=100;

  /*
   * Vari�vel para rotacionar o canh�o.
   */
  static AffineTransformOp op;
  
  /*
   * Tamanho, em pixels do sistema.
   */
  private static int boardWidth;
  private static int boardHeight;
  
  /*
   * Teclas que ser�o usadas durante o jogo.
   */
  private static boolean esquerda=false;
  private static boolean direita=false;
  private static boolean cima=false;
  private static boolean baixo=false;
  private static boolean espaco=false;
  private static boolean esc=false;

  /*
   * Vari�vel booleana final de jogo.
   */
  private static boolean gameOver=false;

  /*
   * Vari�vel booleana para indicar que venceu.
   */
  private static boolean youWin=false;
  
  /*
   * Inicializa a janela em est�tico.
   */
  private final static JFrame frame = new JFrame("Canon 1.0b");
  
  public static int larguraJanela(){
   return boardWidth*pixelSize;
  }

  public static int alturaJanela(){
   return boardHeight*pixelSize;
  }
  
  /**
   * Desenha na tela o jogo, desenha() � um comando para
   * desenhar na tela o jogo. O desenho deve ser explicitamente
   * chamado pelo desenvolvedor quando for necess�rio mostrar
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
   * Rotaciona o canh�o de alguns graus dados como par�metros.
   * Os graus variam entre -1 e -90.
   *
   * @param graus um inteiro com o angulo do canh�o.
   */
  public static void rotate(int graus){

   double rotationRequired = Math.toRadians(graus);
   double locationX = canon.getWidth()/2;
   double locationY = canon.getHeight()/2;
   AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
   op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);   
  }
  

  /**
   * Configura uma posi��o x e y para o alvo ficar.
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
   * Fixa a posi��o da bala de canh�o na tela.
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
   * Configura a pot�ncia entre 0 e 100 %, este
   * valor deve ser traduzido para uma velocidade.
   * 
   * @param p nova pot�ncia a ser configurada.
   */
  public static void fixaPotencia(int p){
   potencia = p;
  }
  
  /**
   * Deve desenhar os elementos do jogo na tela, canh�o, bala, 
   * alvo. Esse comando � chamado indiretamente atrav�s do desenha.
   * 
   * @param g um elemento gr�fico para desenhar.
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
    
    //desenha a bala do canh�o
    g.setColor(Color.black);
    g.fillOval(balaX, balaY, 10, 10);

    //desenha a barra de pot�ncia
    g.setColor(Color.gray);
    g.drawRect(10, 10, 100, 20);
    g.setColor(Color.blue);
    g.fillRect(10, 10, potencia, 20);
    g.drawString(""+potencia+" %", 125, 30);
  }
  
  /** 
   * Desenha uma mensagem na tela na posi��o x, y.
   *
   * @param g um elemento gr�fico.
   * @param x coordenada x para fazer transla��o.
   * @param y coordenada y para fazer transla��o.
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
   * Desenha uma mensagem de game over na pr�xima
   * vez que chamar desenha(). 
   */
  public static void gameOver(){
    gameOver = true;
  }

  /**
   * Desenha uma mensagem de you win na pr�xima
   * vez que chamar desenha(). 
   */
  public static void youWin(){
    youWin = true;
  }
 
  /**
   * Retorna verdadeiro se a tecla ESC estiver sendo
   * pressionada pelo usu�rio.
   * 
   * @return verdadeiro (true) caso a ESC estiver
   * sendo precionada, falso (false) caso contr�rio.
   */
  public static boolean apertouEsc(){
    return esc;
  }
  
  /**
   * Retorna verdadeiro se a SETA ESQUERDA estiver sendo
   * pressionada pelo usu�rio.
   * 
   * @return verdadeiro (true) caso a seta para a esquerda estiver
   * sendo precionada, falso (false) caso contr�rio.
   */
  public static boolean apertouEsquerda(){
    return esquerda;
  }
  
  /**
   * Retorna verdadeiro se a SETA DIREITA estiver sendo
   * pressionada pelo usu�rio.
   * 
   * @return verdadeiro (true) caso a seta para a direita estiver
   * sendo precionada, falso (false) caso contr�rio.
   */
  public static boolean apertouDireita(){
    return direita;
  }

  /**
   * Retorna verdadeiro se SETA CIMA estiver sendo
   * pressionada pelo usu�rio.
   * 
   * @return verdadeiro (true) caso a seta para a cima estiver
   * sendo precionada, falso (false) caso contr�rio.
   */
  public static boolean apertouCima(){
    return cima;
  }

  /**
   * Retorna verdadeiro se SETA BAIXO estiver sendo
   * pressionada pelo usu�rio.
   * 
   * @return verdadeiro (true) caso a seta para a baixo estiver
   * sendo precionada, falso (false) caso contr�rio.
   */
  public static boolean apertouBaixo(){
    return baixo;
  }

  /**
   * Retorna verdadeiro se ESPA�O estiver sendo
   * pressionado pelo usu�rio.
   * 
   * @return verdadeiro (true) caso espa�o estiver
   * sendo precionada, falso (false) caso contr�rio.
   */
  public static boolean apertouEspaco(){
    return espaco;
  }
  
  /**
   * Inicia a janela do jogo. Este m�todo tem que ser chamado
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
