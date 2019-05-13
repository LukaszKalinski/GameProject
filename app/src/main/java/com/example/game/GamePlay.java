package com.example.game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import static com.example.game.FirstLogin.firstChosenVillageType;
import static com.example.game.LoginActivity.loggedUserName;

public class GamePlay extends AppCompatActivity {

    BuildingsDatabase buildingsDatabase = new BuildingsDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_play);

        System.out.println("Logged as: " + loggedUserName);
        System.out.println("Village type is: " + firstChosenVillageType.getType());
        System.out.println("xCoord is: " + String.valueOf(firstChosenVillageType.coordX));
        System.out.println("yCoord is: " + String.valueOf(firstChosenVillageType.coordY));



        if (buildingsDatabase.databaseIsEmpty()){
            //if it's already exist
            System.out.println("DATABASE IS CREATED");

        } else {
            //if it doesn't exist!
            buildingsDatabase.open();
            buildingsDatabase.AddBuilding(
                    "Quarry",
                    String.valueOf(firstChosenVillageType.coordX),
                    String.valueOf(firstChosenVillageType.coordY),
                    "0",
                    "QuarryMakeStones",
                    "Stone",
                    "300"
            );
            buildingsDatabase.close();
        }
    }
}
