package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.HashSet;

public interface Game {
    void moveOtherTanks(Player player, ArrayList<Tree> treeObstacles, ArrayList<Player> otherTanks, HashSet<GridPoint2> levelBorders);
}
