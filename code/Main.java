import java.awt.*; // for drawings

/**
 * Program provides original dx_ball game
 * author Melih Efe Sonmez
 * since Date: 15.03.2025
 */
public class Main {

    /**
     * Starts the dx-ball game and draws the game scene with a loop
     * <p>
     * Gamer determines the angle of the ball using left and right arrows and starts the game with SPACE button.
     * By using left and right arrows, gamer controls the paddle and tries to destroy the bricks by the ball.
     * Until all bricks are destroyed, game continues; or game finishes when the ball hits the ground.
     * </p>
     * @param args Main input arguments are not used
     */
    public static void main(String[] args) {

        // Canvas properties, scale and set the canvas with the given parameters
        double X_SCALE = 800.0, Y_SCALE = 400.0;
        StdDraw.setCanvasSize(800, 400);
        StdDraw.setXscale(0.0, X_SCALE);
        StdDraw.setYscale(0.0, Y_SCALE);
        StdDraw.enableDoubleBuffering(); // to prevent flickering

        // Color array for bricks (first import java.awt.Color)
        Color[] COLORS = {new Color(255, 0, 0), new Color(220, 20, 60),
                new Color(178, 34, 34), new Color(139, 0, 0),
                new Color(255, 69, 0), new Color(165, 42, 42)
        };

        // Game Components (These can be changed for custom scenarios)
        double BALL_RADIUS = 8; // Ball radius
        double BALL_VELOCITY = 5; // Magnitude of the ball velocity
        Color BALL_COLOR = new Color(15, 82, 186); // Color of the ball
        double[] initialBallPos = {400, 18}; //Initial position of the ball in the format {x, y}
        double[] paddlePos = {400, 5}; // Initial position of the center of the paddle
        double PADDLE_HALFWIDTH = 60; // Paddle half width
        double PADDLE_HALFHEIGHT = 5; // Paddle half height
        double PADDLE_SPEED = 20; // Paddle speed
        Color PADDLE_COLOR = new Color(128, 128, 128); // Paddle color
        double BRICK_HALFWIDTH = 50; // Brick half width
        double BRICK_HALFHEIGHT = 10; // Brick half height

        // 2D array to store center coordinates of bricks in the format {x, y}
        double[][] BRICK_COORDINATES = new double[][]{
                {250, 320}, {350, 320}, {450, 320}, {550, 320},
                {150, 300}, {250, 300}, {350, 300}, {450, 300}, {550, 300}, {650, 300},
                {50, 280}, {150, 280}, {250, 280}, {350, 280}, {450, 280}, {550, 280}, {650, 280}, {750, 280},
                {50, 260}, {150, 260}, {250, 260}, {350, 260}, {450, 260}, {550, 260}, {650, 260}, {750, 260},
                {50, 240}, {150, 240}, {250, 240}, {350, 240}, {450, 240}, {550, 240}, {650, 240}, {750, 240},
                {150, 220}, {250, 220}, {350, 220}, {450, 220}, {550, 220}, {650, 220},
                {250, 200}, {350, 200}, {450, 200}, {550, 200}};

        // Brick Colors
        Color[] BRICK_COLORS = new Color[]{
                COLORS[0], COLORS[1], COLORS[2], COLORS[3],
                COLORS[2], COLORS[4], COLORS[3], COLORS[0], COLORS[4], COLORS[5],
                COLORS[5], COLORS[0], COLORS[1], COLORS[5], COLORS[2], COLORS[3], COLORS[0], COLORS[4],
                COLORS[1], COLORS[3], COLORS[2], COLORS[4], COLORS[0], COLORS[5], COLORS[2], COLORS[1],
                COLORS[4], COLORS[0], COLORS[5], COLORS[1], COLORS[2], COLORS[3], COLORS[0], COLORS[5],
                COLORS[1], COLORS[4], COLORS[0], COLORS[5], COLORS[1], COLORS[2],
                COLORS[3], COLORS[2], COLORS[3], COLORS[0]};

        // Brick coordinates whether being destroyed info
        double[][] brickCoordinatesState = new double[BRICK_COORDINATES.length][3];

        for (int i = 0; i < BRICK_COORDINATES.length; i++) { // add -1 to brick coordinates to show they did not encounter with a collision
            brickCoordinatesState[i][0] = BRICK_COORDINATES[i][0];
            brickCoordinatesState[i][1] = BRICK_COORDINATES[i][1];
            brickCoordinatesState[i][2] = -1;
        }

        double angle = 90; // initial angle of arrow

        // Starting loop of the game
        while (true) {
            if (StdDraw.isKeyPressed(37)) { // move the arrow to the left with <- button
                if (angle < 180) {
                    angle += 1; // one degree change
                }
            }
            if (StdDraw.isKeyPressed(39)) { // move the arrow to the right -> button
                if (angle > 0) {
                    angle -= 1; // one degree change
                }
            }
            if (StdDraw.isKeyPressed(32)) { // throw the ball using SPACE button
                break; // end loop and start the game
            }

            // Draw the new scene of the game
            StdDraw.clear(StdDraw.WHITE); // clear the background

            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setFont(new Font("Helvetica", Font.PLAIN, 18));
            StdDraw.textLeft(15, 375, "Angle:" + angle); // type angle to the top left

            StdDraw.setPenColor(StdDraw.RED);
            double xEnd = initialBallPos[0] + 50 * Math.cos(Math.toRadians(angle)); // determine the arrow's coordinates
            double yEnd = initialBallPos[1] + 50 * Math.sin(Math.toRadians(angle)); // determine the arrow's coordinates
            StdDraw.line(initialBallPos[0], initialBallPos[1], xEnd, yEnd);

            StdDraw.setPenColor(BALL_COLOR); // arrange color for the ball
            StdDraw.filledCircle(initialBallPos[0], initialBallPos[1], BALL_RADIUS); // draw circle on the screen
            StdDraw.setPenColor(PADDLE_COLOR); // arrange color for the paddle
            StdDraw.filledRectangle(paddlePos[0], paddlePos[1], PADDLE_HALFWIDTH, PADDLE_HALFHEIGHT); // draw paddle on the screen

            // Draw bricks with a loop
            for (int i = 0; i < BRICK_COORDINATES.length; i++) {
                double brickX = brickCoordinatesState[i][0];
                double brickY = brickCoordinatesState[i][1];
                StdDraw.setPenColor(BRICK_COLORS[i]);
                if (brickCoordinatesState[i][2] == -1) {
                    StdDraw.filledRectangle(brickX, brickY, BRICK_HALFWIDTH, BRICK_HALFHEIGHT); // draw brick on the screen
                }
            }
            StdDraw.show(); // show the drawing on the screen
            StdDraw.pause(20); // pause the drawing at each iteration
        }

        // Some variables for the following loop
        double velocityX = BALL_VELOCITY * Math.cos(Math.toRadians(angle)); // x component of the ball's velocity
        double velocityY = BALL_VELOCITY * Math.sin(Math.toRadians(angle)); // y component of the ball's velocity
        int score = 0; // initial score
        int spaceCount = 0; // to prevent a bug when pressed the SPACE button
        boolean spacePaused = false; // info of the game whether PAUSED or not
        long lastSpaceTime = 0; // last time pressed SPACE

        // Main animation loop
        while (true) {

            if (StdDraw.isKeyPressed(32)) { // control of SPACE press
                long currentTime = System.currentTimeMillis(); // system time

                if (currentTime - lastSpaceTime >= 200) { // wait 200 ms
                    spaceCount += 1;

                    if (!spacePaused && spaceCount > 1) { // ignore first press
                        spacePaused = true;
                    } else if (spacePaused && spaceCount > 1) {
                        spacePaused = false;
                    }

                    lastSpaceTime = currentTime; // change last time SPACE pressed
                }
            }

            if (spacePaused == false) { // continue game if it is not paused

                // Paddle moves
                if (StdDraw.isKeyPressed(37)) { // move the paddle to the left
                    if (paddlePos[0] - PADDLE_HALFWIDTH > 0) { // consider limitations
                        paddlePos[0] = paddlePos[0] - PADDLE_SPEED;
                    }
                }
                if (StdDraw.isKeyPressed(39)) { // move the paddle to the right
                    if (paddlePos[0] + PADDLE_HALFWIDTH < X_SCALE) { // consider limitations
                        paddlePos[0] = paddlePos[0] + PADDLE_SPEED;
                    }
                }

                // Coordinates of the paddle's surfaces
                double paddleLeft = paddlePos[0] - PADDLE_HALFWIDTH;
                double paddleRight = paddlePos[0] + PADDLE_HALFWIDTH;
                double paddleTop = paddlePos[1] + PADDLE_HALFHEIGHT;

                // COLLISIONS
                if (Math.abs(initialBallPos[0] + velocityX) > X_SCALE - BALL_RADIUS) { // with right wall
                    velocityX = -velocityX;
                } else if (Math.abs(initialBallPos[0] + velocityX) < 0 + BALL_RADIUS) { // with left wall
                    velocityX = -velocityX;
                } else if (Math.abs(initialBallPos[1] + velocityY) > Y_SCALE - BALL_RADIUS) { // with ceiling
                    velocityY = -velocityY;
                } else if (paddlePos[0] - PADDLE_HALFWIDTH < initialBallPos[0] && paddlePos[0] + PADDLE_HALFWIDTH > initialBallPos[0]) { // with the paddle's flat
                    if (Math.abs(initialBallPos[1] + velocityY) < 2 * PADDLE_HALFHEIGHT + BALL_RADIUS) {
                        velocityY = -velocityY;
                    }
                } else if (Math.pow(Math.pow(paddleLeft - initialBallPos[0], 2) + Math.pow(paddleTop - initialBallPos[1], 2), 0.5) < BALL_RADIUS) { // with the paddle's top-left

                    // Variables for dot product
                    double dx = initialBallPos[0] - paddleLeft;
                    double dy = initialBallPos[1] - paddleTop;
                    double distance = Math.pow(Math.pow(dx, 2) + Math.pow(dy, 2), 0.5);

                    // Dot product calculation
                    double dot = velocityX * (dx / distance) + velocityY * (dy / distance); // -x and +y since normal is in the second quadrant of coordinate system

                    // Change the velocity components
                    velocityX = velocityX - 2 * dot * (dx / distance);
                    velocityY = velocityY - 2 * dot * (dy / distance);

                } else if (Math.pow(Math.pow(paddleRight - initialBallPos[0], 2) + Math.pow(paddleTop - initialBallPos[1], 2), 0.5) < BALL_RADIUS) { // with the paddle's top-right

                    // Variables for dot product
                    double dx = initialBallPos[0] - paddleRight;
                    double dy = initialBallPos[1] - paddleTop;
                    double distance = Math.pow(Math.pow(dx, 2) + Math.pow(dy, 2), 0.5);

                    // Dot product calculation
                    double dot = velocityX * (dx / distance) + velocityY * (dy / distance); // +x and +y since normal is in the first quadrant of coordinate system

                    // Change the velocity components
                    velocityX = velocityX - 2 * dot * (dx / distance);
                    velocityY = velocityY - 2 * dot * (dy / distance);

                }

                // for adjustment of double collisions
                int collisionCountTop = 0;
                int collisionCountBottom = 0;
                int collisionCountRight = 0;
                int collisionCountLeft = 0;

                // Check all bricks on by one
                for (int i = 0; i < BRICK_COORDINATES.length; i++) {

                    if (brickCoordinatesState[i][2] == -1) {  // the brick did not collide

                        // Coordinates of the bricks's edges
                        double brickLeft = brickCoordinatesState[i][0] - BRICK_HALFWIDTH;
                        double brickRight = brickCoordinatesState[i][0] + BRICK_HALFWIDTH;
                        double brickBottom = brickCoordinatesState[i][1] - BRICK_HALFHEIGHT;
                        double brickTop = brickCoordinatesState[i][1] + BRICK_HALFHEIGHT;

                        // Coordinates of the ball's edges
                        double ballLeft = initialBallPos[0] - BALL_RADIUS;
                        double ballRight = initialBallPos[0] + BALL_RADIUS;
                        double ballBottom = initialBallPos[1] - BALL_RADIUS;
                        double ballTop = initialBallPos[1] + BALL_RADIUS;

                        // Collisions with brick's 4 surfaces
                        if (initialBallPos[0] >= brickLeft && initialBallPos[0] <= brickRight && ballTop >= brickBottom && ballBottom < brickBottom) { // with bottom surface

                            brickCoordinatesState[i][2] = 1;
                            score += 10;
                            collisionCountBottom += 1;

                        } else if (initialBallPos[0] >= brickLeft && initialBallPos[0] <= brickRight && ballTop > brickTop && ballBottom <= brickTop) { // with top surface

                            brickCoordinatesState[i][2] = 1;
                            score += 10;
                            collisionCountTop += 1;

                        } else if (initialBallPos[1] >= brickBottom && initialBallPos[1] <= brickTop && ballRight >= brickLeft && ballLeft < brickLeft) { // with left surface

                            brickCoordinatesState[i][2] = 1;
                            score += 10;
                            collisionCountLeft += 1;

                        } else if (initialBallPos[1] >= brickBottom && initialBallPos[1] <= brickTop && ballRight > brickRight && ballLeft <= brickRight) { // with right surface

                            brickCoordinatesState[i][2] = 1;
                            score += 10;
                            collisionCountRight += 1;

                        }
                        // Bricks' corner collisions
                        else if (Math.pow(Math.pow(brickLeft - initialBallPos[0], 2) + Math.pow(brickTop - initialBallPos[1], 2), 0.5) <= BALL_RADIUS) { // with top-left corner

                            brickCoordinatesState[i][2] = 1;
                            score += 10;

                            // Variables for dot product
                            double dx = initialBallPos[0] - brickLeft;
                            double dy = initialBallPos[1] - brickTop;
                            double distance = Math.pow(Math.pow(dx, 2) + Math.pow(dy, 2), 0.5);

                            // Dot product calculation
                            double dot = velocityX * (dx / distance) + velocityY * (dy / distance); // -x and +y since normal is in the second quadrant of coordinate system

                            // Change the velocity components
                            velocityX = velocityX - 2 * dot * (dx / distance);
                            velocityY = velocityY - 2 * dot * (dy / distance);


                        } else if (Math.pow(Math.pow(brickRight - initialBallPos[0], 2) + Math.pow(brickTop - initialBallPos[1], 2), 0.5) <= BALL_RADIUS) { // with top-right corner

                            brickCoordinatesState[i][2] = 1;
                            score += 10;

                            // Variables for dot product
                            double dx = initialBallPos[0] - brickRight;
                            double dy = initialBallPos[1] - brickTop;
                            double distance = Math.pow(Math.pow(dx, 2) + Math.pow(dy, 2), 0.5);

                            // Dot product calculation
                            double dot = velocityX * (dx / distance) + velocityY * (dy / distance); // +x and +y since normal is in the first quadrant of coordinate system

                            // Change the velocity components
                            velocityX = velocityX - 2 * dot * (dx / distance);
                            velocityY = velocityY - 2 * dot * (dy / distance);

                        } else if (Math.pow(Math.pow(brickLeft - initialBallPos[0], 2) + Math.pow(brickBottom - initialBallPos[1], 2), 0.5) <= BALL_RADIUS) { // with bottom-left corner

                            brickCoordinatesState[i][2] = 1;
                            score += 10;

                            // Variables for dot product
                            double dx = initialBallPos[0] - brickLeft;
                            double dy = initialBallPos[1] - brickBottom;
                            double distance = Math.pow(Math.pow(dx, 2) + Math.pow(dy, 2), 0.5);

                            // Dot product calculation
                            double dot = velocityX * (dx / distance) + velocityY * (dy / distance); // -x and -y since normal is in the third quadrant of coordinate system

                            // Change the velocity components
                            velocityX = velocityX - 2 * dot * (dx / distance);
                            velocityY = velocityY - 2 * dot * (dy / distance);

                        } else if (Math.pow(Math.pow(brickRight - initialBallPos[0], 2) + Math.pow(brickBottom - initialBallPos[1], 2), 0.5) <= BALL_RADIUS) { // with bottom-right corner

                            brickCoordinatesState[i][2] = 1;
                            score += 10;

                            // Variables for dot product
                            double dx = initialBallPos[0] - brickRight;
                            double dy = initialBallPos[1] - brickBottom;
                            double distance = Math.pow(Math.pow(dx, 2) + Math.pow(dy, 2), 0.5);

                            // Dot product calculation
                            double dot = velocityX * (dx / distance) + velocityY * (dy / distance); // +x and -y since normal is in the fourth quadrant of coordinate system

                            // Change the velocity components
                            velocityX = velocityX - 2 * dot * (dx / distance);
                            velocityY = velocityY - 2 * dot * (dy / distance);

                        }
                    }
                }

                // Implement velocity changes for one time whether the ball collides with one or two bricks
                if (collisionCountBottom == 1 || collisionCountBottom == 2) {
                    velocityY = -velocityY;
                }
                if (collisionCountTop == 1 || collisionCountTop == 2) {
                    velocityY = -velocityY;
                }
                if (collisionCountLeft == 1 || collisionCountLeft == 2) {
                    velocityX = -velocityX;
                }
                if (collisionCountRight == 1 || collisionCountRight == 2) {
                    velocityX = -velocityX;
                }

                // New position of the ball
                initialBallPos[0] = initialBallPos[0] + velocityX;
                initialBallPos[1] = initialBallPos[1] + velocityY;

                // Control variable for end of the game
                boolean allBricksDestroyed = true;

                // Check "Is there any brick?"
                for (int i = 0; i < BRICK_COORDINATES.length; i++) {

                    if (brickCoordinatesState[i][2] == -1) {
                        allBricksDestroyed = false;
                        break; // if there is brick, end the loop
                    }
                }

                if (allBricksDestroyed) { // end the game by victory

                    StdDraw.clear(StdDraw.WHITE); // clear the background to get rid of the last brick

                    StdDraw.setPenColor(BALL_COLOR); // arrange color for the ball
                    StdDraw.filledCircle(initialBallPos[0], initialBallPos[1], BALL_RADIUS); // draw circle on the screen
                    StdDraw.setPenColor(PADDLE_COLOR); // arrange color for the paddle
                    StdDraw.filledRectangle(paddlePos[0], paddlePos[1], PADDLE_HALFWIDTH, PADDLE_HALFHEIGHT); // draw paddle on the screen

                    // Victory display
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.setFont(new Font("Helvetica", Font.BOLD, 30));
                    StdDraw.textLeft(328, 160, "VICTORY!");
                    StdDraw.setFont(new Font("Helvetica", Font.PLAIN, 22));
                    StdDraw.textLeft(340, 132, "SCORE: " + score);

                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.filledRectangle(745, 375, 70, 10); // cover the scoreboard


                    StdDraw.show(); // show the drawing on the screen
                    StdDraw.pause(20); // pause the drawing at each iteration
                    break;

                }
                if (Math.abs(initialBallPos[1]) <= BALL_RADIUS) { // end the game by game over

                    // Game Over display
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.setFont(new Font("Helvetica", Font.BOLD, 30));
                    StdDraw.textLeft(308, 160, "GAME OVER!");
                    StdDraw.setFont(new Font("Helvetica", Font.PLAIN, 22));
                    StdDraw.textLeft(345, 132, "SCORE: " + score);

                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.filledRectangle(745, 375, 70, 10); // cover the scoreboard

                    StdDraw.show(); // show the drawing on the screen
                    StdDraw.pause(20); // pause the drawing at each iteration
                    break;
                }
                StdDraw.clear(StdDraw.WHITE); // clear the background in "spacePaused = false" to prevent flickering on the PAUSED
            }

            // PAUSED sign at the top left
            if (spacePaused == true) {
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setFont(new Font("Helvetica", Font.BOLD, 19));
                StdDraw.textLeft(15, 370, "PAUSED");
                StdDraw.show(); // show the drawing on the screen
                StdDraw.pause(20); // pause the drawing at each iteration
            }

            // Draw the new scene of the game
            StdDraw.setPenColor(BALL_COLOR); // arrange color for the ball
            StdDraw.filledCircle(initialBallPos[0], initialBallPos[1], BALL_RADIUS); // draw circle on the screen
            StdDraw.setPenColor(PADDLE_COLOR); // arrange color for the paddle
            StdDraw.filledRectangle(paddlePos[0], paddlePos[1], PADDLE_HALFWIDTH, PADDLE_HALFHEIGHT); // draw paddle on the screen

            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setFont(new Font("Helvetica", Font.PLAIN, 19));
            StdDraw.textLeft(685, 375, "Score: " + score); // draw scoreboard

            // Draw bricks with a loop
            for (int i = 0; i < BRICK_COORDINATES.length; i++) {
                double brickX = BRICK_COORDINATES[i][0];
                double brickY = BRICK_COORDINATES[i][1];
                StdDraw.setPenColor(BRICK_COLORS[i]);
                if (brickCoordinatesState[i][2] == -1) { // bricks which exist
                    StdDraw.filledRectangle(brickX, brickY, BRICK_HALFWIDTH, BRICK_HALFHEIGHT); // draw brick on the screen
                }
            }

            StdDraw.show(); // show the drawing on the screen
            StdDraw.pause(20); // pause the drawing at each iteration}
        }
    }
}