

import java.util.Random;

/*
 * AUTOR : ENDRIUS EWALD
 * DATA: 12/05/2014
 * DISCIPLINA: ALGORITIMOS E PROGRAMACAO
 * FUNCIONALIDADE: Este programa é um game no qual o jogador tem que atingir
 *                 um alvo, de posicionamento aleatorio, dispondo apenas de 
 *                 3 projeteis. O jogador tem 3 tentativas para executar a 
 *                 tarefa  controlando a INCLINACAO e a POTENCIA  do CANHAO.
 */


public class JogoCanon{
  public static void main(String args[]){
    
    //Inicia o programa auxiliar
    Canon.init();
    
    //Variaveis
    int mira,potencia,X,Y,vida,tempo,sx,sy;
    boolean bool;
    
    //Declarando variaveis
    mira = -45;
    sx = 67;
    sy = 400;
    potencia = 50;
    X = 67;
    Y = 400;
    vida = 0;
    bool = true;
    tempo = 0;
    
    //Fixa Alvo
    /*Devido a formula do decaimento da bala nao ser fidedigna  em aulgumas extremidades 
     * nao é possivel atingir o alvo, para fixa-lo em um lugar padrao iniba as linhas abaixo
     */ 
    Random r = new Random();
    int H = (r.nextInt()%(Canon.alturaJanela() - 80));
    int W = (r.nextInt()%(Canon.larguraJanela() - 80));   
    Canon.fixaAlvo ( Math.abs(W) , Math.abs(H) );
    
    //Mira
    while(true){
      
      //Diminui Potencia
      if (Canon.apertouEsquerda()){
        if(potencia > 1){
          potencia--;
          //Debug potencia\\
          //System.out.println(potencia);
        } 
      }
      
      //Aumenta Potencia
      if (Canon.apertouDireita()){
        if(potencia < 100){
          potencia++;
          //Debug potencia\\
          //System.out.println(potencia);
        }
      }
      
      //Desce Canhao
      if (Canon.apertouBaixo()){        
        if (mira < 0){
          mira++;
          sx++;
          sy++;
          System.out.println(Math.sin(Math.PI/180*-mira));
          //Debug mira\\
          //System.out.println(mira);
        }
      }
      
      //Sobe Canhao
      if (Canon.apertouCima()){
        if (mira > -90){
          mira--;
          sx--;
          sy--;
          System.out.println(Math.sin(Math.PI/180*-mira));
          //Debug mira\\
          //System.out.println(mira);
        }
      }
      
      //Posiciona a bala ao lado do canhao
      Canon.fixaBala(80,450);
      //Posiciona a potencia
      Canon.fixaPotencia(potencia);
      //Posiciona o canhao
      Canon.rotate(mira);
      //Desenha o canhao, a bala e o alvo  
      Canon.desenha();
      //Deslocamento 
      if (Canon.apertouEspaco()){
        
        //Verifica se ha vidas disponiveis
        if (vida < 3){
          
          //Posiciona a bala dependendo da posicao do canhao(mira)
          X = sx;
          Y = sy;
          //Movimento Bala 
          while (X < Canon.larguraJanela()+10 && X > -10 && Y < Canon.alturaJanela()+10){// && Y > -10){
            
            //Calcula a trajetoria da bala
            tempo++;
            X = X + (int)(potencia/Math.sin(Math.PI/180*-mira));
            Y = Y - (int)(potencia*Math.sin(Math.PI/180*-mira)*tempo) + (int)((Math.pow(5*tempo,2))/2); 
            System.out.println("X"+X+"Y"+Y);
 
            //Debug X da bala\\
            //System.out.print("X="+X+" ");
            //Debug Y da bala\\
            //System.out.println("Y="+Y);
            
            //Verifica se acertou o Alvo
            while (X >= Math.abs(W) && X <= (Math.abs(W)+70) && Y >= Math.abs(H) && Y <= (Math.abs(H)+70)){
              if (bool){
                Canon.fixaBala(X,Y);
                Canon.youWin();
                Canon.desenha();
                bool = false;
              }
            }
            
            //Posiciona Bala durante disparo
            Canon.fixaBala(X,Y);
            //Desenha Bala durante disparo
            Canon.desenha();
          }
          //Recoloca a bala para novo disparo
          X = 67;
          Y = 400;
          tempo = 0;
          
        //Encerra programa apos ficar sem vidas e clicar a tecla espaço
        }else{
          System.exit(0);
        }
        
        //Consome vida apos fim do disparo
        vida++;
        //Debug Vida\\
        //System.out.println(vida);
        
        //Verifica se nao ha vidas
        if (vida >= 3){
          Canon.gameOver();
          Canon.desenha();
        }    
      } 
    }
  }
}
//Fim do Programa
  
