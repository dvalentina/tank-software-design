package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.stream.Stream;

@RunWith(Parameterized.class)
public class TreeTest {

    public static Stream<Arguments> dataForCreateTreeTest() {
        return Stream.of(
                Arguments.of(new GridPoint2(0, 0)),
                Arguments.of(new GridPoint2(6, 2))
        );
    }

    @ParameterizedTest
    @MethodSource("dataForCreateTreeTest")
    public void createTreeTest(GridPoint2 initialPosition) {
        Tree tree = new Tree(initialPosition);
        Assertions.assertEquals(tree.getCoordinates(), initialPosition);
    }
}
