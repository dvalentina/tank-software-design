package ru.mipt.bit.platformer;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

@RunWith(Parameterized.class)
public class LevelTest {

    public static Stream<Arguments> dataForCreateLevelTest() {
        final int height = 11;
        final int width = 7;
        return Stream.of(
                Arguments.of(new Player(new GridPoint2(0, 0), 0f),
                        new ArrayList<Tree>(Arrays.asList(new Tree(new GridPoint2(2, 2)))),
                        height, width),
                Arguments.of(new Player(new GridPoint2(6, 1), 90f),
                        new ArrayList<Tree>(Arrays.asList(new Tree(new GridPoint2(3, 6)),
                                new Tree(new GridPoint2(3, 6)))),
                        height, width)
        );
    }

    @ParameterizedTest
    @MethodSource("dataForCreateLevelTest")
    public void createLevelTest(Player player, ArrayList<Tree> trees, int height, int width) {
        Level level = new Level(player, trees, height, width);
        Assertions.assertEquals(level.getPlayer(), player);
        Assertions.assertEquals(level.getTreeObstacles(), trees);
    }
}
