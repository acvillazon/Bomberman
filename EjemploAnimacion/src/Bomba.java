
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author usuario
 */
public class Bomba {
    
    public static final int UP=0;
    public static final int RIGTH=1;
    public static final int DOWN=2;
    public static final int LEFT=3;
    public static final int NONE=-1;
    public static final int BOMB=5;
    public ArrayList<Blocks> N_bombas = new ArrayList<>();

    public void loadBomb(Animation bombas[]){
        String path1="Bomba";
         bombas[0]=new Animation();
        for (int i = 1; i < 3; i++) {
            bombas[0].addScene(new ImageIcon(
                    getClass().getResource(
                            "BomberBlanco"+"//"+path1+i+".png")).getImage(), 200);
        }
    }
    
    public String LanzarBomb(long start,Animation bombas[],int i){
        if(start<=3000){
            bombas[0].update(start);
            return "0";
        }
        else
        {
            
            int X = N_bombas.get(i).Pox;
            int Y = N_bombas.get(i).Poy;
            //System.out.println("x: "+X);
            //System.out.println("y: "+Y);
            N_bombas.remove(i);
            return X+"-"+Y;
        }
        
    }
    
    public String DrawBomb(int bx, int by, int bcurrent,long start){
        int byy=(by+25)/50;
        int bxx=(bx+25)/50;
        Image p = null;
        switch(bcurrent){
            case UP:
                byy=(byy-1); 
                break;
            case DOWN:
                byy=(byy+1);
                break;
            case LEFT:
                bxx=(bxx-1);
                break;
            case RIGTH:
                bxx=(bxx+1);
                break;
        }
        Blocks b = new Blocks(bxx,byy,p,false,start);
        System.out.println("M"+start);
        N_bombas.add(b);
        return bxx+"-"+byy;
        //g.drawImage(bombas[0].getImage(), bxx*50, byy*50, 40, 40, null);
    } 
    
}
