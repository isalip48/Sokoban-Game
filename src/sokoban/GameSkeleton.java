package sokoban;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class GameSkeleton extends JPanel{
    // Arena Size
    final int COLS = 10, ROWS = 8;
    // Images 
    final String GRASSLAYOUT_IMAGE = "grass.png";
    final String BRICK_IMAGE = "bricks.png";
    final String CRATES_IMAGE  = "box.png";
    final String WAREHOUSEKEEPER_IMAGE = "warehousekeeper.png";
    final String DIAMOND_IMAGE = "diamond.png";
    
    // Player Location
    int WarehouseKeeperRow, WarehouseKeeperCol; 
    
    // Current Level
    int currentLevel = 01;
    
    GameCoding [][] gameArray;
    private char [][] level01 = {
                                {'w', 'w', 'w', 'w', 'w' , 'w', 'w','w','w','w'},
                                {'w', 'w', 'w', 'h', ' ', ' ',  ' ' , ' ', 'w', 'w'},
                                {'w', 'w', 'w', 'c', 'c' , ' ', ' ',' ', 'w' , 'w'},
                                {'w', 'w', 'w', ' ', ' ' , ' ', ' ',' ', 'w' , 'w'},
                                {'w', 'w', 'w', ' ', 'w' , ' ', ' ',' ', 'w' , 'w'},
                                {'w', 'w', 'w', ' ', 'w' , ' ', ' ',' ', 'w' , 'w'},
                                {'w', 'w', 'w', 'd', 'w' , 'd', ' ',' ', 'w' , 'w'},
                                {'w', 'w', 'w', 'w', 'w' , 'w', 'w','w', 'w' , 'w'}
    };
    
      private char [][] level02 = {
                                {'w', 'w', 'w', 'w', 'w' , 'w', 'w','w', 'w' , 'w'},
                                {'w', 'h', ' ', ' ', ' ' , ' ', ' ',' ', 'w' , 'w'},
                                {'w', 'c', 'c', ' ', ' ' , ' ', ' ',' ', 'w' , 'w'},
                                {'w', ' ', 'c', 'w', 'd' , 'w', ' ',' ','w' , 'w'},
                                {'w', ' ', ' ',  ' ', 'w' , 'd', 'w','w', 'w' , 'w'},
                                {'w', ' ', ' ', ' ', ' ' , ' ', ' ',' ', 'w' , 'w'},
                                {'w', 'd', ' ', ' ', ' ' , ' ', ' ',' ', 'w' , 'w'},
                                {'w','w', 'w', 'w', 'w' , 'w', 'w','w', 'w' , 'w'}
      };
      
      private char [][] level03 = {
                                {'w', 'w', 'w', 'w', 'w' , 'w', 'w','w', 'w' , 'w'},
                                {'w', 'h', 'd', 'd', ' ' , ' ', ' ',' ', ' ' , 'w'},
                                {'w', ' ', ' ', ' ', ' ' , ' ', 'w',' ', ' ' , 'w'},
                                {'w', ' ', ' ', ' ', ' ' , ' ', 'w','c', ' ' , 'w'},
                                {'w', ' ', ' ', ' ', ' ' , ' ', 'w',' ', ' ' , 'w'},
                                {'w', ' ', ' ', ' ', ' ' , ' ', 'c','c', ' ' , 'w'},
                                {'w', 'd', 'w', ' ', ' ' , ' ', ' ',' ', ' ' , 'w'},
                                {'w', 'w', 'w', 'w', 'w' , 'w', 'w','w', 'w' , 'w'}
      };   
    
    public GameSkeleton()
    {
        gameArray = new GameCoding[ROWS][COLS];
        loadlevel(currentLevel);
        repaint();
    }
    
    public void loadlevel(int level)
    {
        // load current level
        char [][] curLevel = new char [ROWS][COLS] ;
        if (level == 01)
            curLevel = level01;
        else if (level == 02)
            curLevel = level02;
        else if (level == 03)
            curLevel = level03;
        // Load all tiles
        for (int r = 0 ; r < ROWS; r++)
        {
            for (int c =0 ; c < COLS ; c++)
            {
               gameArray[r][c] = new GrassLayout(GRASSLAYOUT_IMAGE);
            }
        }
        // Load other elements
        for (int r = 0 ; r < ROWS; r++)
        {
            for (int c =0 ; c < COLS ; c++)
            {
               switch(curLevel[r][c])
               {
                   case 'd': gameArray [r][c] = new Diamond(DIAMOND_IMAGE);
                             break;
                   case 'w': gameArray [r][c].elementOnTop = new Bricks(BRICK_IMAGE);
                             break;
                   case 'c': gameArray [r][c].elementOnTop = new Crates(CRATES_IMAGE);
                             break;
                   case 'h': gameArray [r][c].elementOnTop = new WarehouseKeeper(WAREHOUSEKEEPER_IMAGE);
                             WarehouseKeeperRow = r;
                             WarehouseKeeperCol = c;
                             break;
                   default:
                       break;
               }
            }
        }
        
    }
    
    /**
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g)
    {
        
        for (int r = 0 ; r < ROWS; r++)
        {
            for (int c =0 ; c < COLS ; c++)
            {
               int xLoc = 10 + c * 30;
               int yLoc = 10 + r * 30;
               BufferedImage img;
               if (gameArray[r][c].elementOnTop == null)
                    img = gameArray[r][c].getImage();
               else
                   img = gameArray[r][c].elementOnTop.getImage();
               
               g.drawImage(img,xLoc,yLoc,30,30,this);
            }
        }
    }
    
    public void moveRight()
    {
        if (WarehouseKeeperCol +1 < COLS )
            if( gameArray [WarehouseKeeperRow] [WarehouseKeeperCol+1].elementOnTop != null)
               if (gameArray [WarehouseKeeperRow] [WarehouseKeeperCol+1].elementOnTop instanceof Crates )
               {   // It's just a crate
                   // Look into the next location
                   if (gameArray [WarehouseKeeperRow] [WarehouseKeeperCol+2].elementOnTop == null)
                   {
                       // Move crate
                       gameArray [WarehouseKeeperRow][WarehouseKeeperCol+2].elementOnTop = 
                           gameArray [WarehouseKeeperRow][WarehouseKeeperCol+1].elementOnTop;
                       gameArray [WarehouseKeeperRow][WarehouseKeeperCol+1].elementOnTop = null;
                       // Move the gamer
                       gameArray [WarehouseKeeperRow][WarehouseKeeperCol+1].elementOnTop = 
                           gameArray [WarehouseKeeperRow][WarehouseKeeperCol].elementOnTop;
                       gameArray [WarehouseKeeperRow][WarehouseKeeperCol].elementOnTop = null;
                       WarehouseKeeperCol++;
                   }
               }  
               else
               {
                  // walls stay still
               }
            else
            {
                   // Move the gamer
                   gameArray [WarehouseKeeperRow][WarehouseKeeperCol+1].elementOnTop = 
                           gameArray [WarehouseKeeperRow][WarehouseKeeperCol].elementOnTop;
                   gameArray [WarehouseKeeperRow][WarehouseKeeperCol].elementOnTop = null;
                   WarehouseKeeperCol++;
            }  
        repaint();
    }
    
    public void moveLeft()
    {
        if (WarehouseKeeperCol - 1 > 0 )
            if( gameArray [WarehouseKeeperRow] [WarehouseKeeperCol-1].elementOnTop != null)
               if (gameArray [WarehouseKeeperRow] [WarehouseKeeperCol-1].elementOnTop instanceof Crates )
               {   // It's just a crate
                   // Look into the next location
                   if (gameArray [WarehouseKeeperRow] [WarehouseKeeperCol-2].elementOnTop == null)
                   {
                       // Move the crate
                       gameArray [WarehouseKeeperRow][WarehouseKeeperCol-2].elementOnTop = 
                           gameArray [WarehouseKeeperRow][WarehouseKeeperCol-1].elementOnTop;
                       gameArray [WarehouseKeeperRow][WarehouseKeeperCol-1].elementOnTop = null;
                       // Move the gamer
                       gameArray [WarehouseKeeperRow][WarehouseKeeperCol-1].elementOnTop = 
                           gameArray [WarehouseKeeperRow][WarehouseKeeperCol].elementOnTop;
                       gameArray [WarehouseKeeperRow][WarehouseKeeperCol].elementOnTop = null;
                       WarehouseKeeperCol--;
                   }
               }  
               else
               {
                  // walls stay still
               }
            else
            {
                   // Move the gamer
                   gameArray [WarehouseKeeperRow][WarehouseKeeperCol-1].elementOnTop = 
                           gameArray [WarehouseKeeperRow][WarehouseKeeperCol].elementOnTop;
                   gameArray [WarehouseKeeperRow][WarehouseKeeperCol].elementOnTop = null;
                   WarehouseKeeperCol--;
            }  
        repaint();
    }
    
    
    public void moveDown()
    {
        if (WarehouseKeeperRow +1 < ROWS )
            if( gameArray [WarehouseKeeperRow+1] [WarehouseKeeperCol].elementOnTop != null)
               if (gameArray [WarehouseKeeperRow+1] [WarehouseKeeperCol].elementOnTop instanceof Crates )
               {   // It's just a crate
                   // Look into the next location
                   if (gameArray [WarehouseKeeperRow+2] [WarehouseKeeperCol].elementOnTop == null)
                   {
                       // Move crate
                       gameArray [WarehouseKeeperRow+2][WarehouseKeeperCol].elementOnTop = 
                           gameArray [WarehouseKeeperRow+1][WarehouseKeeperCol].elementOnTop;
                       gameArray [WarehouseKeeperRow+1][WarehouseKeeperCol].elementOnTop = null;
                       // Move the gamer
                       gameArray [WarehouseKeeperRow+1][WarehouseKeeperCol].elementOnTop = 
                           gameArray [WarehouseKeeperRow][WarehouseKeeperCol].elementOnTop;
                       gameArray [WarehouseKeeperRow][WarehouseKeeperCol].elementOnTop = null;
                       WarehouseKeeperRow++;
                   }
               }  
               else
               {
                  // walls stay still
               }
            else
            {
                   // Move the gamer
                   gameArray [WarehouseKeeperRow+1][WarehouseKeeperCol].elementOnTop = 
                           gameArray [WarehouseKeeperRow][WarehouseKeeperCol].elementOnTop;
                   gameArray [WarehouseKeeperRow][WarehouseKeeperCol].elementOnTop = null;
                   WarehouseKeeperRow++;
            }  
        repaint();
    }
    
    public void moveUp()
    {
        if (WarehouseKeeperRow -1 > 0 )
            if( gameArray [WarehouseKeeperRow-1] [WarehouseKeeperCol].elementOnTop != null)
               if (gameArray [WarehouseKeeperRow-1] [WarehouseKeeperCol].elementOnTop instanceof Crates )
               {   // It's just a crate
                   // Look into the next location
                   if (gameArray [WarehouseKeeperRow-2] [WarehouseKeeperCol].elementOnTop == null)
                   {
                       // Move crate
                       gameArray [WarehouseKeeperRow-2][WarehouseKeeperCol].elementOnTop = 
                           gameArray [WarehouseKeeperRow-1][WarehouseKeeperCol].elementOnTop;
                       gameArray [WarehouseKeeperRow-1][WarehouseKeeperCol].elementOnTop = null;
                       // Move the gamer
                       gameArray [WarehouseKeeperRow-1][WarehouseKeeperCol].elementOnTop = 
                           gameArray [WarehouseKeeperRow][WarehouseKeeperCol].elementOnTop;
                       gameArray [WarehouseKeeperRow][WarehouseKeeperCol].elementOnTop = null;
                       WarehouseKeeperRow--;
                   }
               }  
               else
               {
                  // walls stay still
               }
            else
            {
                   // Move the gamer
                   gameArray [WarehouseKeeperRow-1][WarehouseKeeperCol].elementOnTop = 
                           gameArray [WarehouseKeeperRow][WarehouseKeeperCol].elementOnTop;
                   gameArray [WarehouseKeeperRow][WarehouseKeeperCol].elementOnTop = null;
                   WarehouseKeeperRow--;
            }  
        repaint();
    }
    
    public boolean levelComplete()
    {
        boolean over = true;
        for (int r = 0 ; r < ROWS; r++)
        {
            for (int c =0 ; c < COLS ; c++)
            {
               if (gameArray[r][c] instanceof Diamond)
               {
                   if (gameArray[r][c].elementOnTop == null )
                       over = false;
                   else
                       if (!(gameArray[r][c].elementOnTop instanceof Crates))
                           over = false;
               }
            }
        }
        return over;
    }
}
   

