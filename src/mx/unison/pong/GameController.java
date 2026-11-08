package mx.unison.pong;

import javax.swing.*;

public class GameController {
    private final GameModel model;
    private final Board board;
    private final Timer timer;

    public GameController(GameModel model, Board board) {
        this.model = model;
        this.board = board;
        this.timer = new Timer(16, e -> gameLoop());
    }

    public void start() {
        model.setState(GameState.RUNNING);
        timer.start();
    }

    public void pause() {
        if (model.getState() == GameState.RUNNING) {
            model.setState(GameState.PAUSED);
        }
    }

    public void resume() {
        if (model.getState() == GameState.PAUSED) {
            model.setState(GameState.RUNNING);
        }
    }

    public void stop() {
        model.setState(GameState.STOPPED);
        timer.stop();
    }

    private void gameLoop() {
        if (model.getState() == GameState.RUNNING) {
            updatePhysics();
        }
        board.repaint();
    }

    private void updatePhysics() {
        if (model.isMovingLeft()) {
            model.setPaddleX(Math.max(0, model.getPaddleX() - model.getPaddleSpeed()));
        }
        if (model.isMovingRight()) {
            model.setPaddleX(Math.min(GameModel.MAX_X - model.getPaddleWidth(), model.getPaddleX() + model.getPaddleSpeed()));
        }

        double nextX = model.getBallX() + model.getBallDX();
        double nextY = model.getBallY() + model.getBallDY();

        if (nextX <= 0) {
            nextX = 0;
            model.setBallDX(-model.getBallDX());
        } else if (nextX + model.getBallSize() >= GameModel.MAX_X) {
            nextX = GameModel.MAX_X - model.getBallSize();
            model.setBallDX(-model.getBallDX());
        }

        if (nextY <= 0) {
            nextY = 0;
            model.setBallDY(-model.getBallDY());
        }

        if (model.getBallDY() > 0 &&
                nextY + model.getBallSize() >= model.getPaddleY() &&
                nextY <= model.getPaddleY() + model.getPaddleHeight() &&
                nextX + model.getBallSize() >= model.getPaddleX() &&
                nextX <= model.getPaddleX() + model.getPaddleWidth()) {

            nextY = model.getPaddleY() - model.getBallSize();
            model.setBallDY(-model.getBallDY());
        }

        if (nextY >= GameModel.MAX_Y) {
            model.setState(GameState.LOST);
        }

        model.setBallX(nextX);
        model.setBallY(nextY);
    }
}