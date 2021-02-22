Watch this game playthrough video to get the concept of this game:
https://youtu.be/6_bg8G2wHC4

# Project Summary

- This project is about a Java 2D game, you can use paddle to bounce off the ball to break the brick.
- It serves to let me become more familiar with Java Graphic basics.
- The thing I was trying to learn is the Java graphics and map generation.
- I was trying to improve my independent coding skills related to java graphics.
- Because I was stuck in my first project, I thought I need to bring up a new but easier project to accomplish
- I also considered doing a project like tic-tac-toe game, but I rejected it because there&#39;s no much to do with Java Graphics.
- The project is appropriately challenging for me because I just started to learn java this month, and this is not a beginner level project. But it&#39;s also not that hard to comprehend.

# Technical Summary

##
# **Main**

Create a JFrame. setResizable(): If the parameter is false then the user cannot resize the frame.

```

package brickBracker;

import javax.swing.\*;

public class Main {

public static void main(String[] args){

JFrame obj = new JFrame();

Gameplay gameplay = new Gameplay();

obj.setBounds(10, 10, 700, 600);

obj.setTitle("BALL Breaker");

obj.setResizable(false);

obj.setVisible(true);

obj.setDefaultCloseOperation(JFrame.EXIT\_ON\_CLOSE);

obj.add(gameplay);

}

}

```

##
# **Gameplay**

```

public class Gameplay extends JPanel implements KeyListener, ActionListener {

```

Add a subclass from this Jpanel class so that gameplay becomes a Jpanel. Keylistener for our keytyped, actionlistener for the ball moving. Here we can see 4 methods being automatically implemented by idea. actionPerformed is from actionListener, the rest of them are from the KeyListener.

```

@Override

public void keyPressed(KeyEvent e) {

}

}

@Override

public void keyReleased(KeyEvent e) {

}

@Override

public void actionPerformed(ActionEvent e) {

}

@Override

public void keyTyped(KeyEvent e) {

}

```

These are some variables to be initialized

```

private boolean play = false; //so that when game starts game shouldn&#39;t play immediately until we press the button

private int score = 0;

private int totalBricks = 21;

private Timer timer;

private int delay = 8;

private int playerX = 310;

private int ballposX = 120;

private int ballposY = 350;

```

## **Gameplay()**

```

public Gameplay(){ //Constructor

map = new MapGenerator(3, 7);

addKeyListener(this);

setFocusable(true);

setFocusTraversalKeysEnabled(false);

timer = new Timer(delay, this);

timer.start();

}

```

## ![](RackMultipart20210204-4-k4svip_html_c90dc6283dc2ac88.png)

The focusable flag indicates whether a component can gain the focus if it is requested to do so. The JPanel component is focusable by default, so nothing will be changed when you set it to true.

setFocusTraversalKeysEnabled() decides whether or not focus traversal keys (TAB key, SHIFT+TAB, etc.) are allowed to be used when the current Component has focus.

For Timer:

