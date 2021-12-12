package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.events.EventManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class FileLevelGenerator implements LevelGenerator{
    private String filePath;

    public FileLevelGenerator(String path) {
        filePath = path;
    }

    @Override
    public Level generateLevel(EventManager events) {
        ArrayList<ArrayList<Character>> levelArray = readLevelFromFile();
        return createLevelFromArray(levelArray, events);
    }

    private Level createLevelFromArray(ArrayList<ArrayList<Character>> levelArray, EventManager events) {
        Player player = new Player(new GridPoint2(0, 0), 0f);
        ArrayList<Tree> trees = new ArrayList<>();
        for (int i = 0; i < levelArray.size(); i++) {
            for (int j = 0; j < levelArray.get(i).size(); j++) {
                char symbol = levelArray.get(i).get(j);
                if (symbol == 'T') {
                    trees.add(new Tree(new GridPoint2(j, i)));
                }
                else if (symbol == 'X') {
                    player = new Player(new GridPoint2(j, i), 0f);
                }
            }
        }
        final int height = levelArray.size();
        final int width = levelArray.get(0).size();
        return new Level(player, trees, height, width, events);
    }

    private ArrayList<ArrayList<Character>> readLevelFromFile() {
        ArrayList<ArrayList<Character>> levelArray = new ArrayList<>();
        try (FileInputStream fin = new FileInputStream(filePath))
        {
            int character = fin.read();
            while ( character != -1 ){
                ArrayList<Character> row = new ArrayList<>();
                while ( ((char)character != '\n') && (character != -1) ) {
                    row.add((char)character);
                    character = fin.read();
                }
                levelArray.add(row);
                character = fin.read();
            }
            Collections.reverse(levelArray);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        return levelArray;
    }
}
