//WELCOME TO MY FIRST EVER WAVE GAME 

package com.tutorial.main;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1550691097823471818L;
    public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;

    private Thread thread;
    private boolean running = false;

    public static boolean paused = false;
    public int diff = 0;

    //0 = normal
    //1 = hard

    private Handler handler;
    private Random r;
    private HUD hud;
    private Spawn spawner;
    private Menu menu;



    public static STATE gameState = STATE.Menu;

    public Game() {

        handler = new Handler();
        hud = new HUD();
        menu = new Menu(this, handler, hud);
        spawner = new Spawn(handler, hud, this);
        //1 random box
        r = new Random();
        
        this.addKeyListener(new KeyInput(handler, this));
        this.addMouseListener(menu);
        

        new Window(WIDTH, HEIGHT, "Let's Build a Game!", this);

        if (gameState == STATE.Game) {
            handler.addObject(new Player(WIDTH/2-32,HEIGHT/2-32, ID.Player, handler));
            handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH -50), r.nextInt(Game.HEIGHT -50), ID.BasicEnemy, handler));
        } else {
            for (int i= 0; i < 20; i++) {
                handler.addObject(new MenuParticle(r.nextInt(WIDTH),r.nextInt(HEIGHT), ID.MenuParticle, handler));
            }
        }
        
        /*50 random boxes werden erschaffen, die beliebig wo starten.
        for(int i = 0; i < 50; i++) {
            handler.addObject(new Player(r.nextInt(WIDTH),r.nextInt(HEIGHT), ID.Player) );
        }
        */
    }

    public synchronized void start() {
        thread = new Thread(this);  // "this" means the "Game" Class
        thread.start();
        running = true;
        
    }
    public synchronized void stop() {
        try{
            thread.join();
            running = false;

        } catch(Exception e) {
            e.printStackTrace();   //Shows the Error
        }
    }

    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60;                         //sets the ticks per frame, Game will run on 60fps
        double ns = 1000000000 / amountOfTicks;            //nano seconds
        double delta = 0;                                  //delta is the time since the game last updated
        long timer = System.currentTimeMillis();          //the current time in Milliseconds
        //int frames = 0;      //frames is the number of frames per seconds you're actually getting
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){                          //While the time between each frame is less than
                tick();                                 // one tick and minus one from the delta
                delta--;
            }
            if(running)                                //if the game is running: render images, add one to frames
                render();
            //frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                //System.out.println("FPS: " + frames);
                //frames = 0;
            }
            //if the gap between when the timer variable was  set and now is less than 1000 Milliseconds:
            // Add 1000 to timer, and print the frames, then set frames to 0 so it can be calculated again.
        }
        stop();
    }

    private void tick() {
        
        if (gameState == STATE.Game) {
            if (!(paused)) {
                
                
                hud.tick();
                spawner.tick();
                handler.tick();

                if (HUD.HEALTH <= 0) {
                    HUD.HEALTH = 100;
                    gameState = STATE.End;
                    handler.clearEnemys();
                    for (int i= 0; i < 20; i++) {
                    handler.addObject(new MenuParticle(r.nextInt(WIDTH),r.nextInt(HEIGHT), ID.MenuParticle, handler));
                    }
                }  
            } 
        } else if (gameState == STATE.Menu || gameState == STATE.End || gameState == STATE.Select || gameState == STATE.Help) {
            menu.tick();
            handler.tick();
        }  
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);                      //Color of the Screen
        g.fillRect(0,0, WIDTH, HEIGHT);          //zeichnet einen gefülltes Rechteck startpunkt (x,y)

        handler.render(g);

        if (paused) {
            g.setColor(Color.white);
            g.drawString("PAUSED", 100, 100);
        }
        
        if (gameState == STATE.Game) {
            hud.render(g);
        } else if (gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.End || gameState == STATE.Select) {
            menu.render(g);
        } 

        g.dispose();                                      //the JFrame window to be destroyed and cleaned up by the operating system
        bs.show();
    }

    public static int clamp(int var, int min, int max) {
        if (var >= max) 
            return var = max;
        else if (var <= min)
            return var = min;
        else   
            return var;
    }

    public static void main(String[] args) {
        new Game();
    }
}

