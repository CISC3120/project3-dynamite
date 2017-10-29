package edu.cuny.brooklyn.cisc3120.project.game;

import javafx.scene.layout.StackPane;


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

    public double getWidth() {
        return width;
    }
    
    public double getHeight() {
        return height;
    }
    
    public StackPane buildShootingPane() {
    	StackPane shootingPane=new StackPane();
    	
    	return shootingPane;
    	
    }

}
