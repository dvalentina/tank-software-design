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
import ru.mipt.bit.platformer.commands.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GameAiAdapter implements Game{
    private AI ai = new NotRecommendingAI();

    private GameState getAiGameState(Level level) {
        GameState.GameStateBuilder builder = new GameState.GameStateBuilder()
                .player(getAiPlayer(level.getPlayer()))
                .obstacles(getAiObstacles(level.getTreeObstacles()))
                .bots(getAiBots(level.getOtherTanks()))
                .levelHeight(getSizesFromBorders(level.getBorders()).y)
                .levelWidth(getSizesFromBorders(level.getBorders()).x);
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

    private org.awesome.ai.state.movable.Player getAiPlayer(Tank player) {
        org.awesome.ai.state.movable.Player.PlayerBuilder builder = new org.awesome.ai.state.movable.Player.PlayerBuilder()
                .source(player)
                .x(player.getCoordinates().x)
                .y(player.getCoordinates().y)
                .destX(player.getDestinationCoordinates().x)
                .destY(player.getCoordinates().y)
                .orientation(getAiOrientation(player.getRotation()));

        return (builder.build());
    }

    private List<Obstacle> getAiObstacles(List<Tree> treeObstacles) {
        List<Obstacle> obstacles = new ArrayList<>();
        for (Tree tree : treeObstacles) {
            obstacles.add(new Obstacle(tree.getCoordinates().x, tree.getCoordinates().y));
        }
        return obstacles;
    }

    private List<Bot> getAiBots(ArrayList<Tank> otherTanks) {
        List<Bot> bots = new ArrayList<>();
        for (Tank tank : otherTanks) {
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

    @Override
    public ArrayDeque<Command> generateOtherTanksCommands(Level level) {
        ArrayDeque<Command> commands = new ArrayDeque<>();
        ArrayList<Tank> otherTanks = level.getOtherTanks();

        List<Recommendation> recommendations = ai.recommend(getAiGameState(level));
        for (Recommendation recommendation : recommendations) {
            Object objectSource = recommendation.getActor().getSource();
            if (otherTanks.contains(objectSource)) {
                Tank tank = otherTanks.get(otherTanks.indexOf(objectSource));
                Action action = recommendation.getAction();

                switch (action) {
                    case MoveNorth:
                        commands.add(new MoveUpCommand(tank, level));
                        break;
                    case MoveWest:
                        commands.add(new MoveLeftCommand(tank, level));
                        break;
                    case MoveSouth:
                        commands.add(new MoveDownCommand(tank, level));
                        break;
                    case MoveEast:
                        commands.add(new MoveRightCommand(tank, level));
                        break;
                }
            }
        }
        return commands;
    }
}
