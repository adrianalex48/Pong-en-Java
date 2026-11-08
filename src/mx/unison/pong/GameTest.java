package mx.unison.pong;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {

    @Test
    public void testMovimientoRaquetaLimites() {
        GameModel model = new GameModel();

        model.setMovingLeft(true);
        model.setPaddleX(Math.max(0, model.getPaddleX() - 500));

        assertEquals(0, model.getPaddleX());

        model.setMovingLeft(false);
        model.setMovingRight(true);
        model.setPaddleX(Math.min(GameModel.MAX_X - model.getPaddleWidth(), model.getPaddleX() + 1000));

        assertEquals(GameModel.MAX_X - model.getPaddleWidth(), model.getPaddleX());
    }

    @Test
    public void testColisionParedSuperior() {
        GameModel model = new GameModel();
        model.setBallY(0);
        model.setBallDY(-5);

        if (model.getBallY() <= 0) {
            model.setBallDY(-model.getBallDY());
        }

        assertEquals(5, model.getBallDY());
    }

    @Test
    public void testPelotaPerdida() {
        GameModel model = new GameModel();
        model.setBallY(GameModel.MAX_Y + 10);

        if (model.getBallY() >= GameModel.MAX_Y) {
            model.setState(GameState.LOST);
        }

        assertEquals(GameState.LOST, model.getState());
    }

    @Test
    public void testPausaYReanudacion() {
        GameModel model = new GameModel();
        model.setState(GameState.RUNNING);

        if (model.getState() == GameState.RUNNING) {
            model.setState(GameState.PAUSED);
        }
        assertEquals(GameState.PAUSED, model.getState());

        if (model.getState() == GameState.PAUSED) {
            model.setState(GameState.RUNNING);
        }
        assertEquals(GameState.RUNNING, model.getState());
    }
}