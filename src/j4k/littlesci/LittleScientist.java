package j4k.littlesci;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * 
 * This needs an additional PNG to run.
 * Get the complete resources (and the build script) at: http//marwane.kalam-alami.net/4k/tls-4k-sources.zip
 * 
 */
@SuppressWarnings("serial")
public class LittleScientist extends Applet implements Runnable {

    final static int EARTH_OFFSET = 250;
    final static int EARTH_SLOWNESS = 20000;
    final static int STARS_SLOWNESS = -40000;
    final static byte TILE_COUNT = 30;
    final static byte WORLD_COUNT = 4;
    final static double TILE_TO_RAD = Math.PI * 2 / TILE_COUNT;
    
    final static int MAN = -1;
    final static int TREE = 1;
    final static int MINE = 100;
    final static int REACTOR = 200;
    final static int LABORATORY = 300;
    final static int ROCK = 400;
    final static int PILLAR = 500;
    final static int ROCKET = 600;
    final static int BROKEN_ROCKET = 900;
    
    final static byte CHAR_SPRITE_HEIGHT = 14;
    final static float PLAYER_BRAKING = 0.6F;
    final static float PLAYER_ANIMATION_SPEED = 0.2F;
    final static float PLAYER_ACCELERATION = 0.008F;
    final static int[][] ITEM_PROPS = new int[][] {
      /* { Needed wood, needed stone, Needed uranium, Item ID, Item scale } */
      new int[]{ 2, 0, 0, TREE, 2}, // Tree
      new int[]{ 10, 0, 0, MINE, 4}, 
      new int[]{ 20, 50, 0, REACTOR, 5},
      new int[]{ 30, 15, 0, LABORATORY, 5},
      new int[]{ 15, 99, 50, ROCKET, 6}
    };
    final static int[] ITEM_SPRITES_POS = new int[] { 19, 22, 27, 31, 35, 40 };

    static long t;
    static byte gameState = 0; // 0 = Starting, 1 = Playing, 2 = Rocket taking off, 3 = Switching world, 4 = End
    static int target = -1; // -1 = No target, i = Target tile
    static int selectedItem = 0;
    static boolean rightClick = false;
    static int currentWorld = 0;
    
