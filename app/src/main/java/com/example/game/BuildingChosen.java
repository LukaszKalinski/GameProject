package com.example.game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BuildingChosen extends AppCompatActivity {

    int clickedPosition;
    String clickedName;
    int clickedLevel;
    String clickedDescription;
    String clickedProduct;
    int clickedProductionTime;
    BuildingsDatabase buildingsDatabase = new BuildingsDatabase(this);

    TextView buildingDetailHeader;
    TextView buildingDetailDescription;
    TextView buildingDetailProduct;
    TextView buildingDetailProductionTime;
    TextView buildingDetailLevel;
    Button expandBuildingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.building_chosen);

        Bundle extras = getIntent().getExtras();
        clickedPosition = Integer.parseInt(extras.getString("sendingPosition"));
        clickedName = extras.getString("sendingName");
        clickedLevel = Integer.parseInt(extras.getString("sendingLevel"));
        clickedDescription = extras.getString("sendingDescription");
        clickedProduct = extras.getString("sendingProduct");
        clickedProductionTime = Integer.parseInt(extras.getString("sendingProductionTime"));
        System.out.println("SEND POSITION: " + String.valueOf(clickedPosition) +
                ", SEND NAME: " + clickedName +
                ", SEND LEVEL: " + String.valueOf(clickedLevel) +
                ", SEND DESCRIPTION: " + clickedDescription +
                ", SEND PRODUCT: " + clickedProduct +
                ", SEND PRODUCTIONTIME: " + String.valueOf(clickedProductionTime));

        buildingDetailHeader = (TextView) findViewById(R.id.buildingDetailHeader);
        buildingDetailHeader.setText(String.valueOf(clickedName));
        buildingDetailDescription = (TextView) findViewById(R.id.buildingDetailDescription);
        buildingDetailDescription.setText(String.valueOf(clickedDescription));
        buildingDetailProduct = (TextView) findViewById(R.id.buildingDetailProduct);
        buildingDetailProduct.setText(String.valueOf(clickedProduct));
        buildingDetailProductionTime = (TextView) findViewById(R.id.buildingDetailProductionTime);
        buildingDetailProductionTime.setText(String.valueOf(clickedProductionTime));
        buildingDetailLevel = (TextView) findViewById(R.id.buildingDetailLevel);
        buildingDetailLevel.setText(String.valueOf(clickedLevel));
        expandBuildingBtn = (Button) findViewById(R.id.expandBuildingBtn);

        expandBuildingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                raiseBuildingLevel(clickedName, clickedLevel, clickedProductionTime);
                finish();
            }
        });

    }

    public void raiseBuildingLevel(String name, int level, int productionTime){
        buildingsDatabase.open();
        buildingsDatabase.raiseCurrentBuildingLevelByOne(name, level, productionTime);
        buildingsDatabase.close();

        System.out.println("RAISED LEVEL IN DETAILS");
    }
}
