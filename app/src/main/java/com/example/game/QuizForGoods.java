package com.example.game;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizForGoods extends AppCompatActivity {

    GoodsDatabase goodsDatabase = new GoodsDatabase(this);
    public String[] goodsQuant;
    Button finishQuizBtn;
    TextView quizHeader2;
    boolean ifWon;

    int a;
    int b;
    int c;
    int d;
    int e;
    Button answer1;
    Button answer2;
    Button answer3;
    public int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_for_goods);

        ifWon = false;
        finishQuizBtn = (Button) findViewById(R.id.finishQuizBtn);
        quizHeader2 = (TextView) findViewById(R.id.quizHeader2);
        answer1 = (Button) findViewById(R.id.answer1);
        answer2 = (Button) findViewById(R.id.answer2);
        answer3 = (Button) findViewById(R.id.answer3);

        a = (int) (Math.random()*100);
        b = (int) (Math.random()*100);
        c = a + b;
        d = c - (int) (Math.max(Math.random()*3,1));
        e = c + (int) (Math.max(Math.random()*3,1));

        quizHeader2.setText(String.valueOf(a) + " + " + String.valueOf(b) + " = [?]");
        answer1.setText(String.valueOf(d));
        answer2.setText(String.valueOf(c));
        answer3.setText(String.valueOf(e));

        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer1.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                answer2.getBackground().clearColorFilter();
                answer3.getBackground().clearColorFilter();

                result = Integer.parseInt(answer1.getText().toString());
                ifWon = (c == result);
            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer1.getBackground().clearColorFilter();
                answer2.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                answer3.getBackground().clearColorFilter();
                result = Integer.parseInt(answer2.getText().toString());
                ifWon = (c == result);
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer1.getBackground().clearColorFilter();
                answer2.getBackground().clearColorFilter();
                answer3.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                result = Integer.parseInt(answer3.getText().toString());
                ifWon = (c == result);
            }
        });


        finishQuizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //QUIZ
                ifWinsQuiz();
            }
        });

    }

    public void ifWinsQuiz(){
        if (ifWon){
            Toast.makeText(QuizForGoods.this,"YOU HAVE WON", Toast.LENGTH_LONG).show();
            getGoodsQuant();
            raiseGoodsQuant(100,100,100,100);
            finish();
        } else {
            Toast.makeText(QuizForGoods.this,"Sorry, try next time", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void getGoodsQuant(){
        int stoneQuant = 1;
        int woodQuant = 2;
        int foodQuant = 3;
        int waterQuant = 4;
        goodsDatabase.open();
        Cursor cursor = goodsDatabase.getRecords();
        goodsQuant = new String[6];
        cursor.moveToFirst();
        goodsQuant[0] = cursor.getString(stoneQuant);
        goodsQuant[1] = cursor.getString(woodQuant);
        goodsQuant[2] = cursor.getString(foodQuant);
        goodsQuant[3] = cursor.getString(waterQuant);
        cursor.close();
        goodsDatabase.close();
    }

    public void raiseGoodsQuant(int newStoneQuant, int newWoodQuant, int newFoodQuant, int newWaterQuant){
        goodsDatabase.open();
        goodsDatabase.raiseGoods(Integer.parseInt(goodsQuant[0]),
                Integer.parseInt(goodsQuant[1]),
                Integer.parseInt(goodsQuant[2]),
                Integer.parseInt(goodsQuant[3]),
                newStoneQuant,
                newWoodQuant,
                newFoodQuant,
                newWaterQuant);
        goodsDatabase.close();
    }
}
