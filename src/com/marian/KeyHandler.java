package com.marian;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by marian on 11/24/2015.
 */
public class KeyHandler  extends  GameDisplay implements  KeyListener{

    static int computerPaddleMaxSpeed = 3;   //Max number of pixels that computer paddle can move clock tick. Higher number = easier for computer
        static int humanPaddleMaxSpeed = 5;   //This doesn't quite do the same thing... this is how many pixels human moves per key press TODO use this in a better way

        //static int humanPaddleSpeed = 0;      // "speed" is pixels moved up or down per clock tick
        static int computerPaddleSpeed = 0;   // same
        static int ballSize = 10;                //Diameter of ball

        static double ballSpeed = 5;   //Again, pixels moved per clock tick
        static double ballDirection = Math.PI + 1;

        //private static class KeyHandler implements KeyListener {

        @Override
        public void keyTyped(KeyEvent ev) {
            char keyPressed = ev.getKeyChar();
            char q = 'q';
            char s = 's';
            char u = 'u';
            char d = 'd';
            if (keyPressed == q) {
                System.exit(0);    //quit if user presses the q key.
            }

            if (gameOver && keyPressed == s ) {
                ballX = 160;
                removeGameOver = true;
                gameOver = false;
                score = false;
            }
            if (gameOver && keyPressed == u){
                ballX =150;
                removeGameOver = true;
                gameOver = false;
                score = false;

            }
            if (gameOver && keyPressed == d) {
                ballX = 150;
                removeGameOver = true;
                gameOver = false;
                score = false;
            }

        }

        @Override
        public void keyReleased(KeyEvent ev) {
        }   //Don't need this one, but required to implement it.

        @Override
        public void keyPressed(KeyEvent ev) {

            removeInstructions = true;   //game has started
            removeGameOver = false;

            if (ev.getKeyCode() == KeyEvent.VK_DOWN) {
                System.out.println("down key");
                moveDown();
            }
            if (ev.getKeyCode() == KeyEvent.VK_UP) {
                System.out.println("up key");
                moveUp();
            }


            //ev.getComponent() returns the GUI component that generated this event
            //In this case, it will be com.marian.GameDisplay JPanel
            ev.getComponent().repaint();   //This calls paintComponent(Graphics g) again
        }

        private void moveDown() {
            //Coordinates decrease as you go up the screen, that's why this looks backwards.
            if (humanPaddleY < screenSize - paddleSize) {
                humanPaddleY += humanPaddleMaxSpeed;
            }
        }

        private void moveUp() {
            //Coordinates increase as you go down the screen, that's why this looks backwards.
            if (humanPaddleY > paddleSize) {
                humanPaddleY -= humanPaddleMaxSpeed;
            }
        }

        protected static void moveComputerPaddle() {

            //if ballY = 100 and paddleY is 50, difference = 50, need to adjust
            //paddleY by up to the max speed (the minimum of difference and maxSpeed)

            //if ballY = 50 and paddleY = 100 then difference = -50
            //Need to move paddleY down by the max speed

            int ballPaddleDifference = computerPaddleY - (int) ballY;
            int distanceToMove = Math.min(Math.abs(ballPaddleDifference), computerPaddleMaxSpeed);

            System.out.println("computer paddle speed = " + computerPaddleSpeed);

            if (ballPaddleDifference > 0) {   //Difference is positive - paddle is below ball on screen
                computerPaddleY -= distanceToMove;

            } else if (ballPaddleDifference < 0) {
                computerPaddleY += distanceToMove;

            } else {
                //Ball and paddle are aligned. Don't need to move!
                computerPaddleSpeed = 0;

            }


        }
        protected static void moveBall() {
            System.out.println("move ball");
            //move in current direction
            //bounce off walls and paddle
            //TODO Take into account speed of paddles

            //Work in double

            boolean hitWall = false;
            boolean hitHumanPaddle = false;
            boolean hitComputerPaddle = false;

            if (ballX <= 0 || ballX >= screenSize ) {
                gameOver = true;
                return;
            }
            if (ballY <= 0 || ballY >= screenSize-ballSize) {
                hitWall = true;
            }

            //If ballX is at a paddle AND ballY is within the paddle size.
            //Hit human paddle?
            if (ballX >= screenSize-(paddleDistanceFromSide+(ballSize)) &&
                    (ballY > humanPaddleY-paddleSize && ballY < humanPaddleY+paddleSize))
                hitHumanPaddle = true;

            //Hit computer paddle?
            if (ballX <= paddleDistanceFromSide && (ballY >
                    computerPaddleY-paddleSize && ballY < computerPaddleY+paddleSize))
                hitComputerPaddle = true;


            if (hitWall == true) {
                //bounce
                ballDirection = ( (2 * Math.PI) - ballDirection );
                System.out.println("ball direction " + ballDirection);
            }

            //Bounce off computer paddle - just need to modify direction
            if (hitComputerPaddle == true) {
                ballDirection = (Math.PI) - ballDirection;
                //TODO factor in speed into new direction
                //TODO So if paddle is moving down quickly, the ball will angle more down too

            }

            //Bounce off computer paddle - just need to modify direction
            if (hitHumanPaddle == true) {
                ballDirection = (Math.PI) - ballDirection;
                //TODO consider speed of paddle
            }

            //Now, move ball correct distance in the correct direction

            // ** TRIGONOMETRY **

            //distance to move in the X direction is ballSpeed * cos(ballDirection)
            //distance to move in the Y direction is ballSpeed * sin(ballDirection)

            ballX = ballX + (ballSpeed * Math.cos(ballDirection));
            ballY = ballY + (ballSpeed * Math.sin(ballDirection));

            // ** TRIGONOMETRY END **

        }
    }


