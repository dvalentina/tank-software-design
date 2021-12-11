package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import org.awesome.ai.AI;
import org.awesome.ai.Action;
import org.awesome.ai.Recommendation;
import org.awesome.ai.state.GameState;
import org.awesome.ai.state.immovable.Obstacle;
import org.awesome.ai.state.movable.Bot;
import org.awesome.ai.state.movable.Orientation;
import org.awesome.ai.strategy.NotRecommendingAI;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GameAiAdapter{
    private AI ai = new NotRecommendingAI();

    public void moveOtherTanks(Player player, ArrayList<Tree> treeObstacles, ArrayList<Player> otherTanks, HashSet<GridPoint2> levelBorders) {
        List<Recommendation> recommendations = ai.recommend(getAiGameState(player, treeObstacles, otherTanks, levelBorders));
        for (Recommendation recommendation : recommendations) {
            Object objectSource = recommendation.getActor().getSource();
            if (otherTanks.contains(objectSource)) {
                Player tank = otherTanks.get(otherTanks.indexOf(objectSource));
                Action action = recommendation.getAction();
                Direction direction = getDirectionFromAction(action);

                ArrayList<Player> newOtherTanks = new ArrayList<>(otherTanks);
                newOtherTanks.remove(tank);
                newOtherTanks.add(player);

//                tank.move(direction, treeObstacles, newOtherTanks, levelBorders);
            }
        }
    }

    private Direction getDirectionFromAction(Action action) {
        Direction direction;
        switch (action) {
            case MoveNorth:
                direction = Direction.UP;
                break;
            case MoveWest:
                direction = Direction.LEFT;
                break;
            case MoveSouth:
                direction = Direction.DOWN;
                break;
            case MoveEast:
                direction = Direction.RIGHT;
                break;
            default:
                direction = Direction.RIGHT;
        }
        return direction;
    }

    private GameState getAiGameState(Player player, ArrayList<Tree> treeObstacles, ArrayList<Player> otherTanks, HashSet<GridPoint2> levelBorders) {
        GameState.GameStateBuilder builder = new GameState.GameStateBuilder()
                .player(getAiPlayer(player))
                .obstacles(getAiObstacles(treeObstacles))
                .bots(getAiBots(otherTanks))
                .levelHeight(getSizesFromBorders(levelBorders).y)
                .levelWidth(getSizesFromBorders(levelBorders).x);
        return builder.build();
    }

    private GridPoint2 getSizesFromBorders(HashSet<GridPoint2> levelBorders) {
        GridPoint2 size = new GridPoint2(0, 0);
        for (GridPoint2 tile : levelBorders) {
            if (tile.x > size.x) {
                size.x = tile.x;
            }
            if (tile.y > size.y) {
                size.y = tile.y;
            }
        }
        return size;
    }

    private Orientation getAiOrientation(float rotation) {
        Orientation orientation = Orientation.W;
        if (rotation == 90f) {
            orientation = Orientation.N;
        } else if (rotation == 180f) {
            orientation = Orientation.W;
        } else if (rotation == -90f) {
            orientation = Orientation.S;
        } else if (rotation == 0f) {
            orientation = Orientation.E;
        }
        return orientation;
    }

    private org.awesome.ai.state.movable.Player getAiPlayer(Player player) {
        org.awesome.ai.state.movable.Player.PlayerBuilder builder = new org.awesome.ai.state.movable.Player.PlayerBuilder()
                .source(player)
                .x(player.getCoordinates().x)
                .y(player.getCoordinates().y)
                .destX(player.getDestinationCoordinates().x)
                .destY(player.getCoordinates().y)
                .orientation(getAiOrientation(player.getRotation()));

        return (builder.build());
    }

    private List<Obstacle> getAiObstacles(ArrayList<Tree> treeObstacles) {
        List<Obstacle> obstacles = new ArrayList<>();
        for (Tree tree : treeObstacles) {
            obstacles.add(new Obstacle(tree.getCoordinates().x, tree.getCoordinates().y));
        }
        return obstacles;
    }

    private List<Bot> getAiBots(ArrayList<Player> otherTanks) {
        List<Bot> bots = new ArrayList<>();
        for (Player tank : otherTanks) {
            Bot.BotBuilder builder = new Bot.BotBuilder()
                    .source(tank)
                    .x(tank.getCoordinates().x)
                    .y(tank.getCoordinates().y)
                    .destX(tank.getDestinationCoordinates().x)
                    .destY(tank.getDestinationCoordinates().y)
                    .orientation(getAiOrientation(tank.getRotation()));
            bots.add(builder.build());
        }
        return bots;
    }
}
