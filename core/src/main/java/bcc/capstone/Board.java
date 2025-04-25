package bcc.capstone;

public class Board {
    private Piece[][] grid;
    private int size;

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
        return true;
    }
}
