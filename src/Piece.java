class Piece {
    private String colour;
    private String type;
    private int value;

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    Piece(String type, String colour) {
        this.colour = colour;
        this.type = type;
        switch (type) {
            case "Pawn":
                value = 1;
                break;
            case "Rook":
                value = 5;
                break;
            case "Bishup":
                value = 3;
                break;
            case "King":
                value = 9;
                break;
            case "Queen":
                value = 8;
                break;
            case "Knight":
                value = 3;
                break;
        }
    }


}
