package com.akatsu.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.akatsu.main.Game;
import com.akatsu.world.Camera;
import com.akatsu.world.World;

public class Enemy extends Entity{
	
	private double speed = 0.8;
	
	int maskX = 8, maskY = 8, maskH = 10, maskW = 10;
	
	private BufferedImage[] sprites = new BufferedImage[4];
	
	//animation time control
		private int frames = 0;
		private int maxFrames = 10;
		private int index = 0;
		private int maxIndex = 3;
		
		private int life = 3;

	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		sprites[0] = Game.spritesheet.getSprite(0, 144, 16, 16);
		sprites[1] = Game.spritesheet.getSprite(16, 144, 16, 16);
		sprites[2] = Game.spritesheet.getSprite(32, 144, 16, 16);
		sprites[3] = Game.spritesheet.getSprite(48, 144, 16, 16);
	}

	public void tick() {
		if(!playerColliding()) {
			if((int) x < Game.player.getX()
					&& World.place_free((int) (x + speed), this.getY())
					&& !collinding((int) (x + speed), this.getY())) {
				x += speed;
			}else if ((int) x > Game.player.getX() 
					&& World.place_free((int) (x - speed), this.getY())
					&& !collinding((int) (x - speed), this.getY())) {
				x -= speed;
			}
			
			if((int) y < Game.player.getY() 
					&& World.place_free(this.getX(), (int) (y + speed))
					&& !collinding(this.getX(), (int) (y + speed))) {
				y += speed;
			}else if ((int) y > Game.player.getY() 
					&& World.place_free(this.getX(), (int) (y - speed))
					&& !collinding(this.getX(), (int) (y - speed))) {
				y -= speed;
			}
		} else {
			if(Game.rand.nextInt(100) < 10) {
				Game.player.life--;
				Player.isDamage = true; 
			}
		}
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index > maxIndex) {
				index = 0;
				}
			}
		
		this.enemyDeath();
		this.collidingBullet();
	}
	
	public void enemyDeath() {
		if(life <= 0) {
			Game.entities.remove(this);
		}
	}
	
	public void collidingBullet() {
		for(int i = 0; i < Game.bullets.size(); i++) {
			Entity atual = Game.bullets.get(i);
			if(Entity.collidding(this, atual)) {
				this.life--;
				Game.bullets.remove(i);
				return;
			}
		}
	}
	
	public boolean playerColliding() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskX, this.getY(), maskW, maskW);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 16);
		return enemyCurrent.intersects(player);
	}
	
	public boolean collinding(int nextX, int nextY) {
		Rectangle currentEnemy = new Rectangle(nextX + maskX, nextY +maskY, maskW, maskH);
		for (int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if(e == this) continue;
			Rectangle targetEnemy = new Rectangle(e.getX() + maskX, e.getY() + maskY, maskW, maskH);
			if(currentEnemy.intersects(targetEnemy)) return true;
		}
		return false;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
}
