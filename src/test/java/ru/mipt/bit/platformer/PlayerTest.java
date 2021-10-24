package ru.mipt.bit.platformer;
import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.stream.Stream;

@RunWith(Parameterized.class)
public class PlayerTest {

    public static Stream<Arguments> dataForCreatePlayerTest() {
        return Stream.of(
                Arguments.of(new GridPoint2(0, 0), 0f),
                Arguments.of(new GridPoint2(5, 6), 90f)
        );
    }

    @ParameterizedTest
    @MethodSource("dataForCreatePlayerTest")
    public void createPlayerTest(GridPoint2 initialPosition, float rotation) {
        Player player = new Player(initialPosition, rotation);
        Assertions.assertEquals(player.getCoordinates(), initialPosition);
        Assertions.assertEquals(player.getDestinationCoordinates(), initialPosition);
        Assertions.assertEquals(player.getRotation(), rotation);
    }

    public static Stream<Arguments> dataForMoveTest() {
        GridPoint2 initialPlayerCoordinates = new GridPoint2(1, 1);
        ArrayList<Tree> trees = new ArrayList<>();
        trees.add(new Tree(new GridPoint2(1, 2)));
        ArrayList<Player> otherTanks = new ArrayList<>();
        otherTanks.add(new Player(new GridPoint2(2, 1), 0f));

        return Stream.of(
                Arguments.of(initialPlayerCoordinates, trees, otherTanks, Direction.DOWN, new GridPoint2(1, 0), 0f, -90f),
                Arguments.of(initialPlayerCoordinates, trees, otherTanks, Direction.UP, new GridPoint2(1, 1), 1f, 90f),
                Arguments.of(initialPlayerCoordinates, trees, otherTanks, Direction.RIGHT, new GridPoint2(1, 1), 1f, 0f)
        );
    }

    @ParameterizedTest
    @MethodSource("dataForMoveTest")
    public void moveTest(
            GridPoint2 initialPlayerCoordinates,
            ArrayList<Tree> trees,
            ArrayList<Player> otherTanks,
            Direction direction,
            GridPoint2 destinationCoordinates,
            float movementProgress,
            float rotation) {

        Player player = new Player(initialPlayerCoordinates, 0f);

        player.move(direction, trees, otherTanks);

        Assertions.assertEquals(player.getDestinationCoordinates(), destinationCoordinates);
        Assertions.assertEquals(player.getMovementProgress(), movementProgress);
        Assertions.assertEquals(player.getRotation(), rotation);
    }
}