[https://docs.oracle.com/javase/tutorial/uiswing/misc/timer.html#:~:text=A%20Swing%20timer%20(an%20instance,timer%20facility%20in%20the%20java.&amp;text=For%20example%2C%20the%20tool%20tip,and%20when%20to%20hide%20it](https://docs.oracle.com/javase/tutorial/uiswing/misc/timer.html#:~:text=A%20Swing%20timer%20(an%20instance,timer%20facility%20in%20the%20java.&amp;text=For%20example%2C%20the%20tool%20tip,and%20when%20to%20hide%20it).

In short, the timer will repeat the task "this" with the interval int "delay".

## **paint()**

```

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

```

These are pretty straightforward, just created some entities.

```

g.dispose();

```

A Graphics object cannot be used after dispose has been called. ... Graphics objects which are provided as arguments to the paint and update methods of components are automatically released by the system when those methods return.

After using dispose() here, the paint() method won&#39;t be called repeatedly by timer so that the stuff created can keep moving. But we still need to redraw the paddle after we press the key.

If you miss a ball, you got this:

```

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

```

If you win, you got this:

```

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

```

## **keyPressed(keyEvent e)**

```

@Override

public void keyPressed(KeyEvent e) {

if(e.getKeyCode() == KeyEvent.VK\_RIGHT){// To detect if we press the right key

if(playerX >= 600){//To prevent player from moving outside the bound

playerX = 600;

}else{

moveRight();

}

}

if(e.getKeyCode() == KeyEvent.VK\_LEFT){// To detect if we press the left key

if(playerX <= 10){//To prevent player from moving outside the bound

playerX = 10;

}else{

moveLeft();

}

}

if(e.getKeyCode() == KeyEvent.VK\_ENTER){

if(!play){

restart();

}

}

```

I am gonna implement the moveLeft and moveRight later. Again, this is straightforward, the annotation explains most of them.

Press enter to restart the game:

```

if(e.getKeyCode() == KeyEvent.VK\_ENTER){

if(!play){

restart();

}

}

```

```

public void restart(){

play = true;

ballposX = 120;

ballposY = 350;

ballXdir = - randomNumber(1, 4);

ballYdir = - randomNumber(1, 4);

playerX = 310;

score = 0;

totalBricks = 21;

map = new MapGenerator( 3, 7);

repaint();

}

```

## **moveRight &amp; moveLeft**

```

private void moveLeft() {

play = true; //remember it was initialized as false;

playerX -= 20; // move to left for 20 pixels.

}

private void moveRight() {

play = true; //remember it was initialized as false;

playerX += 20;

}

```

## **ActionPerformed()**

```

public void actionPerformed(ActionEvent e) {

timer.start();

repaint();

```

So that when we move the paddle it will update its position.

The repaint() method is used to cause paint() to be invoked by the AWT painting thread.

About paint and repaint:

[https://stackoverflow.com/questions/10768619/paint-and-repaint-in-java](https://stackoverflow.com/questions/10768619/paint-and-repaint-in-java)

### **Ball behavior in actionPerformed()**

```

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

```

#### **Collision with paddle**

```

if(play){// play was false, so when player starts moving the ball starts moving

if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))){

ballYdir = -ballYdir;

}

```

Use intersects method to detect if they collide, and then bounce off again.

#### **Collision with Bricks!**

```

A: for(int i = 0; i<map.map.length;i++){// the first map is the class declared above

// the second map is the actual array

for(int j = 0; j<map.map[0].length;j++){

if (map.map[i][j]>0){

int brickX = j\*map.brickWidth+80;

int brickY = i\*map.brickHeight+50;

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

break A;//yep, so that we don&#39;t have to unnecessarily check all the blocks again.

```

Yes, we repaint them just to check the value right here.

This chunk of code basically just picks every block out of its place and check if they are intersected with the ball, once got the one being collided, the iteration breaks.

Don&#39;t forget this code is in the actionPerformed function, it&#39;s being called repeatedly itself all the time.

##
# **MapGenerator**

## **MapGenerator**

```

public class MapGenerator {

public int map[][];// contains all the bricks

public int brickWidth;

public int brickHeight;

public MapGenerator(int row, int col){

map = new int[row][col];

for(int i = 0; i < map.length; i++){

for (int j = 0; j < map[0].length; j++){

map[i][j] = 1;

// we draw 1 in the map to indicate that there are bricks in it. if it&#39;s 0, then the

// brick in that position is broken.

}

}

```

Used 2D array to design a map

## **Draw the brick**

```

public void draw(Graphics2D g){

//draw a brick wherever there&#39;s a position of 1

for(int i = 0; i < map.length; i++){

for (int j = 0; j < map[0].length; j++){

if(map[i][j] > 0){

g.setColor(Color.WHITE);

g.fillRect(j\*brickWidth+80, i\*brickHeight+50, brickWidth, brickHeight);

}

}

}

}

```

Difference between Graphics and Graphics2D:

[http://euler.vcsu.edu:7000/9385/](http://euler.vcsu.edu:7000/9385/)

Since we didn&#39;t draw the border, it looks like a blank flag in the screen.

So I added this:

```

g.setStroke((new BasicStroke(3)));

g.setColor(Color.BLACK);

g.drawRect(j\*brickWidth+80, i\*brickHeight+50, brickWidth, brickHeight);

```

Difference between drawRect and fillRect:

[https://docs.oracle.com/javase/8/docs/api/java/awt/Graphics2D.html#draw-java.awt.Shape-](https://docs.oracle.com/javase/8/docs/api/java/awt/Graphics2D.html#draw-java.awt.Shape-)

Basically drawrect strokes a rectangle with the pen color instead of filling it with the pen color.

Now it looks normal.

## **Change the Brick&#39;s Value function**

```

public void setBrickValue(int value, int row, int col){// so that we can change the status of some bricks

map[row][col] = value;

}

```

##
# **Updates &amp; Enhancements**

## **V1.1**

- Changed the initial direction to a random value

```

private int ballXdir = - randomNumber(1, 4);

private int ballYdir = - randomNumber(1, 4);

```

- Fixed the bug that the ball may infinitely bounce horizontally.

# Process Summary

- I basically finished this project because it&#39;s a simple functional game. I started with watching some youtube tutorials online, which are very helpful, and I follow the same logic in the first project to finish the game.
- I did get stuck when I was trying to find and fix a logic error. Logic error is the most difficult and common bug, but it turned out that my spelling was wrong because I used the auto-fill in intellij which leads to another variable. I succeeded in changing some direction values to random values, that part was kinda frustrating because the random value isn&#39;t really comfortable to use in Java. The new thing I am proud of is that I finally understand what ActionListener and Action performed really are.
- If I were to continue this project, I would design a level setting, difficulty settings and more options for paddle and ball, maybe some random awards for speed or attack range as well just like every robust brick breaker game in the market.
