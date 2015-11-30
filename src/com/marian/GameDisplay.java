package com.marian;

import javax.swing.*;
import javax.swing.*;
import java.awt.*;
/**
 * Created by marian on 11/24/2015.
 */
public class GameDisplay extends JPanel {


        //private static class com.marian.GameDisplay extends JPanel {
        static int screenSize = 300;    //and width - screen is square
        static int paddleSize = 25;     //Actually half the paddle size - how much to draw on each side of center
        static int paddleDistanceFromSide = 10;  //How much space between each paddle and side of screen

        //static int gameSpeed = 75;  //How many milliseconds between clock ticks? Reduce this to speed up game

        static int computerPaddleY = screenSize / 2;    //location of the center of the paddles on the Y-axis of the screen
        static int humanPaddleY = screenSize / 2;

        static int computerPaddleMaxSpeed = 3;   //Max number of pixels that computer paddle can move clock tick. Higher number = easier for computer
        static int humanPaddleMaxSpeed = 5;   //This doesn't quite do the same thing... this is how many pixels human moves per key press TODO use this in a better way

        // static int humanPaddleSpeed = 0;      // "speed" is pixels moved up or down per clock tick
        //static int computerPaddleSpeed = 0;   // same

        static double ballX = screenSize / 2;   //Imagine the ball is in a square box. These are the coordinates of the top of that box.
        static double ballY = screenSize / 2;   //So this starts the ball in (roughly) the center of the screen
        static int ballSize = 10;                //Diameter of ball

        // static double ballSpeed = 5;   //Again, pixels moved per clock tick
        //static com.marian.GameDisplay gamePanel;   //draw the game components here

        static boolean gameOver;      //Used to work out what message, if any, to display on the screen
        static boolean removeInstructions = false;  // Same as above
        static boolean removeGameOver = false;
        static boolean score = false;
        int computerplayerWin = 0;
        int humanplayWin = 0;


        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            //to check human or computer who win the game
            if (gameOver && !score) {
                if (ballX <= 0) {
                    humanplayWin++;
                } else {
                    computerplayerWin++;
                }
            }
            if (gameOver && !removeGameOver) {
                //set color when game is over text
                g.setColor(Color.red);
                g.drawString("game over!", 30, 40);
                //set color on human player  and computer player
                g.setColor(Color.black);
                g.drawString("score: " + "human_player win =" + humanplayWin + " computer win=" + computerplayerWin, 30, 50);
                g.setColor(Color.blue);
                //set color to start game again
                g.drawString("press u to start the game, " , 20, 90);
                g.setColor(Color.BLUE);
                g.drawString("press d to start the game", 20, 90);
                g.setColor(Color.DARK_GRAY);
                g.drawString("press s to start the game again", 20, 80);
                score= true;

                return;
            }

            if (removeInstructions) {
                g.drawString("Pong! Press up or down to move", 20, 30);
                g.drawString("Press q to quit", 20, 60);
            }
            g.setColor(Color.orange);
            g.fillOval((int) ballX, (int) ballY, ballSize, ballSize);

            g.setColor(Color.blue);

            //While game is playing, these methods draw the ball, paddles, using the global variables
            //Other parts of the code will modify these variables

            //Ball - a circle is just an oval with the height equal to the width
            //g.drawOval((int)ballX, (int)ballY, ballSize, ballSize);
            //Computer paddle

            g.drawLine(paddleDistanceFromSide, computerPaddleY - paddleSize,
                    paddleDistanceFromSide, computerPaddleY + paddleSize);
            //Human paddle
            g.setColor(Color.red);
            g.drawLine(screenSize - paddleDistanceFromSide, humanPaddleY -
                    paddleSize, screenSize - paddleDistanceFromSide, humanPaddleY + paddleSize);

        }

    }

