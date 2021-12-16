package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Player extends GameObject {

    Random r = new Random();
    Handler handler;

    public Player(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }
    public void tick() {
        x += velX;
        y += velY;
        //Spieler kann das feld nicht verlassen
        x = Game.clamp(x, 0, Game.WIDTH - 48);
        y = Game.clamp(y, 0, Game.HEIGHT - 71);

        handler.addObject(new Trail(x, y, ID.Trail, Color.white, 32, 32, 0.05f, handler));

        collision();
    }

    public void collision() {
        for (int i = 0; i < handler.object.size(); i++) { //running through all objects in the game (for loop)
            
            GameObject tempObject = handler.object.get(i); //her elemana bakiyor 
           
            if (tempObject.getID() == ID.BasicEnemy || tempObject.getID() == ID.FastEnemy || tempObject.getID() == ID.SmartEnemy) { //wenn das obj ist gleich enemy 
                if (getBounds().intersects(tempObject.getBounds())) {   //wenn du getroffen wirst 
                    //collision code
                    HUD.HEALTH -= 2;   //mach health minus 2 
                }
            }
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.white); 
        // if (id == ID.Player2) g.setColor(Color.blue);
        g.fillRect(x,y,32,32);
    }

    

}
