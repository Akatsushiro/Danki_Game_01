package com.akatsu.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.akatsu.main.Game;
import com.akatsu.world.Camera;

public class Entity {
	
	public static BufferedImage LIFEPACK = Game.spritesheet.getSprite(128, 0, 16, 16);
	public static BufferedImage AMMUNITION = Game.spritesheet.getSprite(128, 16, 16, 16);
	public static BufferedImage GUN = Game.spritesheet.getSprite(128, 64, 16, 16);
	public static BufferedImage ENEMY = Game.spritesheet.getSprite(112, 48, 16, 16); 
	
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	private BufferedImage sprite;
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
	}

	public int getX() {
		return (int) x;
	}

	public int getY() {
		return (int) y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY()- Camera.y, null);
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

}
