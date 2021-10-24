package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class FileLevelGenerator implements LevelGenerator{
    private ArrayList<ArrayList<Character>> levelArray = new ArrayList<>();
    private String filePath;
    private Player player;
    private ArrayList<Tree> trees = new ArrayList<Tree>();

    public FileLevelGenerator(String path) {
        filePath = path;
    }

    @Override
    public Level generateLevel() {
        readLevelFromFile();
        createLevelFromArray();
        return new Level(player, trees);
    }

    private void createLevelFromArray() {
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
    }

    private void readLevelFromFile() {
        try (FileInputStream fin = new FileInputStream(filePath))
        {
            int character = fin.read();
            while ( character != -1 ){
                ArrayList<Character> row = new ArrayList<Character>();
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
    }
}
