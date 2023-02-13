package desarrollointerfaces.examen.pong;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

// Esta implementación contiene un problema de funcionalidad dado que a veces no detecta la bola a la pala.
// Si no está en movimiento tampoco dectecta la bola a la pala

public class HelloApplication extends Application {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 600;

    private double ballX = 500;
    private double ballY = 300;

    //Velocidad Bola
    private double VelocidadBallX = 3;
    private double VelocidadBallY = 3;

    //Bola
    private Circle ball;

    //Palas
    private double paddle1Y = HEIGHT / 2;
    private Rectangle palaI;
    private double paddle2Y = HEIGHT / 2;
    private Rectangle palaD;

    //Teclas de movimiento
    private boolean upPressed;
    private boolean downPressed;
    private boolean wPressed;
    private boolean sPressed;

    //Puntuación
    int score1 = 0;
    int score2 = 0;

    @Override
    public void start(Stage primaryStage) {
        //Declaración y creación de los elementos
        ball = new Circle(ballX, ballY, 10, Color.WHITE);

        palaI = new Rectangle(10, paddle1Y, 10, 100);
        palaI.setFill(Color.WHITE);

        palaD = new Rectangle(WIDTH - 20, paddle2Y, 10, 100);
        palaD.setFill(Color.WHITE);

        //Gestión del los records o puntuación
        Text score = new Text();
        //score.setStyle("-fx-text-inner-color: red;");
        score.setFont(Font.font(30));
        score.setX(350);
        score.setY(90);

        //Creación del panel y añadimos elementos
        Pane root = new Pane();
        root.getChildren().addAll(ball, palaI, palaD);

        //Creación de la escena y color de la misma
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.setFill(Color.PURPLE);

        //Control de las teclas. Controla si están presionadas o no
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.W) {
                upPressed = true;
            } else if (event.getCode() == KeyCode.S) {
                downPressed = true;
            } else if (event.getCode() == KeyCode.UP) {
                wPressed = true;
            } else if (event.getCode() == KeyCode.DOWN) {
                sPressed = true;
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.W) {
                upPressed = false;
            } else if (event.getCode() == KeyCode.S) {
                downPressed = false;
            } else if (event.getCode() == KeyCode.UP) {
                wPressed = false;
            } else if (event.getCode() == KeyCode.DOWN) {
                sPressed = false;
            }
        });

        //Funcionalidad base del juego
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            if (upPressed) {
                paddle1Y -= 8;
            }
            if (downPressed) {
                paddle1Y += 8;
            }
            if (wPressed) {
                paddle2Y -= 8;
            }
            if (sPressed) {
                paddle2Y += 8;
            }
            ballX += VelocidadBallX;
            ballY += VelocidadBallY;

            if (ballX <= 35) {
                if (ballY > paddle1Y && ballY < paddle1Y + 50) {
                    VelocidadBallX = -VelocidadBallX;
                } else {
                    score1++;
                    score.setText("Player 1: " + score2 + "  Player 2: " + score1);
                    ballX = 500;
                    ballY = 300;
                    VelocidadBallX = 3;
                    VelocidadBallY = 3;
                }
            }
            if (ballX >= 972) {
                if (ballY > paddle2Y && ballY < paddle2Y + 50) {
                    VelocidadBallX = -VelocidadBallX;
                } else {
                    score2++;
                    score.setText("Player 1: " + score2 + "  Player 2: " + score1);
                    ballX = 500;
                    ballY = 300;
                    VelocidadBallX = 3;
                    VelocidadBallY = 3;
                }
            }
            if (ballY <= 10 || ballY >= 500) {
                VelocidadBallY = -VelocidadBallY;
            }
            if (paddle1Y <= 0) {
                paddle1Y = 0;
            } else if (paddle1Y >= 500) {
                paddle1Y = 500;
            }

            if (paddle2Y <= 0) {
                paddle2Y = 0;
            } else if (paddle2Y >= 500) {
                paddle2Y = 500;
            }
            palaI.setY(paddle1Y);
            palaD.setY(paddle2Y);
            ball.setCenterX(ballX);
            ball.setCenterY(ballY);
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        score.setText("Player 1: " + score2 + "  Player 2: " + score1);
        root.getChildren().add(score);


        primaryStage.setTitle("PONG");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}