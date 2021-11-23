// @author Karla Jacquelin Guzman Sanchez

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JPanel;

public class Chamfer extends JPanel {
    
    private float mtx[][];
    private float[][] window = {  {1.4f, 1 ,1.4f}, {1,0,1}, {1.4f,1,1.4f} };
    private final int c = 25, xI, yI, d;
    
    public Chamfer(int w, int h, int d){
        this.setBounds(new Rectangle(w*20+30,h*20+50));
        
        this.d = d;
        mtx = new float[w][h];
        
        for(int i = 0; i < mtx.length ; i++){
            for(int j = 0; j < mtx[0].length ; j++){
                mtx[i][j] = Float.MAX_VALUE;
            }
        }
        
        for(int i = 0; i < 50; i++){
            mtx[(int) (Math.random()*(mtx.length - 1))][(int) (Math.random()*(mtx[0].length - 1))] = -1;
        }
        
        xI = (int) (Math.random()*(mtx.length - 1)); yI = (int) (Math.random()*(mtx[0].length - 1));
        mtx[xI][yI] = 0;

    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponents(g);
        g.setColor(Color.BLACK);
        g.setFont( new Font( "Tahoma", Font.LAYOUT_LEFT_TO_RIGHT, 10 ) );
        
        for(int i = 0; i < mtx.length ; i++){
            for(int j = 0; j < mtx[0].length ; j++){
                g.drawRect(i*c, j*c, c, c);
                
                if(mtx[i][j] == -1)
                    g.fillRect(i*c, j*c, c, c);
                
                if(i == xI && j == yI){
                    g.setColor(Color.red);
                    g.fillRect(i*c, j*c, c, c);
                    g.setColor(Color.BLACK);
                }
            }
        }
        
        g.drawString("pI", xI*c+5, yI*c+(3*c/6));
        
        if(d == 0){
            boolean found = false;
            for(int i = 0 ; i < mtx.length ; i++){
                for(int j = 0 ; j < mtx[0].length ; j++){
                    if( i == xI && j == yI) found = true;

                    if(found){
                        if(mtx[i][j] != -1) firstPass(i, j, g);
                    }
                }
            }

            for(int i = mtx.length - 1 ; i >= 0 ; i--){
                for(int j = mtx[0].length - 1 ; j >= 0 ; j--){
                    if( i == xI && j == yI) found = true;

                    if(found){
                        if(mtx[i][j] != -1) secondPass(i, j, g);
                    }
                }
            }
        }else{
            
            boolean found = false;
            for(int i = 0 ; i < mtx.length ; i++){
                for(int j = 0 ; j < mtx[0].length ; j++){
                    if( i == xI && j == yI) found = true;

                    if(found){
                        if(mtx[i][j] != -1) firstPass(i , j, g);
                    }
                }
                
                for(int j = mtx[0].length -1 ; j >= 0 ; j--){
                    if(found){
                        if(mtx[i][j] != -1) mLeft(i , j, g);
                    }
                }
            }

            for(int i = mtx.length - 1 ; i >= 0 ; i--){
                for(int j = mtx[0].length -1 ; j >= 0 ; j--){
                    if(mtx[i][j] != -1) secondPass(i , j, g);
                }
                for(int j = 0 ; j < mtx[0].length ; j++){
                    if(mtx[i][j] != -1) firstPass(i , j, g);
                }
            }
        }
        //Cuadricula
        for(int i = 0 ; i < mtx.length ; i++){
            for(int j = 0 ; j < mtx[0].length ; j++){
                g.drawRect(i*c, j*c, c, c);
            }
        }
    }
    
    private void firstPass(int x, int y, Graphics g){
        mRight(x,y,g);
        //mRightDown(x,y,g);
        mDown(x,y,g);
        //mLeftDown(x,y,g);
    }
    
    private void secondPass(int x, int y, Graphics g){
        mLeft(x,y,g);
        //mLeftUp(x,y,g);
        mUp(x,y,g);
        //mRightUp(x,y,g);
    }
    
    private void mRight(int x, int y, Graphics g){
        
        if( x+1 < mtx.length && y < mtx[0].length && x >= 0 && y >= 0){
            if(mtx[x+1][y] != -1){
//                float dist = Math.max(Math.abs((x+1)-x), Math.abs(y-y));
                if(mtx[x+1][y] > window[1][2] + mtx[x][y] && mtx[x][y] != Float.MAX_VALUE){
                    mtx[x+1][y] = window[1][2] + mtx[x][y];
                    g.setColor(Color.WHITE);
                    g.fillRect((x+1)*c, (y)*c, c, c);
                    g.setColor(Color.BLACK);
                    g.drawString(String.format("%.1f", mtx[x+1][y]), (x+1)*c+c/6, (y)*c+(3*c/6));
                }
            }
        }
    }
    
    private void mRightUp(int x, int y, Graphics g){
        
        if( x+1 < mtx.length && x >= 0 && y-1 >= 0 && y < mtx[0].length){
            if(mtx[x+1][y-1] != -1){
                //float dist = Math.max(Math.abs((x+1)-x), Math.abs((y-1)-y));
                if(mtx[x+1][y-1] > window[0][2] + mtx[x][y] && mtx[x][y] != Float.MAX_VALUE){
                    mtx[x+1][y-1] = window[0][2] + mtx[x][y];
                    g.setColor(Color.WHITE);
                    g.fillRect((x+1)*c, (y-1)*c, c, c);
                    g.setColor(Color.BLACK);
                    g.drawString(String.format("%.1f", mtx[x+1][y-1]), (x+1)*c+c/6, (y-1)*c+(3*c/6));
                }
            }
        }
    }
    
