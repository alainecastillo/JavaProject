import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BrickBreaker extends JPanel implements KeyListener, ActionListener, Runnable {
    // movement keys..
    static boolean right = false;
    static boolean left = false;
    boolean [] keys = new boolean[256];
    // ..............

    // bola
    int ballx = 160;
    int bally = 420;


    //pangsalo
    int batx = 230;
    int baty = 420;


    // brick
    int brickx = 100;
    int bricky = 50;

	//score
    int score=0;
    
    //lives
    int lives=3;
	
	//gamestate
    int GameState=0;

    // declaring ball, paddle,bricks
    Rectangle Ball = new Rectangle(ballx, bally, 10, 10);
    Rectangle Bat = new Rectangle(batx, baty, 60, 10);
    Rectangle[] Brick = new Rectangle[101];

    BrickBreaker() {}

    // /...Game Loop...................

    // /////////////////// When ball strikes borders......... it
    // reverses......==>
    int movex = -1;
    int movey = -1;
    boolean ballFallDown = false;
    boolean bricksOver = false;

    int count = 0;
    String status;

    public void bricks() {
        play();
        ////////////// =====Creating bricks for the game===>.....
        for (int i = 0; i < Brick.length; i++) {
            Brick[i] = new Rectangle(brickx, bricky, 30, 10);
            if (i == 10) {
                brickx = 80;
                bricky = 62;
            }
            if (i == 20) {
                brickx = 80;
                bricky = 74;
            }
            if (i == 30) {
                brickx = 80;
                bricky = 86;
            }
            if (i == 40) {
                brickx = 80;
                bricky = 98;
            }
            if (i == 50) {
                brickx = 80;
                bricky = 110;
            }
            if (i == 60) {
                brickx = 80;
                bricky = 122;
            }
            if (i == 70) {
                brickx = 80;
                bricky = 134;
            }
            if (i == 80) {
                brickx = 80;
                bricky = 146;
            }
            if (i == 90) {
                brickx = 80;
                bricky = 158;
            }
            brickx += 31;
        }// Tapos na yung paggawa ng brick
    }

    public void ballReverses() { // == ball reverses when touches the brick=======
        for (int i = 0; i < Brick.length; i++) {
            if (Brick[i] != null) {
                if (Brick[i].intersects(Ball)) {
                    Brick[i] = null;
                    score=score+25;

                    // movex = -movex;
                    movey = -movey;
                    count++;
                }// end of 2nd if..
            }// end of 1st if..
        }// end of for loop..
    }

    public void ballHitsBricks() {
        // /////////// =================================
        if (count == Brick.length) {// check if ball hits all bricks
            bricksOver = true;
            status = "YOU WON THE GAME";
            repaint();
        }
    }

    public void ballPosition() {
        Ball.x += movex;
        Ball.y += movey;
    }

    public void paddleControls() {
        if (left == true) {

            Bat.x -= 3;
            right = false;
        }
        if (right == true) {
            Bat.x += 3;
            left = false;
        }
        if (Bat.x <= 4) {
            Bat.x = 4;
        } else if (Bat.x >= 440) {
            Bat.x = 440;
        }
    }

    boolean play=false; //boolean play

    public void ballReversesStrikesBat() {
        // /===== Ball reverses when strikes the bat
        if(GameState==1) play=true; {
            if (Ball.intersects(Bat)) {
                movey = -movey;
            }
        }
    }

    public void ballReversesLeftAndRight(){
        // ....ball reverses when touches left and right boundary
        if(GameState==1) play=true; {
        if (Ball.x <= 0 || Ball.x + Ball.height >= 500) {
                movex = -movex;
            }
        }// if ends here
        if(GameState==1) play=true; {
        if (Ball.y <= 0) {// ////////////////|| bally + Ball.height >= 250
                movey = -movey;
            }
        }// if ends here.....
    }

    public void gameOver() {
        if(GameState==1) play=true; {
        if (Ball.y >= 420) {// when ball falls below bat game is over...
                ballFallDown = true;
                status = "YOU LOST THE GAME";
                repaint();
            }
        }
    }

    public void play() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE) {
                    if(GameState == 0) {
                        GameState = 1;
                    }
                    else if(GameState == 2) {
                        play=false;
                        ballx=160;
                        bally=420;

                        batx = 230;
                        baty = 420;

                        brickx = 100;
                        bricky = 50;

                        GameState = 0;
                    }
                }
                else if(keys[32]==false) {
                    play=false;
                    }
                }
            });
        }

    public void run() {
        bricks();
        while (true) {

            try {
                ballReverses();
                ballHitsBricks();
                // /////////// =================================
                repaint();
                ballPosition();
                paddleControls();
                ballReversesStrikesBat();
                // //=====================================
                ballReversesLeftAndRight();
                gameOver();
                Thread.sleep(10);
            }
            catch(Exception ex) {}// try catch end
        }// while loop end
    }// loop end

    //Keyevents
    @Override
    public void keyPressed(KeyEvent e) {
        if (GameState == 1) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_LEFT) {
                left = true;
                // System.out.print("left");
            }

            if (keyCode == KeyEvent.VK_RIGHT) {
                right = true;
                // System.out.print("right");
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (GameState == 1) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_LEFT) {
                left = false;
            }

            if (keyCode == KeyEvent.VK_RIGHT) {
                right = false;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent arg0) {}

    //RESTART BUTTON
    @Override
    public void actionPerformed(ActionEvent e) {
        if (GameState == 1) {
            String str = e.getActionCommand();
            if (str.equals("Restart")) {
                score = 0;
                if (lives > 0) lives = lives - 1;
                {
                    status = "No more lives";
                    repaint();
                }
                if (lives == 0) {
                    JOptionPane.showMessageDialog(null, "No more lives. Please try again.");
                    System.exit(0);
                }

                System.out.print("game");
                this.restart();
            }
        }
    }


    //WHEN RESTART PRESS
    public void restart() {
        requestFocus(true);
        // ..............
        // variables declaration for ball
        ballx = 160;
        bally = 420;
        // variables declaration for ball

        // variables declaration for bat
        batx = 230;
        baty = 420;
        // variables declaration for bat

        // variables declaration for brick
        brickx = 100;
        bricky = 50;
        // variables declaration for brick

        // declaring ball, paddle,bricks
        Ball = new Rectangle(ballx, bally, 10, 10);
        Bat = new Rectangle(batx, baty, 60, 10);
        // Rectangle Brick;// = new Rectangle(brickx, bricky, 30, 10);
        Brick = new Rectangle[101];

        movex = -1;
        movey = -1;
        ballFallDown = false;
        bricksOver = false;
        count = 0;
        status = null;

        ////////////// =====brikcs===>..... gawa ulit ng bricks
        for (int i = 0; i < Brick.length; i++) {
            Brick[i] = new Rectangle(brickx, bricky, 30, 10);
            if (i == 10) {
                brickx = 80;
                bricky = 62;
            }
            if (i == 20) {
                brickx = 80;
                bricky = 74;
            }
            if (i == 30) {
                brickx = 80;
                bricky = 86;
            }
            if (i == 40) {
                brickx = 80;
                bricky = 98;
            }
            if (i == 50) {
                brickx = 80;
                bricky = 110;
            }
            if (i == 60) {
                brickx = 80;
                bricky = 122;
            }
            if (i == 70) {
                brickx = 80;
                bricky = 134;
            }
            if (i == 80) {
                brickx = 80;
                bricky = 146;
            }
            if (i == 90) {
                brickx = 80;
                bricky = 158;
            }
            brickx += 31;
        }
        repaint();
    }


    // declaring ball, paddle,bricks
    public void paint(Graphics g) {
        super.paint(g);

        if(GameState == 0) {
                g.setColor(new Color(0, 0, 0, 150));
                g.fillRect(0, 0, getWidth(), getHeight());

                Font fnt = new Font("Arial", Font.BOLD, 20);
                g.setFont(fnt);
                g.setColor(Color.WHITE);
                g.drawString("PRESS SPACEBAR TO START", 200, 230);
        }

        if(GameState == 1) {
        Image img1 = Toolkit.getDefaultToolkit().getImage("bg.jpg");

            g.drawImage(img1, -100, -500, this);

            g.setColor(Color.WHITE);
            g.fillOval(Ball.x, Ball.y, Ball.width, Ball.height);

            g.setColor(Color.WHITE);
            g.fill3DRect(Bat.x, Bat.y, Bat.width, Bat.height, true);

            g.setColor(Color.WHITE);
            g.drawRect(0, 0, 500, 470);
            g.setFont(new Font("Showcard Gothic", Font.PLAIN, 15));
            g.drawString("Lives: " + lives, 550, 200);

            g.drawString("Score: " + score, 550, 270);

            for (int i = 0; i < Brick.length; i++) {
                if (Brick[i] != null) {
                    g.fill3DRect(Brick[i].x, Brick[i].y, Brick[i].width,
                            Brick[i].height, true);
                }
            }

            if (ballFallDown == true || bricksOver == true) {
                Font f = new Font("Showcard Gothic", Font.BOLD, 20);
                g.setFont(f);
                g.drawString(status, 150, 250);
                g.drawString("Score: " + score, 200, 200);
                ballFallDown = false;
                bricksOver = false;
            }
        }
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        BrickBreaker game = new BrickBreaker();
        JButton button = new JButton("Restart");


        frame.setTitle("Brick Breaker");
        frame.setSize(750, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(game);

        frame.add(button, BorderLayout.EAST);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        button.addActionListener(game);
        button.setFont(new Font("Showcard Gothic", Font.PLAIN, 10));

        game.addKeyListener(game);
        game.setFocusable(true);
        Thread t = new Thread(game);
        t.start();
    }

}
