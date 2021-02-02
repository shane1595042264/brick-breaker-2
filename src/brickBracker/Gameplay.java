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
        // the paddle
        g.setColor(Color.GREEN);
        g.fillRect(playerX, 550, 100, 8);
        // the ball
        g.setColor(Color.YELLOW);
        g.fillOval(ballposX, ballposY, 20, 20);

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
