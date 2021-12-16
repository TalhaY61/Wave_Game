package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class MenuParticle extends GameObject {

    private Handler handler;
    Random r = new Random();
    private Color clr;
   
    public MenuParticle (int x, int y, ID id, Handler handler) {
        super(x,y,id);
        this.handler = handler;
    
        //Velocity (Vel) = Geschwindigkeit
        //random geschwindigkeit
        velX = (r.nextInt(7 - -7) + -7);
        velY = (r.nextInt(7 - -7) + -7);

        if (velX == 0) velX = 1;
        if (velY == 0) velY = 1;
        
        //(red,green,blue) with random variable
        clr = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
  
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 16, 16);
    }

    public void tick() {
        x += velX;
        y += velY;

        if ( y <= 0 || y >= Game.HEIGHT - 32) velY *= -1;
        if ( x <= 0 || x >= Game.WIDTH - 16) velX *= -1;
        
        
        handler.addObject(new Trail(x, y, ID.Trail, clr, 16, 16, 0.05f, handler));
    }

    public void render(Graphics g) {
        g.setColor(clr);
        g.fillRect(x,y,16,16);
    }

    
    
}
 