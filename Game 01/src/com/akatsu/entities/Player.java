package com.akatsu.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.akatsu.main.Game;
import com.akatsu.world.Camera;

public class Player extends Entity{
	
	public boolean right, left, up, down;
	public double speed = 1.5;
	
	//animation time control
	private int frames = 0;
	private int maxFrames = 10;
	private int index = 0;
	private int maxIndex = 3;
	private boolean moved = false;
	
	
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;
	private BufferedImage lastPosition;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		rightPlayer = new BufferedImage[4];
		leftPlayer  = new BufferedImage[4];
		upPlayer    = new BufferedImage[4];
		downPlayer  = new BufferedImage[4];
		
		for(int i = 0; i < 4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(64 + (i * 16), 16, 16, 16);
			leftPlayer[i]  = Game.spritesheet.getSprite(0 + (i * 16), 16, 16, 16);
			upPlayer[i]    = Game.spritesheet.getSprite(0 + (i * 16), 0, 16, 16);
			downPlayer[i]  = Game.spritesheet.getSprite(64 + (i * 16), 0, 16, 16);
		}
		
		lastPosition = downPlayer[0];
	}
	
	public void tick() {
		moved = false;
		if(right) {
			moved = true;
			x += speed;
		} else if (left) {
			moved = true;
			x -= speed;
		}
		
		if(up) {
			moved = true;
			y -= speed;
		} else if (down) {
			moved = true;
			y += speed;
		}
		
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex) {
					index = 0;
				}
			}
		}
		
		Camera.x = this.getX() - (Game.WIDTH / 2);
		Camera.y = this.getY() - (Game.HEIGHT / 2);
		
	}
	
	public void render(Graphics g) {
		if(up) {
			g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			lastPosition = upPlayer[0];
		}else if(down) {
			g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			lastPosition = downPlayer[0];
		} else {
			if(right) {
				g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				lastPosition = rightPlayer[0];
			}else if(left) {
				g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				lastPosition = leftPlayer[0];
			}else {
				g.drawImage(lastPosition, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		}
	}
}
