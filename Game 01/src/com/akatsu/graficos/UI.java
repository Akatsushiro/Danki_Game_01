package com.akatsu.graficos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.akatsu.entities.Player;
import com.akatsu.main.Game;

public class UI {
	
	public BufferedImage foto;
	
	public void render(Graphics g) {
		
		foto = Game.spritesheet.getSprite(144, 144, 16, 16); 
		
		//foto do personagem
		g.setColor(new Color(80, 80, 80, 160));
		g.fillRect(5, 2, 20, 20);
		
		g.setColor(new Color(50, 50, 50, 160));
		g.fillRect(6, 3, 18, 18);
		
		g.drawImage(foto, 7, 3, null);
		
		//barra de vida
		g.setColor(new Color(90, 90, 90, 180));
		g.fillRect(25, 5, Player.maxLife + 2, 10);
		
		g.setColor(new Color(50, 50, 50, 180));
		g.fillRect(26, 6, Player.maxLife, 8);
		
		g.setColor(new Color(0, 225, 0, 200));
		g.fillRect(26, 6, Game.player.life, 8);
	}
}
