package bcc.capstone;

public class Board {
    private Piece[][] grid;
    private final int size;


    public Board(int size){
        this.size = size;
        grid = new Piece[size][size];
        reset();
    }

    public void reset() {
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                grid[i][j]=Piece.EMPTY;
            }
        }
    }

    public boolean move(int x, int y, Piece piece){
        if(grid[x][y]!=Piece.EMPTY){
            return false;
        }
        grid[x][y]=piece;
        
        
        for(int dx = -1; dx<=1; dx++){
            for(int dy = -1; dy<=1; dy++){
                if(dx==0&&dy==0){
                    continue;
                }
                int numChecked = 0;
                boolean ongoing = true;
                int curX=x;
                int curY=y;

                while(ongoing){
                    curX+=dx;
                    curY+=dy;
                    if(grid[curX][curY]==piece){
                        numChecked++;

                    if(grid[curX][curY]==piece.opposite()){
                        ongoing=false;
                        for(int i=0; i<numChecked; i++){
                            curX-=dx;
                        curY-=dy;
                            grid[curX][curY]=piece;
                        }
                    }
                    if(grid[curX][curY]==piece.EMPTY){
                        ongoing=false;
                    }
                    }
                }
            }
        }
        return true;
    }
    
    
    public int checkScore(Piece type){
        int count = 0;
        for(int row = 0; row<size; row++){
            for(int col = 0; col<size; col++){
                if(grid[row][col]==type){
                    count++;
                }
            }
        }
        return count;

    }

    public int getSize(){
            return this.size; 
        }
    

}
