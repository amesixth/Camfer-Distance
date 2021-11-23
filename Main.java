// @author Karla Jacquelin Guzman Sanchez

import java.awt.Rectangle;
import javax.swing.JFrame;

public class Main extends JFrame {
    
    public Main(int w, int h, int m) throws InterruptedException{
        this.setBounds(new Rectangle(w*25+30,h*25+50));
        Chamfer c = new Chamfer(w,h,m);
        
        this.add(c);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }

    public static void main(String[] args) throws InterruptedException {
        Main w = new Main(25,25,0);
    }

}
