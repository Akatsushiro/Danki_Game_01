package com.akatsu.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.akatsu.entities.Ammunition;
import com.akatsu.entities.Enemy;
import com.akatsu.entities.Entity;
import com.akatsu.entities.Gun;
import com.akatsu.entities.Lifepack;
import com.akatsu.main.Game;

public class World {
	
	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 16;
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			for(int x = 0; x < map.getWidth(); x++) {
				for (int y = 0; y < map.getHeight(); y++) {
					tiles[x + y * WIDTH] = new FloorTile(x * 16, y * 16, Tile.TILE_FLOOR);
					switch (pixels[x + (y * map.getWidth())]){
						case 0xFF000000:
							tiles[x + y * WIDTH] = new FloorTile(x * 16, y * 16, Tile.TILE_FLOOR);
							break;
						case 0xFFFFFFFF:
							tiles[x + y * WIDTH] = new WallTile(x * 16, y * 16, Tile.TILE_WALL);
							break;
						case 0xFF00fcff:
							//player
							Game.player.setX(x * 16);
							Game.player.setY(y * 16);
							tiles[x + y * WIDTH] = new FloorTile(x * 16, y * 16, Tile.TILE_FLOOR);
							break;
						case 0xFF0b7000:
							//lifepack
							Game.entities.add(new Lifepack(x * 16, y * 16, 16, 16, Entity.LIFEPACK));
							break;
						case 0xFF0c00ff:
							//Ammunition
							Game.entities.add(new Ammunition(x * 16, y * 16, 16, 16, Entity.AMMUNITION));
							break;
						case 0xFF18ff00:
							//Gun
							Game.entities.add(new Gun(x * 16, y * 16, 16, 16, Entity.GUN));
							break;
						case 0xFF7a0ea5:
							//Enemy roxo
							Game.entities.add(new Enemy(x * 16, y * 16, 16, 16, Entity.ENEMY));
							break;
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean place_free(int xNext, int yNext) {
		int x1 = xNext / TILE_SIZE;
		int y1 = yNext / TILE_SIZE;
		
		int x2 = (xNext + TILE_SIZE - 1) / TILE_SIZE;
		int y2 = yNext / TILE_SIZE;
		
		int x3 = xNext / TILE_SIZE;
		int y3 = (yNext + TILE_SIZE - 1) / TILE_SIZE;
		
		int x4 = (xNext + TILE_SIZE - 1) / TILE_SIZE;
		int y4 = (yNext + TILE_SIZE - 1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile) ||
				 (tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile) ||
				 (tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile) ||
				 (tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile));
	}
	
	
	public void render(Graphics g) {
		int xStart = Camera.x >> 4 ;
		int yStart = Camera.y >> 4;
		
		int xFinal = xStart + (Game.WIDTH >> 4) + 1;
		int yFinal = yStart + (Game.HEIGHT >> 4) + 1;
		
		for (int x = xStart; x <= xFinal; x++) {
			for (int y = yStart; y <= yFinal; y++) {
				if(x  < 0 || y < 0 || x >= WIDTH || y >= HEIGHT) continue;
				Tile tile = tiles[x + (y * WIDTH)];
				tile.render(g);
			}
		}
	}
	
}
