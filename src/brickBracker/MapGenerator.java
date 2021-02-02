package brickBracker;

import java.awt.*;

public class MapGenerator {
    public int map[][];// contains all the bricks
    public int brickWidth;
    public int brickHeight;
    public MapGenerator(int row, int col){
        map = new int[row][col];
        for(int i = 0; i < map.length; i++){
            for (int j = 0; j < map[0].length; j++){
                map[i][j] = 1;
                // we draw 1 in the map to indicate that there are bricks in it. if it's 0, then the
                // brick in that position is broken.
            }
        }
        brickHeight = 150/row;
        brickWidth = 540/col;
    }
    public void draw(Graphics2D g){
        //draw a brick wherever there's a position of 1
        for(int i = 0; i < map.length; i++){
            for (int j = 0; j < map[0].length; j++){
                if(map[i][j] > 0){
                    g.setColor(Color.WHITE);
                    g.fillRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);
                }
            }
        }
    }
}
