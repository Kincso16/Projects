public class Model {
    private int score;
    private int bestScore;
    private int size;
    private int index;
    private int width;
    private int height;
    private boolean ok;

    public Model(int size, int bestScore, int index, int width, int height) {
        this.score = 0;
        this.size = size;
        this.bestScore = bestScore;
        this.index = index;
        this.width = width;
        this.height = height;
        this.ok = true;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getBestScore() {
        return bestScore;
    }

    public int getSize() {
        return size;
    }

    public int getIndex() {
        return index;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
}