    private void mRightDown(int x, int y, Graphics g){
        
        if( x+1 < mtx.length && y+1 < mtx[0].length && x >= 0 && y >= 0){
            if(mtx[x+1][y+1] != -1){
//                float dist = Math.max(Math.abs((x+1)-x), Math.abs((y+1)-y));
                if(mtx[x+1][y+1] > window[2][2] + mtx[x][y] && mtx[x][y] != Float.MAX_VALUE){
                    mtx[x+1][y+1] = window[2][2] + mtx[x][y];
                    g.setColor(Color.WHITE);
                    g.fillRect((x+1)*c, (y+1)*c, c, c);
                    g.setColor(Color.BLACK);
                    g.drawString(String.format("%.1f", mtx[x+1][y+1]), (x+1)*c+c/6, (y+1)*c+(3*c/6));
                }
            }
        }
    }
    
    private void mDown(int x, int y, Graphics g){
        
        if( y+1 < mtx[0].length && x < mtx.length && x >= 0 && y >= 0){
            if(mtx[x][y+1] != -1){
//                float dist = Math.max(Math.abs(x-x), Math.abs((y+1)-y));
                if(mtx[x][y+1] > window[2][1] + mtx[x][y] && mtx[x][y] != Float.MAX_VALUE){
                    mtx[x][y+1] = window[2][1] + mtx[x][y];
                    g.setColor(Color.WHITE);
                    g.fillRect((x)*c, (y+1)*c, c, c);
                    g.setColor(Color.BLACK);
                    g.drawString(String.format("%.1f", mtx[x][y+1]), (x)*c+c/6, (y+1)*c+(3*c/6));
                }
            }
        }
    }
    
    private void mLeft(int x, int y, Graphics g){
        
        if(x-1 >= 0 && x < mtx[0].length && y < mtx[0].length && y >= 0){
            if(mtx[x-1][y] != -1){
//                float dist = Math.max(Math.abs((x-1)-x), Math.abs(y-y));
                if(mtx[x-1][y] > window[1][0] + mtx[x][y] && mtx[x][y] != Float.MAX_VALUE){
                    mtx[x-1][y] = window[1][0] + mtx[x][y];
                    g.setColor(Color.WHITE);
                    g.fillRect((x-1)*c, (y)*c, c, c);
                    g.setColor(Color.BLACK);
                    g.drawString(String.format("%.1f", mtx[x-1][y]), (x-1)*c+c/6, (y)*c+(3*c/6));
                }
            }
        }
    }
    
    private void mLeftUp(int x, int y, Graphics g){
        
        if(x-1 >= 0 && x < mtx[0].length && y < mtx[0].length && y-1 >= 0){
            if(mtx[x-1][y-1] != -1){
//                float dist = Math.max(Math.abs((x-1)-x), Math.abs((y-1)-y));
                if(mtx[x-1][y-1] > window[0][1] + mtx[x][y] && mtx[x][y] != Float.MAX_VALUE){
                    mtx[x-1][y-1] = window[0][1] + mtx[x][y];
                    g.setColor(Color.WHITE);
                    g.fillRect((x-1)*c, (y-1)*c, c, c);
                    g.setColor(Color.BLACK);
                    g.drawString(String.format("%.1f", mtx[x-1][y-1]), (x-1)*c+c/6, (y-1)*c+(3*c/6));
                }
            }
        }
    }
    
    private void mLeftDown(int x, int y, Graphics g){
        
        if(x-1 >= 0 && x < mtx[0].length && y+1 < mtx[0].length && y >= 0){
            if(mtx[x-1][y+1] != -1){
//                float dist = Math.max(Math.abs((x-1)-x), Math.abs((y+1)-y));
                if(mtx[x-1][y+1] > window[2][0] + mtx[x][y] && mtx[x][y] != Float.MAX_VALUE){
                    mtx[x-1][y+1] = window[2][0] + mtx[x][y];
                    g.setColor(Color.WHITE);
                    g.fillRect((x-1)*c, (y+1)*c, c, c);
                    g.setColor(Color.BLACK);
                    g.drawString(String.format("%.1f", mtx[x-1][y+1]), (x-1)*c+c/6, (y+1)*c+(3*c/6));
                }
            }
        }
    }
    
    private void mUp(int x, int y, Graphics g){
        
        if(y-1 >= 0 && y < mtx[0].length && x < mtx.length && x >= 0){
            if(mtx[x][y-1] != -1){
//                float dist = Math.max(Math.abs((x)-x), Math.abs((y-1)-y));
                if(mtx[x][y-1] > window[0][1] + mtx[x][y] && mtx[x][y] != Float.MAX_VALUE){
                    mtx[x][y-1] = window[0][1] + mtx[x][y];
                    g.setColor(Color.WHITE);
                    g.fillRect((x)*c, (y-1)*c, c, c);
                    g.setColor(Color.BLACK);
                    g.drawString(String.format("%.1f", mtx[x][y-1]), (x)*c+c/6, (y-1)*c+(3*c/6));
                }
            }
        }
    }
}