    public void run() {

        // Graphics init
        BufferedImage sprites;
        try {
            sprites = ImageIO.read(LittleScientist.class.getResource("s"));
        } catch (Exception e) {
            return;
        }
        BufferedImage buffer = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
        Graphics2D appletGraphics = (Graphics2D) getGraphics();
        Font smallFont = appletGraphics.getFont().deriveFont((float)10);
        Font bigFont = smallFont.deriveFont((float)16).deriveFont(Font.BOLD);
        
        Random r = new Random();
        byte[] stars = new byte[400];
        int[][] explosions = new int[TILE_COUNT * WORLD_COUNT][2]; // { animation frame (1 to 5), color }
        int[][] worlds = new int[][] {
              new int[] { 130, 0x443311, 1, 1 },
              new int[] { 170, 0x444444, 0, 1 },
              new int[] { 160, 0x334411, 0, 1 },
              new int[] { 150, 0x114455, 0, 1 }
        }; // { planet radius, color, discovered, labo. bonus }
        
        while (true) {

            /*
             * --------------------
             * Game init
             * -------------------- 
             */
            
            // Background init
            r.nextBytes(stars);
            
            // Planets contents
            int[] items = new int[TILE_COUNT * WORLD_COUNT];
            for (int i = 0; i < 10; i++) { 
                items[r.nextInt(TILE_COUNT)] = r.nextInt(50);
            }
            for (int i = 0; i < TILE_COUNT; i++) {
                items[i + TILE_COUNT] = ROCK + r.nextInt(50); // 2nd planet's rocks
            }
            for (int i = 0; i < TILE_COUNT; i++) {
                items[i + 2 * TILE_COUNT] = PILLAR + r.nextInt(50); // 3rd planet's pillars
            }
            items[TILE_COUNT + 5] = 10; // Joker tree
            int earthSize = worlds[0][0];
            currentWorld = 0;
            
            // Player variables
            double position = 0;
            double animation = 0;
            double speed = 0;
            int[] resources = new int[3];
           // resources = new int[] { 1000, 1000, 1000 }; // XXX Debug
            int inRocket = -1;
            int foundWorlds = 1;
            selectedItem = 0;

            // Game variables
            long score = System.currentTimeMillis();
            gameState = 1;
            int itemToUpdate;
            
            // Game loop
            while (gameState > 0) {
            
            Graphics2D g = (Graphics2D) buffer.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            t = System.currentTimeMillis();

            /*
             * --------------------
             * World switch
             * -------------------- 
             */
            if (gameState == 3) {
            	// Change world
                currentWorld++;
                earthSize = worlds[currentWorld][0];
                
                // Update rocket state
            	items[inRocket] = 0;
            	inRocket = ((inRocket + TILE_COUNT / 2) % TILE_COUNT) + currentWorld * TILE_COUNT;
            	items[inRocket] = 999;
            	position = inRocket * TILE_TO_RAD + .2;
            	
            	// Update game state
            	if (worlds[currentWorld][2] == 0) {
            	    foundWorlds++;
            	}
            	worlds[currentWorld][2] = 1;
            	gameState = 1;
            }
            
            /*
             * --------------------
             * Game update
             * -------------------- 
             */
            
            // Update items (from all worlds)
            while ((itemToUpdate = r.nextInt(TILE_COUNT * WORLD_COUNT + 50)) < TILE_COUNT * WORLD_COUNT) {
            	// Valid to 1 -> 49 (trees), 200 -> 249 (reactors)
	            if (items[itemToUpdate] > 0
	            		&& items[itemToUpdate] % 200 >= 0
	            		&& items[itemToUpdate] % 200 < 50
	            		&& items[itemToUpdate] < ROCK) {
	            	items[itemToUpdate] += worlds[currentWorld][3];
	            }
            }
            
            if (inRocket == -1) {
                
                // Character behavior
                if (target != -1) {
                    int currentTile = (int) (Math.round(position / TILE_TO_RAD) % TILE_COUNT) + TILE_COUNT * currentWorld;
                    if (target == currentTile) {
                        target = -1;
                        speed /= 2;
                        if (rightClick) {
                            if (items[currentTile] == 0) { // Buy
                                int i;
                                if (resources[0] >= ITEM_PROPS[selectedItem][0]
                                 && resources[1] >= ITEM_PROPS[selectedItem][1]
                                 && resources[2] >= ITEM_PROPS[selectedItem][2]) {
                                    for (i = 0; i < 3; i++) {
                                        resources[i] -= ITEM_PROPS[selectedItem][i];
                                    }
                                    items[currentTile] = ITEM_PROPS[selectedItem][3];
                                    if (selectedItem == 3 && worlds[currentWorld][3] < 16) { // Laboratory
                                    	worlds[currentWorld][3] *= 2;
                                    }
                                    explosions[currentTile][0] = 4;
                                    explosions[currentTile][1] = 0x77FFFFFF;
                                }
                            }
                        }
                        else { // Interact
                        	if (items[currentTile] >= BROKEN_ROCKET) {
                                // Nothing
                        	}
                        	else if (items[currentTile] >= ROCKET) {
                                inRocket = currentTile;
                                gameState = 2;
                            }
                            else if (items[currentTile] >= PILLAR) {
                                items[currentTile] -= 5;
                                resources[2]++;
                                if (r.nextDouble() > .7 && earthSize > 110) {
                                    earthSize--;
                                }
                                if (items[currentTile] < PILLAR) {
                                    items[currentTile] = 0;
                                }
                            }
                            else if (items[currentTile] >= ROCK) {
                                items[currentTile] -= 5;
                                resources[1]++;
                                if (items[currentTile] < ROCK) {
                                    items[currentTile] = 0;
                                }
                            }
                            else if (items[currentTile] >= LABORATORY) {
                            	// Nothing
                            }
                            else if (items[currentTile] >= REACTOR) {
                                resources[2] += (items[currentTile] % 100) / 3;
                                items[currentTile] = REACTOR;
                            }
                            else if (items[currentTile] >= MINE) {
                                resources[1] += (items[currentTile] % 100) / 3;
                                items[currentTile] = MINE;
                            }
                            else if (items[currentTile] >= TREE) { 
                                resources[0] += items[currentTile] / 5;
                                items[currentTile] = 0;
                                explosions[currentTile][0] = 1;
                                explosions[currentTile][1] = 0xAA55FF22;
                            }
                        }
                    }
                    else if ((target < currentTile && currentTile - target < TILE_COUNT / 2)
                          || (target > currentTile && target - currentTile > TILE_COUNT / 2)) {
                        speed -= PLAYER_ACCELERATION;
                    }
                    else {
                        speed += PLAYER_ACCELERATION;
                    }
                }
                    
                // Update character
                position += speed;
                position = (position + 2 * Math.PI) % (2 * Math.PI);
                speed *= PLAYER_BRAKING;
                if (Math.abs(speed) > PLAYER_ACCELERATION) {
                    animation += PLAYER_ANIMATION_SPEED;
                }
                else {
                    animation = 1;
                }
            }
            
			// Update rocket
			if (gameState == 2) {
				// Rocket take off
				items[inRocket]++;
				if (items[inRocket] < 900) {
					if (items[inRocket] > ROCKET + 100) {
						gameState = 3;
					}
					if (items[inRocket] > ROCKET + 50) {
						earthSize--;
					}
				}
			}
			else if (inRocket != -1) {
        		if (items[inRocket] > 930) {
    				// Rocket fall
        			items[inRocket] -= 3;
        		}
        		else {
        			// Rocket explosion
        			for (int i = -3; i < 3; i++) {
						explosions[((inRocket + TILE_COUNT - i) % TILE_COUNT + TILE_COUNT * currentWorld)][0] = 1;
						explosions[((inRocket + TILE_COUNT - i) % TILE_COUNT + TILE_COUNT * currentWorld)][1] = 0xFFFFFF00;
        			}
        			inRocket = -1;
        		}
        	}
            
			// Game end
            if (foundWorlds == WORLD_COUNT) {
                gameState = 4;
            }
			
            /*
             * --------------------
             * Rendering
             * -------------------- 
             */
            
            // Background
            g.setColor(new Color(0x223355));
            g.fillRect(0, 0, 500, 500);
            g.translate(EARTH_OFFSET, EARTH_OFFSET - 30);
            
            // Stars
            g.rotate((double) t/STARS_SLOWNESS);
            g.setColor(new Color(0xFFFF88));
            for (int i = 0; i < stars.length-2; i++) {
                g.rotate(stars[i+1]);
                g.fillOval(0, 3 * Math.abs(stars[i]), stars[i] % 4, stars[i] % 4);
                g.rotate(- stars[i+1]);
            }
            g.rotate((double) -t/STARS_SLOWNESS);

            
            g.rotate((double) t/EARTH_SLOWNESS);

            // Earth
            g.setPaint(new GradientPaint(-earthSize, -20, new Color(worlds[currentWorld][1]).brighter().brighter(),
                    earthSize, earthSize, new Color(0xDDCCAA)));
            g.fillOval(-earthSize, -earthSize, earthSize*2, earthSize*2);
            
            // Earth : Target feedback
            g.setColor(new Color(0xAAFFFFFF, true));
            if (target != -1) {
            	g.rotate(target * TILE_TO_RAD);
                g.fillOval(-6, earthSize - 16, 12, 12);
                g.rotate(- target * TILE_TO_RAD);
            }
            
            // Earth : Items
            for (int i = TILE_COUNT * currentWorld; i < TILE_COUNT * (currentWorld + 1); i++) {
                if (items[i] > 0) {
                    g.translate(0, earthSize);
                    int spriteId = items[i] / 100;
                    boolean special = false;
                    if (spriteId == 4 || spriteId == 5) {
                        spriteId -= 3;
                        special = true;
                    }
                    if (spriteId >= 6) {
                        spriteId = 4;
                    }
                    int y = (spriteId == 4) ? items[i] - ROCKET : -1;
                    double scale = ITEM_PROPS[spriteId][4];
                    if (special) {
                        scale = (items[i] % 100) / 10 + 1;
                    }
                    if (spriteId == 0) { // Mine & tree growth
                        scale += (double) (items[i] % 100) / 12;
                    }
                    if (spriteId != 4 && earthSize < 100) {
                    	scale /= 1 + (100 - earthSize) / 7;
                    }
                    int x = (int) (scale * (ITEM_SPRITES_POS[spriteId + 1] - ITEM_SPRITES_POS[spriteId]) / 2);
                    if (items[i] > BROKEN_ROCKET) {
                    	scale *= -1;
                    	y = items[i] - BROKEN_ROCKET;
                    }
                    g.drawImage(sprites, -x, y, x, y + (int)(7 * scale),
                            ITEM_SPRITES_POS[spriteId] + ((special) ? 1 : 0), 14,
                            ITEM_SPRITES_POS[spriteId + 1] + ((special) ? -1 : 0), 7, this);
                    if (items[i] > ROCKET) { // Rocket animation
                        for (int j = 10; j < 15; j++) {
                            g.setColor(new Color(r.nextInt(), true));
                            g.fillOval(r.nextInt(10) - 10, items[i] - ROCKET - r.nextInt(15) - 10, j, j);
                        }
                    }
                    int localData = items[i] % 100;
                    if (items[i] >= MINE && items[i] < LABORATORY && scale > 2) {
                    	if (items[i] >= REACTOR) {
                    		if (localData < 50) {
	                    		// Reactor anim
	                    		g.setColor(new Color(0x448844));
	                    		for (int j = 0; j < worlds[currentWorld][3] * worlds[currentWorld][3]; j++) {
	                    			int d = r.nextInt(40 + worlds[currentWorld][3] * 2);
	                    			double theta  = (r.nextDouble() * .8) * Math.PI + 1.8;
	                    			g.rotate(theta);
	                    			g.drawLine(0, 0, 0, d);
	                    			g.rotate(-theta);
	                    		}
	                    	}
                    		g.setColor(new Color(0x55FF33));
                    	}
                    	else {
                    		if (localData < 50) {
	                    		// Mine anim
                        		g.setColor(new Color(0x444444));
	                			int saw = (int) ((t/(100/worlds[currentWorld][3]) + i*70) % 100); // 0 -> 100, 0 -> 100...
	                    		if (saw < worlds[currentWorld][3]) {
	                    			// Update mine
	                    			items[i] += 3;
	                    		}
	                			int bounce = 10 + ((saw > 50) ? 100 - saw : saw); // 10 -> 60 -> 10 -> 60... 
	                			g.fillRect(-1, -bounce, 1, bounce);
	                    		if (saw > 50) {
	                    			g.fillOval(-4, -bounce-3, 8, 8);
	                    		}
                    		}
                    		g.setColor(new Color(0x222222));
                    	}
                    	g.fillOval(-localData/8, -localData/8 - 10,
                    			localData/4, localData/4);
                    	
                    }
                    g.translate(0, -earthSize);
                }
                else if (items[i] <= MAN) {
                    g.drawImage(sprites, 0, earthSize + 28, 10, earthSize, 0, 0, 5, 14, this);
                }
                
                // Particle explosions
            	if (explosions[i][0] > 0) {
                    g.translate(0, earthSize + 20);
            		g.setColor(new Color(explosions[i][1], true));
            		while (r.nextInt(10) > 0) {
            			int d = r.nextInt(15);
            			double theta = r.nextDouble() * Math.PI * 2;
            			g.rotate(theta);
	            		g.fillOval(0, d, 2, 2);
	            		g.rotate(-theta);
            		}
            		explosions[i][0]++;
            		if (explosions[i][0] > 5) {
            			explosions[i][0] = 0;
            		}
                    g.translate(0, - earthSize - 20);
            	}
                
                g.rotate(TILE_TO_RAD);
            }
            
            // Earth : Character
            g.setColor(Color.white);
            if (inRocket == -1) {
                g.rotate(position);
                if (gameState == 1) {
                    g.translate(0, earthSize);
                    int spriteX = ((int) animation % 2 == 1) ? 0 : 5 + 7 * ((int) animation % 4) / 2;
                    int spriteWidth = ((int) animation % 2 == 1) ? 5 : 7;
                    int spriteAlgWidth = (speed < 0) ? -spriteWidth : spriteWidth;
                    g.drawImage(sprites, -spriteAlgWidth, 0, spriteAlgWidth, CHAR_SPRITE_HEIGHT * 2,
                            spriteX + spriteWidth, CHAR_SPRITE_HEIGHT, spriteX, 0, this);
                    g.translate(0, -earthSize);
                }
                g.rotate(-position);
            }
            
            g.rotate((double) -t/EARTH_SLOWNESS);
            g.translate(- EARTH_OFFSET, - EARTH_OFFSET + 30);

            // HUD : Resources
            g.setFont(bigFont);
            g.setColor(Color.white);
            for (int i = 0; i < resources.length; i++) {
                g.drawImage(sprites, 
                        20, i*30 + 371,
                        41, i*30 + 391,
                        19 + i * 7, 0, 19 + (i + 1) * 7, 7, this);
                g.drawString(String.valueOf(resources[i]), 48, i*30 + 386);
            }
            
            // HUD : Items
            g.setFont(smallFont);
            for (int i = 0; i < 5; i++) {
                g.setColor(new Color(0x55FFFFFF, true));
                g.fillRoundRect(190 + i*60, 440, 55, 40, 10, 10);
                if (selectedItem == i) {
                    g.setColor(Color.white);
                    g.drawRoundRect(190 + i*60, 440, 55, 40, 10, 10);
                    g.fillRoundRect(190 + i*60, 440, 30, 40, 10, 10);
                }
                int offset = 0;
                for (int j = 0; j < 3; j++) {
                    if (ITEM_PROPS[i][j] > 0) {
                        g.drawImage(sprites, 223 + i*60, 444 + offset, 230 + i*60, 451 + offset,
                                19 + j * 7, 0, 19 + (j + 1) * 7, 7, this);
                        g.setColor(Color.white);
                        g.drawString(String.valueOf(ITEM_PROPS[i][j]), 230 + i*60, 451 + offset);
                        offset += 12;
                    }
                }
                g.drawImage(sprites, 195 + i*60, 447, 215 + i*60, 475,
                        ITEM_SPRITES_POS[i], 7, ITEM_SPRITES_POS[i+1], 14, this);
            }
            
            /*
             * --------------------
             * Game end
             * -------------------- 
             */
            if (gameState == 4) {
            	if (score > 100000) {
            		score = (System.currentTimeMillis() - score) / 1000;
            		for (int i = 0; i < WORLD_COUNT * TILE_COUNT; i++) {
            			if (i != inRocket) {
            				items[i] = r.nextInt(5) - 2;
            			}
            		}
            	}
                g.setColor(new Color(0xCCFFFFFF, true));
                g.fillRoundRect(160, 190, 175, 70, 10, 10);
                g.setColor(new Color(0x443322));
                g.setFont(bigFont.deriveFont(0));
                g.drawString("You win!", 170, 210);
                g.drawString("Right click to restart", 170, 250);
                g.setFont(bigFont);
                g.drawString("Time (s): ", 170, 230);
                g.drawString(String.valueOf(score), 260, 230);
            }
            

            // HUD : Planets
            g.translate(15, 480);
            for (int i = 0; i < WORLD_COUNT; i++) {
                g.translate(20, 0);
                if (worlds[i][2] > 0) {
                    g.setPaint(new GradientPaint(-10, -10, new Color(worlds[i][1]),
                            10, 10, new Color(0xDDCCAA)));
                }
                else {
                    g.setColor(Color.gray);
                }
                g.fillOval(-15, -15, 15, 15);
            }
            // no need for g.translate(-...), since these are the last Graphics calls
            
            appletGraphics.drawImage(buffer, 0, 0, this);
            do {
                Thread.yield();
            } while (System.currentTimeMillis() - t < 16);
            if (!isActive()) {
                return;
            }
            
            }
        }
    }

    @Override
    public boolean handleEvent(Event evt) {
        if (evt.id == Event.MOUSE_DRAG || evt.id == Event.MOUSE_DOWN) {
	        if (gameState == 1) { 
                if (evt.y > 410) {
                    if (evt.x > 170 && evt.x < 480) {
                        selectedItem = (evt.x - 175) / 65;
                    }
                }
                else {
                    rightClick = (evt.modifiers != 0);
                    evt.x -= EARTH_OFFSET;
                    evt.y -= 220;
                    if (evt.x != 0 && evt.y != 0) {
                        double targetTheta = (Math.PI * 200000001.5 - (double) t/EARTH_SLOWNESS + 2 * Math.atan(evt.y / (evt.x + Math.sqrt(evt.y * evt.y + evt.x * evt.x)))) % (2 * Math.PI);
                        target = (int) (Math.round(targetTheta / TILE_TO_RAD) % TILE_COUNT) + TILE_COUNT * currentWorld;
                    }
                }
	        }
	        else if (gameState == 4 && evt.modifiers != 0) {
	            gameState = 0;
	        }
        }
    	
        return false;
    }
    
    @Override
    public void start() {
        new Thread(this).start();
    }
    
}