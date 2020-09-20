package com.akatsu.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.akatsu.graficos.Spritesheet;
import com.akatsu.main.Game;
import com.akatsu.world.Camera;
import com.akatsu.world.World;

public class Player extends Entity{
	
	public boolean right, left, up, down;
	public double speed = 1.5;
	
	//animation time control
	private int frames = 0;
	private int maxFrames = 10;
	private int index = 0;
	private int maxIndex = 3;
	private boolean moved = false;
	private int damageFrames = 0;
	
	
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;
	private BufferedImage[] playerDamage;
	private BufferedImage lastPosition;
	private BufferedImage lastDamagePosition;
	private BufferedImage lastGunPosition;
	
	public int life = 100;
	public static int maxLife = 100;
	public int ammo = 0;
	public static boolean isDamage = false;
	public boolean hasGun = false;
	public int xGunMask, yGunMask;
	public boolean shooting = false;
	public boolean mouseShooting = false;
	public int mouse_X, mouse_Y;
	public int shootTime = 0, shootMaxTime = 20;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		upPlayer = new BufferedImage[4];
		downPlayer = new BufferedImage[4];
		playerDamage = new BufferedImage[4]; 
		
		for(int i = 0; i < 4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(64 + (i * 16), 16, 16, 16);
			leftPlayer[i] = Game.spritesheet.getSprite(0 + (i * 16), 16, 16, 16);
			upPlayer[i] = Game.spritesheet.getSprite(0 + (i * 16), 0, 16, 16);
			downPlayer[i] = Game.spritesheet.getSprite(64 + (i * 16), 0, 16, 16);
			playerDamage[i] = Game.spritesheet.getSprite(16 + (i * 16), 48, 16, 16);
		}
		
