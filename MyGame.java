public class MyGame { 
 public static void main(String args[]) { 
 int col; 
 int lin; 
 
 col = 1; 
 lin = 20; 
 
 //chamado somente uma vez 
 Canon.init(); 
 
 while(col++ < 190){ 
 Canon.fixaBala(lin, col); 
 Canon.desenha(); 
 } 
 
 } 
 }