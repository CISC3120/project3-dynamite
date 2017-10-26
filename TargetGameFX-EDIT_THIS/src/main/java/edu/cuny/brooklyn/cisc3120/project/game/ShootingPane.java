package edu.cuny.brooklyn.cisc3120.project.game;

public class ShootingPane {
	int width, height;
	int[][] paneCells;
	
	public ShootingPane(int height, int width) {
		this.width=width;
		this.height=height;
		
		
		paneCells= new int[height][width];
		for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                paneCells[i][j] = ' ';
            }
        }
		
	}
	
	public int[][] getpane() {
        return paneCells;
    }

    public int getCell(int x, int y) {
        return paneCells[y][x];
    }

    public void setCell(int x, int y, int target) {
        paneCells[y][x] = target;
    }

    public void plotBorder() {
        for (int i = 0; i < width; i++) {
            paneCells[0][i] = '-';
            paneCells[height - 1][i] = '-';
        }
        for (int i = 0; i < height; i++) {
            paneCells[i][0] = '|';
            paneCells[i][width - 1] = '|';
        }
    }

    public void writeText(int x, int y, String text) {
        if (x >= width || y < 0 || y >= height) {
            logger.debug("Text is outside of the displaying area.");
            return;
        }
        for (int i = 0; i < text.length(); i++) {
            if (x + i < 0) {
                logger.debug("Character " + text.charAt(i) + " in the text is outside of the displaying area.");
            } else {
                paneCells[y][x + i] = text.charAt(i);
            }
        }
    }

    public double getWidth() {
        return width;
    }
    
    public double getHeight() {
        return height;
    }

}