		lastPosition = downPlayer[0];
		lastDamagePosition = playerDamage[0];
	}
	
	public void tick() {
		this.playerMoviment();
		this.isDamage();
		this.gameOver();
		this.cameraPosition();
		this.lifepackCollinding();
		this.ammoCollision();
		this.gunCollision();
		this.shoot();
		this.mouseShoot();
	}
	
	public void mouseShoot() {
		if(mouseShooting && hasGun && ammo > 0) {
			
			double angle = Math.atan2( mouse_Y - (this.getY() + 8 - Camera.y), mouse_X - (this.getX() + 8 - Camera.x));              
			double dx = Math.cos(angle);
			double dy = Math.sin(angle);
			int px = 0;
			int py = 0;
			
			if(lastPosition == leftPlayer[0]) {
				px = -6;
				py = 6;
			} else if (lastPosition == rightPlayer[0]) {
				py = 6;
				px = 21;
			}
			
			if(lastPosition == upPlayer[0]) {
				px = 6;
				py = -6;
			} else if (lastPosition == downPlayer[0]) {
				px = 8;
				py = 21;
			}
			
			shootTime++;
			if(shootTime == shootMaxTime) {
				shootTime = 0;
				Bullet bullet = new Bullet(this.getX() + px, this.getY() + py, 2, 2, null, dx, dy);
				Game.bullets.add(bullet);
				ammo--;
			}
			
			
		}
	}
	
	
	public void shoot() {
		if(shooting && hasGun && ammo > 0) {
			
			int dx = 0;
			int dy = 0;
			int px = 0;
			int py = 0;
			
			if(left) {
				dx = -1;
				py = 6;
				px = -6;
			} else if (right) {
				dx = 1;
				py = 6;
				px = 20;
			}else {
				if(lastPosition == leftPlayer[0]) {
					dx = -1;
					py = 6;
					px = -6;
				} else if (lastPosition == rightPlayer[0]) {
					dx = 1;
					py = 6;
					px = 20;
				}
			}
			
			if(up) {
				dy = -1;
				px = 6;
				py = -6;
			}else if (down) {
				dy = 1;
				px = 8;
				py = 20;
						
			}else {
				if(lastPosition == upPlayer[0]) {
					dy = -1;
					px = 6;
					py = -6;
				} else if (lastPosition == downPlayer[0]) {
					dy = 1;
					px = 8;
					py = 20;
				}
			}
			
			
			shootTime++;
			if(shootTime == shootMaxTime) {
				shootTime = 0;
				Bullet bullet = new Bullet(this.getX() + px, this.getY() + py, 2, 2, null, dx, dy);
				Game.bullets.add(bullet);
				ammo--;
			}
		}
	}
	
	public void ammoCollision() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Ammunition){
				if(Entity.collidding(this, atual)) {
					ammo += 100;
					Game.entities.remove(atual);
				}
			
			}
		}
	}
	
	public void playerMoviment() {
		moved = false;
		if(right && World.place_free((int) (x + speed), (int) y)) {
			moved = true;
			x += speed;
		} else if (left && World.place_free((int) (x - speed), (int) y)) {
			moved = true;
			x -= speed;
		}
		
		if(up && World.place_free((int) x, (int) (y - speed))) {
			moved = true;
			y -= speed;
		} else if (down && World.place_free((int) x, (int) (y + speed))) {
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
	}
	
	public void isDamage() {
		if(isDamage) {
			this.damageFrames++;
			if(this.damageFrames == 12) {
				this.damageFrames = 0;
				isDamage = false;
			}
		}
	}
	
	public void cameraPosition() {
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH / 2), 0, World.WIDTH * 16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT / 2), 0, World.HEIGHT * 16 - Game.HEIGHT);
	}
	
	
	public void gameOver() {
		if(life <= 0) {
			Game.entities = new ArrayList<Entity>();
			Game.enemies = new ArrayList<Enemy>();
			Game.spritesheet = new Spritesheet("/spritesheet.png");
			Game.player = new Player(0, 0, 16, 16, Game.spritesheet.getSprite(0, 0, 16, 16));
			Game.entities.add(Game.player);
			Game.world = new World("/mapa01.png");
		}
		return;
	}
	
	public void gunCollision() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Gun){
				if(Entity.collidding(this, atual)) {
					hasGun = true;
					ammo += 100; 
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void lifepackCollinding() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Lifepack){
				if(Entity.collidding(this, atual)) {
					life += 10;
					if(life > 100) life = 100;
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void render(Graphics g) {
		if(up) {
			g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			lastPosition = upPlayer[0];
			lastDamagePosition = playerDamage[1];
			if(hasGun) {
				g.drawImage(Entity.GUN_UP, this.getX() - Camera.x, this.getY() - (int)(this.getHeight() * 0.7) - Camera.y, null);
				xGunMask = 0;
				yGunMask = (int)(this.getHeight() * 0.7);
				lastGunPosition = Entity.GUN_UP;
			}
		}else if(down) {
			g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			lastPosition = downPlayer[0];
			lastDamagePosition = playerDamage[0];
			if(hasGun) {
				g.drawImage(Entity.GUN_DOWN, this.getX() - Camera.x, this.getY() + (int)(this.getHeight() * 0.7) - Camera.y, null);
				xGunMask = 0;
				yGunMask = -1 * (int)(this.getHeight() * 0.7);
				lastGunPosition = Entity.GUN_DOWN;
			}
		} else {
			if(right) {
				g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				lastPosition = rightPlayer[0];
				lastDamagePosition = playerDamage[3];
				if(hasGun) {
					g.drawImage(Entity.GUN_RIGHT, this.getX() + (int)(this.width * 0.7) - Camera.x, this.getY() - Camera.y, null);
					xGunMask = -1 * (int)(this.getHeight() * 0.7);
					yGunMask = 0;
					lastGunPosition = Entity.GUN_RIGHT;
				}
			}else if(left) {
				g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				lastPosition = leftPlayer[0];
				lastDamagePosition = playerDamage[2];
				if(hasGun) {
					g.drawImage(Entity.GUN_LEFT, this.getX() - (int)(this.width * 0.7) - Camera.x, this.getY() - Camera.y, null);
					xGunMask = (int)(this.getHeight() * 0.7);
					yGunMask = 0;
					lastGunPosition = Entity.GUN_LEFT;
				}
			}else {
				g.drawImage(lastPosition, this.getX() - Camera.x, this.getY() - Camera.y, null);
				if(hasGun) g.drawImage(lastGunPosition, this.getX() - xGunMask - Camera.x, this.getY() - yGunMask - Camera.y, null);
			}
		}
		
		if(isDamage) {
			g.drawImage(lastDamagePosition, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	}
}









