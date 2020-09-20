package com.akatsu.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.akatsu.main.Game;
import com.akatsu.world.Camera;

public class Bullet  extends Entity{

	private double dx;
	private double dy;
	private double spd = 4;
	private int range = 15;
	private int rangeEnd = 0;
	
	public Bullet(int x, int y, int width, int height, BufferedImage sprite, double dx, double dy) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
		
	}
	
	public void tick() {
		x += dx * spd;
		y += dy * spd;	
	}
	
	
	
	public void range() {
		rangeEnd++;
		if(rangeEnd == range) {
			Game.bullets.remove(this);
			return;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect(this.getX() - Camera.x, this.getY() - Camera.y, width, height);
	}
}
