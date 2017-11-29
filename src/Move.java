class Move{
    private int startX;
    private int startY;
    private int landingX;
    private int landingY;
    private String name;

    public String getName() {
        return name;
    }

    public Move(int startX, int startY, int landingX, int landingY, String name) {
        this.startX = startX;
        this.startY = startY;
        this.landingX = landingX;
        this.landingY = landingY;
        this.name = name;
    }

    public int getStartX() {
        return startX;
    }
    public int getStartY() {
        return startY;
    }
    public int getLandingX() {
        return landingX;
    }
    public int getLandingY() {
        return landingY;
    }

}
