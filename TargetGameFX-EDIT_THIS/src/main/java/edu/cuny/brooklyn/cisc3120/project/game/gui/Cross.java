package edu.cuny.brooklyn.cisc3120.project.game.gui;

import javafx.scene.canvas.*;
import javafx.scene.paint.Color;
public class Cross {
	private double x, y;
	private double width, height;
	Cross(double width, double height){
		this.width = width;
		this.height = height;
	}
	public void setPos(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public void draw(final GraphicsContext gc){
	      // Save
    	gc.setFill(Color.WHITE);
    	gc.fillRect(0, 0, width, height);
    	gc.clearRect(0, 0, width, height);
    	gc.save();
        // Translate + rotate
//        gc.translate(x, y);
        gc.setLineWidth(1.0);
        
        gc.beginPath();// Draw lines
        gc.moveTo(x, 0);
        gc.lineTo(x, height);
        gc.moveTo(0, y);
        gc.lineTo(width, y);
        gc.stroke();
        gc.closePath();
        // Restore
        gc.restore();
	}
}
