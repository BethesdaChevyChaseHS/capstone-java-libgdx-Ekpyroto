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
        grid[size/2][size/2]=Piece.BLACK;
        grid[size/2-1][size/2-1]=Piece.BLACK;
        grid[size/2-1][size/2]=Piece.WHITE;
        grid[size/2][size/2-1]=Piece.WHITE;
    }

    public boolean move(int x, int y, Piece piece){
        if(grid[x][y]!=Piece.EMPTY){
            return false;
        }
        boolean hasMoved = false;

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
                    if(curY>=size ||curX>=size ||curY<0 ||curX<0){
                        System.out.println("ur code is overflowing dummy");
                        ongoing=false;
                        continue;
                    }

                    if(grid[curX][curY]==piece.opposite()){
                        numChecked++;
                    }
                    if(grid[curX][curY]==piece){
                        if(numChecked==0){
                            ongoing=false;
                            continue;
                        }
                        //if we have found a piece of the same type, we can flip the pieces
                        hasMoved = true;
                        ongoing=false;
                        for(int i=0; i<numChecked; i++){
                            curX-=dx;
                            curY-=dy;
                            grid[curX][curY]=piece;

                        }
                    }
                    if(grid[curX][curY]==Piece.EMPTY){
                        ongoing=false;
                        continue;
                    }
                }
            }
        }
        if(hasMoved){
            grid[x][y] = piece;
            return true;
        }
        return false;
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

    public Piece getPiece(int x, int y){
        return grid[x][y];
    }
    
    // Checks if placing 'piece' at (x, y) would be a legal move, without modifying the board
    private boolean isLegalMove(int x, int y, Piece piece) {
        if (grid[x][y] != Piece.EMPTY) return false;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                int curX = x + dx;
                int curY = y + dy;
                int numChecked = 0;
                while (curX >= 0 && curX < size && curY >= 0 && curY < size && grid[curX][curY] == piece.opposite()) {
                    curX += dx;
                    curY += dy;
                    numChecked++;
                }
                if (numChecked > 0 && curX >= 0 && curX < size && curY >= 0 && curY < size && grid[curX][curY] == piece) {
                    return true;
                }
            }
        }
        return false;
    }

    // Returns true if there is at least one legal move for 'piece'
    public boolean hasLegalMove(Piece piece) {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (isLegalMove(x, y, piece)) {
                    return true;
                }
            }
        }
        return false;
    }
}
