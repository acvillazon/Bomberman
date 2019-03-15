
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author AndresVillazon
 */
public class Blocks {
    
    public int Pox;
    public int Poy;
    public long time=0;
    public boolean Actived;
    Image  Path;

    public Blocks() {
    }
    
    

    public Blocks(int Pox, int Poy, Image Path, boolean Actived, long time) {
        this.Pox = Pox;
        this.Poy = Poy;
        this.Path = Path;
        this.Actived=Actived;
        this.time=time;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
   
    public void drawBlocks(Graphics g, Blocks b){
        g.drawImage(b.Path, 50*b.Poy, 50*b.Pox,50,50, null);
    }

    public int getPox() {
        return Pox;
    }

    public void setPox(int Pox) {
        this.Pox = Pox;
    }

    public int getPoy() {
        return Poy;
    }

    public void setPoy(int Poy) {
        this.Poy = Poy;
    }

    public boolean isActived() {
        return Actived;
    }

    public void setActived(boolean Actived) {
        this.Actived = Actived;
    }

    public Image getPath() {
        return Path;
    }

    public void setPath(Image Path) {
        this.Path = Path;
    }
    
    
}
