
import java.awt.Graphics;
import javax.swing.ImageIcon;

/**
 *
 * @author lgguzman
 */
public class Bomberman {
    public static final int UP=0;
    public static final int RIGTH=1;
    public static final int DOWN=2;
    public static final int LEFT=3;
    public static final int NONE=-1;
    public static final int BOMB=5;
    Animation [] animations;
    Animation [] bombas;
    Bomba Bum = new Bomba();
    public int x;
    public int y;
    public int vx;
    public int vy;
    public int bx;
    public int by;
    public int bcurrent;
    String path;
    int currentDirection;
    int direction;

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
    
    public Bomberman(int x, int y, int vx, int vy, String path) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.path = path;
        this.currentDirection=LEFT;
        this.direction=NONE;
        animations= new Animation[4];
        bombas = new Animation[6];
    }

    public void loadPic(){
        String name[]={"arriba","adelante","abajo","atras"};
        for (int j = 0; j < 4; j++) {
            
             animations[j]=new Animation();
             for (int i = 1; i <= 5; i++) {
            animations[j].addScene(
                    new ImageIcon(
                    getClass().getResource(
                            path+"//"+name[j]+i+".png")).getImage(), 100);
            }
        }
    }
    
    public void draw(Graphics g){
        g.drawImage(animations[currentDirection].getImage(), x, y, 50, 50, null);
    }
    
    
    public void move( long time,int Mat[][]){
        if(direction!=NONE)
            currentDirection=direction;
        switch (direction){
            case LEFT:{  
                   if(Colisiones(2,Mat)==true){
                    x-=vx; 
                    }
                    break;
            }
             case RIGTH:{
                
                    if(Colisiones(1,Mat)==true){
                     x+=vx;
                    }
                    break;
            }
              case DOWN:{
                
                    if(Colisiones(4,Mat)==true){
                    y+=vy;  
                    }
                    break;
            }
               case UP:{
                
                   if(Colisiones(3,Mat)==true){
                   y-=vy; 
                   }
                   break;
            } 

        }
        if(direction!=NONE)
            animations[currentDirection].update(time);
    }  
    
     public boolean Colisiones(int Sw, int Mat[][]){
        double Pox=x;
        double Poy=y;
        int Fila=0;
        int Fila1=0;
        int Columna=0;
        int Columna1=0;
        boolean Des=true;
        
        switch(Sw){
            case 1:
            //RIGHT
            Fila = (int)(Pox+50)/50;
            Fila1 = Fila;
            Columna = (int)(Poy+20)/50;
            Columna1 = (int)(Poy+45)/50;
            break;
                
            case 2:
            //LEFT
            Fila = (int)(Pox)/50;
            Fila1 =Fila;
            Columna = (int)(Poy+20)/50;
            Columna1 = (int)(Poy+45)/50;
            break;
            
            case 3:
            //UP
            Fila = (int)(Pox+15)/50;
            Fila1= (int)(Pox+37)/50;
            Columna = (int)(Poy)/50;
            Columna1 =Columna;
            break;
            
            case 4:
            //DOWN
            Fila = (int)(Pox+15)/50;
            Fila1= (int)(Pox+37)/50;
            Columna = (int)(Poy+50)/50;
            Columna1 = Columna;
            break;
        }
        
        if((Mat[Columna][Fila]==2 || Mat[Columna1][Fila]==2) || 
           (Mat[Columna][Fila1]==2 ||Mat[Columna1][Fila1]==2) ||
                
           (Mat[Columna][Fila]==0 || Mat[Columna1][Fila]==0) || 
           (Mat[Columna][Fila1]==0 || Mat[Columna1][Fila1]==0) ||
                
           (Mat[Columna][Fila1]==4 || Mat[Columna1][Fila1]==4) ||
            (Mat[Columna][Fila]==4 || Mat[Columna1][Fila]==4)){
            
            System.out.println(Columna+","+Fila+"Cuidado!!!!!");
            
            Des=false;
        }

        return Des;
    }

    /**
     * Cargar Fotos de bomba al array para que posteriormente update pueda usar para hacer la animación.
     */
    public void loadBomb(){
        Bum.loadBomb(bombas);  
    }
    /**
     * 
     * @param start: Tiempo actual de la animacion bomba, esto para saber que Scene se debe mostrar.
     * @param i : posicion de la bomba en el array.
     * @return QUITAR...
     */
    public String LanzarBomb(long start,int i){
        return Bum.LanzarBomb(start,bombas,i);
    }
}
