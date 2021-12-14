package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.commands.*;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.D;

public class InputHandler {
    public static Command handlePlayerInput(Tank player, Level level) {
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            return new MoveUpCommand(player, level);
        }
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            return new MoveLeftCommand(player, level);
        }
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            return new MoveDownCommand(player, level);
        }
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            return new MoveRightCommand(player, level);
        }
        if (Gdx.input.isKeyJustPressed(SPACE)) {
            return new ShootCommand(player, level);
        }
        return new DoNothingCommand();
    }

    public static Command handleOtherInput(Graphics graphics) {
        if (Gdx.input.isKeyJustPressed(L)) {
            return new ToggleLivesCommand(graphics);
        }
        return new DoNothingCommand();
    }
}
