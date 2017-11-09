class Move{
    int startX;
    int startY;
    int landingX;
    int landingY;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getLandingX() {
        return landingX;
    }

    public void setLandingX(int landingX) {
        this.landingX = landingX;
    }

    public int getLandingY() {
        return landingY;
    }

    public void setLandingY(int landingY) {
        this.landingY = landingY;
    }
}
