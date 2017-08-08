   /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.beingZero.MineSweeper1;

import java.util.Random;

/**
 *
 * @author karthik-pc
 */
public class Model1 {

    Square1[][] board;
    int rows;
    int cols;
    int mines;
    int state;
    int remainingNonMines;
    public final static int OVER = 0;
    public final static int INPROGRESS = 1;
    public final static int WON = 2;

    public Model1(int r, int c, int mc) {
        rows = r;
        cols = c;
        mines = mc;
        state = INPROGRESS;
         board = new Square1[rows][cols];
         remainingNonMines=r*c-mc;

            initializeBoard();
            updateNeighbourCount();

    }

    private void initializeBoard() {
        //initilaze array
        board = new Square1[rows][cols];
        //place mines
        Random rnd = new Random();
        for (int i = 1; i <= mines;) {
            int r = rnd.nextInt(rows);
            int c = rnd.nextInt(cols);

            if (board[r][c] == null) {
                board[r][c] = new Square1(r, c, true);
                i++;
            } 
        
            else {
                System.out.printf("duplicate--%d %d", r, c);
            }
        }
        

        //place non-mines
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == null) 
                    board[i][j] = new Square1(i, j, false);
                    if (board[i][j].isMine) 
                    
                        System.out.print("* ");
                     
                    else
                    
                        System.out.print("- ");
                    
          }
            System.out.println();
        }

    }

    public int click(int i, int j) {
        int r, c;
        Square1 cs = board[i][j];
        
        if(cs.isFlagged){
            state = INPROGRESS;
            return INPROGRESS;
        }

        if (!cs.isOpen) 
        {
            if (cs.isMine) 
            {
                state = OVER;
                return OVER;
            }
               
            cs.isOpen = true;
            remainingNonMines--;
            
            if(remainingNonMines==0){
             state = WON;
             return WON;
            }
           
            if (cs.neighbourCount == 0) 
            {
                for (int a = -1; a <= 1; a++) 
                {
                    for (int b = -1; b <= 1; b++) 
                    {
                        r = a + i;
                        c = b + j;
                        if (r >= 0 && r < rows && c >= 0 && c < cols && !board[r][c].isMine) 
                       
                            click(r,c);
                        
                    }
                }

            }
        }
        state = INPROGRESS;
        return INPROGRESS;
    }

    

    public void toggleFlag(int i, int j) {

        if (!board[i][j].isOpen) {
         /*   if(board[i][j].isFlagged=true)
                board[i][j].isFlagged=false;
            else
                board[i][j].isFlagged=true;*/
            
            board[i][j].isFlagged = !board[i][j].isFlagged;
            
            
        }
    }

    public boolean isFlagged(int i, int j) {
        return board[i][j].isFlagged;
    }
    
    public boolean isOpen(int i, int j) {
        return board[i][j].isOpen;
    } 
    
    public int newNeighbourCount(int i, int j) {
        return board[i][j].neighbourCount;
    }
        

    private void placeMines() {

    }

    private void updateNeighbourCount() {
        //initialize neighbour count
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j].neighbourCount = getNeighbourCount(i, j);
                System.out.print(" " + board[i][j].neighbourCount);
            }
            System.out.println();
        }
    }

    private int getNeighbourCount(int i, int j) {

        int nc = 0;
        int r, c;
        
       for (int a =-1; a <= 1; a++) 
       {
                    for (int b = -1; b <= 1; b++) 
                    {
                        r = a + i;
                        c = b + j;
                        if (r >= 0 && r < rows && c >= 0 && c < cols && board[r][c].isMine)  
                    nc++;
                
                    }
        }

        return nc;
    }

    boolean isMine(int i, int j) {
        return board[i][j].isMine;
    }
    
    int getState()
    {
        return state;
    }

    private class Square1 {

        int rows;
        int cols, neighbourCount;

        boolean isOpen;
        boolean isFlagged;
        boolean isMine;

        public Square1(int r, int c, boolean isMine) {
            rows = r;
            cols = c;
            this.isMine = isMine;
            this.isFlagged = false;
            this.neighbourCount = 0;
            this.isOpen = false;

           
        }

    }

}
