import java.io.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class MazeProgram extends JPanel implements KeyListener{
    JFrame frame;
    int x, y, x2, y2, finishx, finishy;
    int dim = 15;
    String[][] maze;
    Timer timer;
    int dir; // 0 = up, 1 = right, 2 = down, 3 = left
    int dist = 50;
    ArrayList<Wall> walls;
    boolean in3D = false;
    BufferedImage mario;
    BufferedImage[] goomba = new BufferedImage[5];
    boolean gameOver = false;
    Clip groundThemeClip;
    boolean lose = false;
    boolean win = false;

    public MazeProgram() {
        frame = new JFrame("Maze Program");
        frame.add(this);
        frame.setSize(1200, 880);
        frame.addKeyListener(this);
        x = 1;
        y = 1;
        x2 = 1;
        y2 = 1;
        dir = 0;
        try{
            mario = ImageIO.read(new File("mario.png"));
            for(int i=0; i<5; i++)
                goomba[i] = ImageIO.read(new File("goomba.png"));
        } catch(IOException e){}
        for(int i=0; i<5; i++)
            goomba[i] = resize(goomba[i], 400-75*i, 400-75*i);
        setMaze();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public BufferedImage resize(BufferedImage image, int width, int height){
        Image temp = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage scaledVersion = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = scaledVersion.createGraphics();
        g2.drawImage(temp, 0, 0, null);
        g2.dispose();
        return scaledVersion;
    }

    public void setMaze() {
        try {
            File file = new File("maze.txt");
            BufferedReader input = new BufferedReader(new FileReader(file));
            String st;
            int row = 0;
            maze = new String[41][];
            while ((st = input.readLine())!= null){
                String[] rowOfWalls = st.split("");
                maze[row] = rowOfWalls;
                if(st.indexOf("N") >= 0){
                    dir = 0;
                    x = st.indexOf("N");
                    y = row;
                    maze[y][x] = " ";
                }
                if(st.indexOf("E") >= 0){
                    dir = 1;
                    x = st.indexOf("E");
                    y = row;
                    maze[y][x] = " ";
                }
                if(st.indexOf("W") >= 0){
                    dir = 3;
                    x = st.indexOf("W");
                    y = row;
                    maze[y][x] = " ";
                }
                if(st.indexOf("S") >= 0){
                    dir = 2;
                    x = st.indexOf("S");
                    y = row;
                    maze[y][x] = " ";
                }
                if(st.indexOf("G") >= 0){
                    x2 = st.indexOf("G");
                    y2 = row;
                    maze[y2][x2] = " ";
                }
                if(st.indexOf("H") >= 0){
                    finishx = st.indexOf("H");
                    finishy = row;
                    maze[y][x] = "H";
                }
                row++;
            }
        }
        catch(IOException e){}
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(!gameOver){
            if (keyCode == KeyEvent.VK_UP || keyCode == 87) {
                switch (dir) {
                    case 0:
                        if (maze[y - 1][x].equals(" ") || maze[y - 1][x].equals("H")) {
                            y--;
                            if (maze[y2 + 1][x2].equals(" "))
                                y2++;
                        }
                        break;
                    case 1:
                        if (maze[y][x + 1].equals(" ") || maze[y][x + 1].equals("H")) {
                            x++;
                            if (maze[y2][x2 - 1].equals(" "))
                                x2--;
                        }
                        break;
                    case 2:
                        if (maze[y + 1][x].equals(" ") || maze[y + 1][x].equals("H")) {
                            y++;
                            if (maze[y2 - 1][x2].equals(" "))
                                y2--;
                        }
                        break;
                    case 3:
                        if (maze[y][x - 1].equals(" ") || maze[y][x - 1].equals("H")) {
                            x--;
                            if (maze[y2][x2 + 1].equals(" "))
                                x2++;
                        }
                        break;
                }
            } else if (keyCode == KeyEvent.VK_LEFT || keyCode == 65) {
                dir--;
                if (dir < 0) {
                    dir = 3;
                }
            } else if (keyCode == KeyEvent.VK_RIGHT || keyCode == 68) {
                dir++;
                if (dir > 3) {
                    dir = 0;
                }
            }
        }

        if(x == x2 && y == y2){
            System.out.println("You lose");
            gameOver = true;
            lose = true;
        }

        if(x==finishx && y==finishy){
            System.out.println("You win");
            gameOver = true;
            win = true;
        }

        if (e.getKeyCode() == 32) {
            in3D = !in3D;
        }

        if (in3D) {
            walls = new ArrayList<Wall>();
            addLeftPathways();
            addLeftWalls();
            addRightPathways();
            addRightWalls();
            addCeilings();
            addFloors();
            addFrontWalls();
        }

        repaint();
    }

    public void keyReleased(KeyEvent e) {}

    public void keyTyped(KeyEvent e) {}

    public void paintComponent(Graphics g){
        super.paintComponent(g);


        Graphics2D g2 = (Graphics2D)g;
        if(in3D){
            g.setColor(new Color(0, 0, 0));
            g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
        }

        g2.setStroke(new BasicStroke(2));
        if (!in3D){
            g.setColor(new Color(0, 50, 0));
            g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
            for(int i = 0; i < maze.length; i++){
                for (int j = 0; j < maze[i].length; j++){
                    if (maze[i][j].equals("#")){
                        g.setColor(new Color(0, 128, 0));
                        g.fillRect(j*dim+40, i*dim+40, dim, dim);
                        g.setColor(Color.BLACK);
                        g.drawRect(j*dim+40, i*dim+40, dim, dim);
                    }
                }
            }
            double marioRotationAngle = 0.0;
            double goombaRotationAngle = 0.0;

            if (dir == 0) {
                marioRotationAngle = 0.0; // Up
                goombaRotationAngle = Math.toRadians(180); // goomba (facing down)
            } else if (dir == 1) {
                marioRotationAngle = Math.toRadians(90); // 90 degrees for right
                goombaRotationAngle = Math.toRadians(270); // goomba (facing left)
            } else if (dir == 2) {
                marioRotationAngle = Math.toRadians(180); // 180 degrees for down
                goombaRotationAngle = 0.0; // goomba (facing up)
            } else if (dir == 3) {
                marioRotationAngle = Math.toRadians(270); // 270 degrees for left
                goombaRotationAngle = Math.toRadians(90); // goomba (facing right)
            }
            g2.translate(x * dim + 40, y * dim + 40);
            g2.rotate(marioRotationAngle, dim / 2, dim / 2);
            g2.drawImage(mario, 0, 0, dim, dim, this);
            g2.rotate(-marioRotationAngle, dim / 2, dim / 2);
            g2.translate(-x * dim - 40, -y * dim - 40);

            g2.translate(x2 * dim + 40, y2 * dim + 40);
            g2.rotate(goombaRotationAngle, dim / 2, dim / 2);
            g2.drawImage(goomba[0], 0, 0, dim, dim, this);
            g2.rotate(-goombaRotationAngle, dim / 2, dim / 2);
            g2.translate(-x2 * dim - 40, -y2 * dim - 40);
            // g.setColor(new Color(150, 150, 250));
            // g.fillOval(x*dim+40, y*dim+40, dim, dim);
            // g.setColor(Color.CYAN);
            // g.drawOval(x*dim+40, y*dim+40, dim, dim);
        } else{
            for (Wall wall: walls){
                g2.setPaint(wall.getPaint());
                g2.fill(wall.getWall());
                // g.setColor(Color.BLACK);
                // g2.draw(wall.getWall());
            }
            Goomba3d(g2);
        }
        if (gameOver && lose){
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("TimesRoman", Font.PLAIN, 50));
            g2.drawString("YOU LOSE", 300, 200);
        } else if (gameOver && win){
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("TimesRoman", Font.PLAIN, 50));
            g2.drawString("YOU WIN", 300, 200);
        }
    }

    public void addLeftPathways(){
        for (int i = 0; i < 5; i++){
            int[] xx = {100+dist*i, 150+dist*i, 150+dist*i, 100+dist*i};
            int[] yy = {100+dist*i, 150+dist*i, 650-dist*i, 700-dist*i};
            walls.add(new Wall(xx, yy, 50, Color.GREEN, 255-dist*i, "Pathway"));

            xx = new int[]{100+dist*i, 150+dist*i, 150+dist*i, 100+dist*i};
            yy = new int[]{150+dist*i, 150+dist*i, 650-dist*i, 650-dist*i};
            walls.add(new Wall(xx, yy, 50, Color.GREEN, 255-dist*i, "Pathway"));
        }
    }

    public void addLeftWalls(){
        for (int i = 0; i < 5; i++){
            try{
                if (dir == 0 && maze[y-i][x-1].equals("#")){
                    int[] xx = {100+dist*i, 150+dist*i, 150+dist*i, 100+dist*i};
                    int[] yy = {100+dist*i, 150+dist*i, 650-dist*i, 700-dist*i};
                    walls.add(new Wall(xx, yy, 50, Color.GREEN, 255-dist * i, "LeftRight"));
                }
                if (dir == 1 && maze[y-1][x+i].equals("#")){
                    int[] xx = {100+dist*i, 150+dist*i, 150+dist*i, 100+dist*i};
                    int[] yy = {100+dist*i, 150+dist*i, 650-dist*i, 700-dist*i};
                    walls.add(new Wall(xx, yy, 50, Color.GREEN, 255-dist*i, "LeftRight"));
                }
                if (dir == 2 && maze[y+i][x+1].equals("#")){
                    int[] xx = {100+dist*i, 150+dist*i, 150+dist*i, 100+dist*i};
                    int[] yy = {100+dist*i, 150+dist*i, 650-dist*i, 700-dist*i};
                    walls.add(new Wall(xx, yy, 50, Color.GREEN, 255-dist*i, "LeftRight"));
                }
                if (dir == 3 && maze[y+1][x-i].equals("#")){
                    int[] xx = {100+dist*i, 150+dist*i, 150+dist*i, 100+dist*i};
                    int[] yy = {100+dist*i, 150+dist*i, 650-dist*i, 700-dist*i};
                    walls.add(new Wall(xx, yy, 50, Color.GREEN, 255-dist*i, "LeftRight"));
                }
            } catch(ArrayIndexOutOfBoundsException e){}
        }
    }

    public void addRightWalls() {
        for (int i = 0; i < 5; i++){
            try{
                if (dir == 0 && maze[y-i][x+1].equals("#")){
                    int[] xx = {700-dist*i, 650-dist*i, 650-dist*i, 700-dist*i};
                    int[] yy = {100+dist*i, 150+dist*i, 650-dist*i, 700-dist*i};
                    walls.add(new Wall(xx, yy, 50, Color.GREEN, 255-dist*i, "LeftRight"));
                }
                if (dir == 1 && maze[y+1][x+i].equals("#")){
                    int[] xx = {700-dist*i, 650-dist*i, 650-dist*i, 700-dist*i};
                    int[] yy = {100+dist*i, 150+dist*i, 650-dist*i, 700-dist*i};
                    walls.add(new Wall(xx, yy, 50, Color.GREEN, 255-dist*i, "LeftRight"));
                }
                if (dir == 2 && maze[y+i][x-1].equals("#")){
                    int[] xx = {700-dist*i, 650-dist*i, 650-dist*i, 700-dist*i};
                    int[] yy = {100+dist*i, 150+dist*i, 650-dist*i, 700-dist*i};
                    walls.add(new Wall(xx, yy, 50, Color.GREEN, 255-dist*i, "LeftRight"));
                }
                if (dir == 3 && maze[y-1][x-i].equals("#")){
                    int[] xx = {700-dist*i, 650-dist*i, 650-dist*i, 700-dist*i};
                    int[] yy = {100+dist*i, 150+dist*i, 650-dist*i, 700-dist*i};
                    walls.add(new Wall(xx, yy, 50, Color.GREEN, 255-dist*i, "LeftRight"));
                }
            }catch(ArrayIndexOutOfBoundsException e){}
        }
    }

    public void addRightPathways(){
        for (int i = 0; i < 5; i++){
            try{
                int[] xx = {700-dist*i, 650-dist*i, 650-dist*i, 700-dist*i};
                int[] yy = {100+dist*i, 150+dist*i, 650-dist*i, 700-dist*i};
                walls.add(new Wall(xx, yy, 50, Color.GREEN, 255-dist*i, "Pathway"));

                xx = new int[] {700-dist*i, 650-dist*i, 650-dist*i, 700-dist*i};
                yy = new int[] {150+dist*i, 150+dist*i, 650-dist*i, 650-dist*i};
                walls.add(new Wall(xx, yy, 50, Color.GREEN, 255-dist*i, "Pathway"));
            }catch(ArrayIndexOutOfBoundsException e){}
        }
    }

    public void addFrontWalls(){
        for(int i=4; i>=0; i--){
            try{
                int[] xx={150+dist*i,650-dist*i,650-dist*i,150+dist*i};
                int[] yy={150+dist*i,150+dist*i,650-dist*i,650-dist*i};

                //0-N, 1-E, 2-S, 3-W
                if(dir==0 && maze[y-i-1][x].equals("#")){
                    walls.add(new Wall(xx,yy,50,Color.GREEN,205-dist*i,"Front"));
                }

                if(dir==1 && maze[y][x+i+1].equals("#")){
                    walls.add(new Wall(xx,yy,50,Color.GREEN,205-dist*i,"Front"));
                }

                if(dir==2 && maze[y+i+1][x].equals("#")){
                    walls.add(new Wall(xx,yy,50,Color.GREEN,205-dist*i,"Front"));
                }

                if(dir==3 && maze[y][x-i-1].equals("#")){
                    walls.add(new Wall(xx,yy,50,Color.GREEN,205-dist*i,"Front"));
                }

            } catch(ArrayIndexOutOfBoundsException e) {}
        }
    }

    public void Goomba3d(Graphics g)
    {

        for(int i=0; i<5;i++)
        {
                try
                {
                    if(dir==0){
                        if(maze[y-i][x].equals("#"))
                            i=5;
                        else if (x2==x && y2==y-i)
                            g.drawImage(goomba[i], 400-goomba[i].getWidth()/2,650-i*dist-goomba[i].getHeight()+25, this);
                    } else if(dir==1){
                        if(maze[y][x+i].equals("#"))
                            i=5;
                        else if (x2==x+i && y2==y)
                            g.drawImage(goomba[i], 400-goomba[i].getWidth()/2,650-i*dist-goomba[i].getHeight()+25, this);
                    } else if(dir==2){
                        if(maze[y+i][x].equals("#"))
                            i=5;
                        else if (x2==x && y2==y+i)
                            g.drawImage(goomba[i], 400-goomba[i].getWidth()/2,650-i*dist-goomba[i].getHeight()+25, this);
                    } else if(dir==3){
                        if(maze[y][x-i].equals("#"))
                            i=5;
                        else if (x2==x-i && y2==y)
                            g.drawImage(goomba[i], 400-goomba[i].getWidth()/2,650-i*dist-goomba[i].getHeight()+25, this);
                    }

                }
                catch(ArrayIndexOutOfBoundsException e)
                {}
        }
        repaint();
    }

    public void addCeilings() {
        for (int i = 0; i < 5; i++) {
            try {
                int[] xx = {100 + dist * i, 150 + dist * i, 650 - dist * i, 700 - dist * i};
                int[] yy = {100 + dist * i, 150 + dist * i, 150 + dist * i, 100 + dist * i};
                walls.add(new Wall(xx, yy, 50, Color.GREEN, 255-dist*i, "Top"));
            } catch (ArrayIndexOutOfBoundsException e) {}
        }
    }

    public void addFloors(){
        for (int i = 0; i < 5; i++){
            try{
                int[] xx = {650-dist*i, 700-dist*i,100+dist*i, 150+dist*i};
                int[] yy = {650-dist*i, 700-dist*i, 700-dist*i, 650-dist*i};
                walls.add(new Wall(xx, yy, 50, Color.GREEN, 255-dist*i, "Bottom"));
            }catch(ArrayIndexOutOfBoundsException e){}
        }
    }

    public class Wall{
        private int[] y;
        private int[] x;
        private int dist;
        private Color color;
        private int colNum;
        private String type;

        public Wall(int[] x, int[] y, int dist, Color color, int colNum, String type){
            this.x = x;
            this.y = y;
            this.dist = dist;
            this.color = color;
            this.colNum = colNum;
            this.type = type;
        }

        public Polygon getWall(){
            return new Polygon(x, y, x.length);
        }
        public Color getColor(){
            return color;
        }
        public GradientPaint getPaint()
        {
            if(colNum<dist)
                colNum=dist;
            Color startCol = new Color(0, colNum, 0);
            Color endCol = new Color(0, colNum - dist, 0);
            if(type.equals("LeftRight"))
                return new GradientPaint(x[0], y[0], startCol, x[1], y[0], endCol);
            if(type.equals("Top"))
                return new GradientPaint(x[0], y[0], startCol, x[0], y[1], endCol);
            if(type.equals("Bottom"))
                return new GradientPaint(x[0], y[1], startCol, x[0], y[0], endCol);
            if(type.equals("Pathways"))
                return new GradientPaint(x[0],y[0],startCol,x[1],y[0],endCol);
            if(type.equals("Front"))
                return new GradientPaint(x[0], y[0], startCol, x[0], y[1], endCol);
            return new GradientPaint(x[0], y[0], startCol, x[1], y[1], endCol);
        }
    }

    public static void main(String[] args) {
        MazeProgram app = new MazeProgram();
    }
}