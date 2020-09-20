package com.akatsu.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.akatsu.main.Game;
import com.akatsu.world.Camera;

public class Entity {
	
	public static BufferedImage LIFEPACK = Game.spritesheet.getSprite(128, 0, 16, 16);
	public static BufferedImage AMMUNITION = Game.spritesheet.getSprite(128, 16, 16, 16);
	public static BufferedImage ENEMY = Game.spritesheet.getSprite(0, 144, 16, 16);
	public static BufferedImage GUN = Game.spritesheet.getSprite(144, 80, 16, 16);
	public static BufferedImage GUN_LEFT = Game.spritesheet.getSprite(128, 80, 16, 16);
	public static BufferedImage GUN_RIGHT = Game.spritesheet.getSprite(128, 64, 16, 16);
	public static BufferedImage GUN_DOWN = Game.spritesheet.getSprite(112, 64, 16, 16);
	public static BufferedImage GUN_UP = Game.spritesheet.getSprite(112, 80, 16, 16);
	
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	
	protected int mask_X, mask_Y, mask_W, mask_H;
	
	private BufferedImage sprite;
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		this.mask_X = 0;
		this.mask_Y = 0;
		this.mask_W = width;
		this.mask_H = height;
	}
	
	public void setMask(int mask_X, int mask_Y, int mask_W, int mask_H) {
		this.mask_X = mask_X;
		this.mask_Y = mask_Y;
		this.mask_W = mask_W;
		this.mask_H = mask_H;
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
	
	public static boolean collidding(Entity a, Entity b) {
		Rectangle mask_A = new Rectangle(a.getX(), a.getY(), 16, 16);
		Rectangle mask_B = new Rectangle(b.getX(), b.getY(), 16, 16);
		
		return mask_A.intersects(mask_B);
	}

}
