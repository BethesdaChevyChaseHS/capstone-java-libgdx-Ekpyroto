package bcc.capstone;

public enum Piece {
    BLACK, WHITE, EMPTY;

    static{
        BLACK.opposite = WHITE;
        WHITE.opposite = BLACK;
        EMPTY.opposite = EMPTY;
    }

    public Piece opposite(){
        return opposite; 
    }


    /* 
    public Piece oppositePiece(Piece piece){
        switch(piece){
            case BLACK: return WHITE;

            case WHITE: return BLACK;

            default: return EMPTY;
        }
    } */

}
