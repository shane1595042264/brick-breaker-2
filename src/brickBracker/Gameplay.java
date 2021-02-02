package brickBracker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;


public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false; //so that when game starts game shouldn't play immediately until we press the button
    private int score = 0;
    private int totalBricks = 21;
    private Timer timer;
    private int delay = 8;

    private int playerX = 310;
    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;

    private MapGenerator map;


    public Gameplay(){ //Constructor
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();

    }
    public void paint(Graphics g){
        //background
        g.setColor(Color.BLACK);
        g.fillRect(1,1, 692, 592);

        //drawing map
        map.draw((Graphics2D) g);
        //borders
        g.setColor(Color.YELLOW);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);
        // no border for bottom because we need a slider to bounce the ball.

        //score
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 590, 30);
        // the paddle
        g.setColor(Color.GREEN);
        g.fillRect(playerX, 550, 100, 8);
        // the ball
        g.setColor(Color.YELLOW);
        g.fillOval(ballposX, ballposY, 20, 20);

        //win?
        if(totalBricks <=0){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("TimesRoman", Font.BOLD, 30));
            g.drawString("You won! " + score, 190, 300);

            g.setColor(Color.red);
            g.setFont(new Font("TimesRoman", Font.BOLD, 20));
            g.drawString("Press Enter to restart the game", 230, 350);
        }

        //missing ball gg
        if(ballposY > 570){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("TimesRoman", Font.BOLD, 30));
            g.drawString("Game Over! Scores: " + score, 190, 300);

            g.setColor(Color.red);
            g.setFont(new Font("TimesRoman", Font.BOLD, 20));
            g.drawString("Press Enter to restart the game", 230, 350);
        }
        g.dispose();

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){// To detect if we press the right key
            if(playerX >= 600){//To prevent player from moving outside the bound
                playerX = 600;
            }else{
                moveRight();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){// To detect if we press the left key
            if(playerX <= 10){//To prevent player from moving outside the bound
                playerX = 10;
            }else{
                moveLeft();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(!play){
                play = true;
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3, 7 );
                repaint();
            }
        }
    }

    private void moveLeft() {
        play = true; //remember it was initialized as false;
        playerX -= 20; // move to left for 20 pixels.
    }

    private void moveRight() {
        play = true; //remember it was initialized as false;
        playerX += 20;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play){// play was false, so when player starts moving the ball starts moving
            if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))){
                ballYdir =  -ballYdir;
            }
            A: for(int i = 0; i<map.map.length;i++){// the first map is the class declared above
                // the second map is the actual array
                for(int j = 0; j<map.map[0].length;j++){
                    if (map.map[i][j]>0){
                        int brickX = j*map.brickWidth+80;
                        int brickY = i*map.brickHeight+50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;
                        // these variables are declared for later construction of rectangle parameters

                        Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickRect = rect;
                        //collision of bricks happen
                        if(ballRect.intersects(brickRect)){
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;

                            if(ballposX + 19 <= brickRect.x|| ballposX +1>= brickRect.x + brickRect.width){
                                // in this situation, the ball crushes on the side of the brick, so we need to bounce
                                // off horizontally.
                                ballXdir = -ballXdir;
                            }else{
                                ballYdir = -ballYdir;
                            }
                            break A;//yep, so that we don't have to unnecessarily check all the blocks again.



                        }

                    }
                }


            }
            ballposX += ballXdir;
            ballposY += ballYdir;
            if(ballposX <= 0){
                ballXdir = -ballXdir; // so the ball bounds back
            }
            if(ballposY <= 0){
                ballYdir = -ballYdir;
            }
            if(ballposX >= 670){
                ballXdir = -ballXdir; // so the ball bounds back
            }
        }

        repaint();


    }

    @Override
    public void keyTyped(KeyEvent e) {

    }




}
