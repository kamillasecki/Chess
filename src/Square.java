class Square{
    public int xCoor;
    public int yCoor;
    public Piece piece;
    public boolean occupied;

    public Square(int x, int y, Piece piece){
        xCoor = x;
        yCoor = y;
        this.piece = piece;
        occupied = true;
    }

    public Square(int x, int y){
        xCoor = x;
        yCoor = y;
        piece = null;
        occupied = false;
    }

    public int getXC(){
        return xCoor;
    }

    public int getYC(){
        return yCoor;
    }

    public boolean isOccupied () {
        return occupied;
    }

    public void changeOccupied() {
        this.occupied = !this.occupied;
    }

    public Piece getPiece(){
        return piece;
    }

}
