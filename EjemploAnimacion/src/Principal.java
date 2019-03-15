
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Principal extends JFrame{
    public Bomberman j1;
    public Thread th;
    public Canvas c;
    public Image pic;
    
    public int[][] Camp;
    public HashMap<String, Blocks> bloques = new HashMap<>(); 
    public int MaxBloques=0;
    public long start;
    public long hit_start;

    public void iniKeyListener(){
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                switch(ke.getKeyCode()){
                    case KeyEvent.VK_LEFT:{
                        j1.setDirection(Bomberman.LEFT);
                        break;
                    }
                    case KeyEvent.VK_RIGHT:{
                        j1.setDirection(Bomberman.RIGTH);
                        break;
                    }
                    case KeyEvent.VK_UP:{
                        j1.setDirection(Bomberman.UP);
                        break;
                    }
                    case KeyEvent.VK_DOWN:{
                        j1.setDirection(Bomberman.DOWN);
                        break;
                    }
                    case KeyEvent.VK_SPACE:{
                        /**
                         * hit_start: Tiempo iniciial de la bomba al ser colocada, util para saber en que momento la bomba explotara,
                         * como tambien hacerle saber al programa que animacion ejecutar.
                         * 
                         * Guardar Bombas en Array para ser dibujadas y tener control sobre ellas, tiempo, pos, explosion.
                         * 
                         * Meter Bomba en matriz
                         */
                                                
                        hit_start=System.currentTimeMillis();
                        String Pos = j1.Bum.DrawBomb(j1.bx= j1.x, j1.by= j1.y, j1.currentDirection, hit_start);
                        String[] x = Pos.split("-");
                        Camp[Integer.parseInt(x[1])][Integer.parseInt(x[0])]=4;
                        break;
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                switch(ke.getKeyCode()){
                    case KeyEvent.VK_LEFT:{
                        j1.setDirection(Bomberman.NONE);
                        break;
                    }
                    case KeyEvent.VK_RIGHT:{
                        j1.setDirection(Bomberman.NONE);
                        break;
                    }
                    case KeyEvent.VK_UP:{
                        j1.setDirection(Bomberman.NONE);
                        break;
                    }
                    case KeyEvent.VK_DOWN:{
                        j1.setDirection(Bomberman.NONE);
                        break;
                    }
                }

            }
        });
    }
    
    public Principal(int Ancho, int Alto){
        setSize(750,572);
        IniMat_Map();
        Blocks Bloq = new Blocks();
        Image picture2 = new ImageIcon(getClass().getResource("BomberBlanco//Stroke1//Stroke_1Center.png")).getImage();
        
        c=new Canvas();
//        c.addMouseListener(new MouseAdapter(){
//            
//            public void mouseClicked(MouseEvent e){
//                
//            }
//        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j1= new Bomberman(50, 50, 2, 2, "BomberBlanco");
        j1.loadPic();
        j1.loadBomb();
   
        setLayout(new BorderLayout());
        add(c,BorderLayout.CENTER);
        iniKeyListener();
        th = new Thread(new Runnable() {
            @Override
            public void run() {
                c.createBufferStrategy(2);
                start=System.currentTimeMillis();
                
                while(true){
                    try{
                        Graphics g= c.getBufferStrategy().getDrawGraphics();
                        g.setColor(Color.WHITE);
                        g.fillRect(0, 0, c.getWidth(), c.getHeight());

                        for (HashMap.Entry<String, Blocks> entry : bloques.entrySet()) {
                            
                            Bloq.drawBlocks(g, entry.getValue());
                        }

                        j1.move(System.currentTimeMillis()-start,Camp);  
                        j1.draw(g);
                        //g.drawImage(picture2, 300, 300, 50,50,c);

                            for (int i = 0; i < j1.Bum.N_bombas.size(); i++) {
                                String state = j1.LanzarBomb(System.currentTimeMillis()-j1.Bum.N_bombas.get(i).getTime(),i);

                                if(state!="0"){
                                    String Pos = state;
                                    String[] x1 = Pos.split("-");
                                    System.out.println("x: "+x1[0]);
                                    System.out.println("y: "+x1[1]);
                                    Camp[Integer.parseInt(x1[1])][Integer.parseInt(x1[0])]=1;
                                }
                                else
                                {
                                     g.drawImage(j1.bombas[0].getImage(), j1.Bum.N_bombas.get(i).Pox*50, j1.Bum.N_bombas.get(i).Poy*50, 40,40 ,null);
                                }
                            }
                         
                        c.getBufferStrategy().show();
                        Thread.sleep(10);
                    }catch(Exception e){}
                }
            }
        });        
    }
    
    public static void main(String[] args) {
        Principal p = new Principal(15,11);
        p.setVisible(true);
        p.th.start();
    }

    /**
     * INICIALIZA MATRIZ (CAMPO)
     * CREA, DIBUJA MAPA
     * CREA BLOQUES RANDOM
     * PREPARA EL CAMPO PARA JUGAR.
     */
    public void IniMat_Map(){
        Camp = new int[11][15];
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 15; j++) {
                Camp[i][j]=-1;
            }
        }
        B_Up();
        B_Lat();
        B_Rest();
        B_Inter();
        
        Camp[1][1]=9;
        Camp[1][2]=9;
        Camp[1][3]=9;
        Camp[2][1]=9;
        Camp[3][1]=9;

        while(MaxBloques<=40){
          B_Destroy();   
        }
        
         for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 15; j++) {
                System.out.print(" "+Camp[i][j]+" ");
            }
            System.out.println("");
            System.out.println("");
        }
    }
    
    
    public void AgregarBlocks(int i, int j, String path, int Type){
        Image picture = new ImageIcon(getClass().getResource("BomberBlanco//"+path)).getImage();
        Blocks Bloq = new Blocks(i,j,picture,false,0);
        Camp[i][j]=Type;
        bloques.put(i+"-"+j, Bloq);
    }
    
    public void B_Up(){
        for (int j = 0; j < 15; j++) {
           AgregarBlocks(0,j,"Map_Suelo3.png",0);  
           AgregarBlocks(10,j,"Map_Suelo3.png",0);  
        }
    }
    
    public void B_Lat(){
        for (int i = 1; i < 10; i++) {
           AgregarBlocks(i,0,"Map_Suelo3.png",0);  
           AgregarBlocks(i,14,"Map_Suelo3.png",0);  
        }
    }
    
    public void B_Inter(){
        for(int i=2; i<10; i+=2){ 
            for(int j=2; j<14; j+=2){
                AgregarBlocks(i,j,"Map_Suelo3.png",0); 
            }
        }
    }
    
     public void B_Rest(){
        for(int i=1; i<10; i++){ 
            for(int j=1; j<14; j++){
                AgregarBlocks(i,j,"Map_Suelo.png",1); 
            }
        }
    }
    
    public void B_Destroy(){
        Random rnd = new Random();
        int Rj = (int)(rnd.nextDouble()*14+1);
        int Ri = (int)(rnd.nextDouble()*10+1);
        System.out.println(Camp[Ri][Rj]);
        if(Camp[Ri][Rj]!=0 && Camp[Ri][Rj]!=2 && Camp[Ri][Rj]!=9){
          System.out.println("->"+Camp[Ri][Rj]);
          AgregarBlocks(Ri,Rj,"Bloque.png",2); 
          MaxBloques++;
        }
    }
}

/**
 * Bloque UP,LAT, Inter: 0
 * Bloque Campo : 1
 * Bloque Destroy: 2
 * Bloque Bomba : 4
 * Casillas No disponibles: 9.
 */