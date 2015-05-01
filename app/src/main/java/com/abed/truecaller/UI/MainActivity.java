package com.abed.truecaller.UI;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.abed.truecaller.Controllers.ApiManager;
import com.abed.truecaller.Controllers.ResponseProcessors.ResponseProcessor;
import com.abed.truecaller.Controllers.ResponseProcessors.ResponseProcessor10thChar;
import com.abed.truecaller.Controllers.ResponseProcessors.ResponseProcessorEvery10thChar;
import com.abed.truecaller.Controllers.ResponseProcessors.ResponseProcessorWordCounter;
import com.abed.truecaller.R;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends ActionBarActivity {

    private ProgressBar progressBar;
    private int response_num=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar= (ProgressBar)findViewById(R.id.progress);
        FloatingActionButton fab_btn=(FloatingActionButton)findViewById(R.id.fab);
        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ApiManager.checkInternetConnection(MainActivity.this))
                    fetchData();
                else
                    Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void fetchData()
    {
        String url="http://www.truecaller.com/support";
        progressBar.setVisibility(View.VISIBLE);
        //Builder Design Pattern.
        ApiManager.with(this,url)
                .processResponceWith(new ResponseProcessor10thChar() // Strategy Design Pattern
                                .setListener(new ResponseProcessor.DataProcessListener<Character>() {// Observer Design Pattern
                                                 @Override
                                                 public void dataProcessedSuccessfully(Character response) {
                                                     TextView txt=(TextView)findViewById(R.id.txt1);
                                                     txt.setText("'"+response.toString()+"'");
                                                     hideProgress();
                                                 }
                                             }
                                )
                )
                .build();


        ApiManager.with(this, url)
                .processResponceWith(new ResponseProcessorEvery10thChar() // Strategy Design Pattern
                                .setListener(new ResponseProcessor.DataProcessListener<ArrayList<Character>>() {
                                                 @Override
                                                 public void dataProcessedSuccessfully(ArrayList<Character> response) {
                                                     TextView txt=(TextView)findViewById(R.id.txt2);
                                                     txt.setText(response.toString());
                                                     hideProgress();
                                                 }
                                             }
                                )
                )
                .build();


        ApiManager.with(this, url)
                .processResponceWith(new ResponseProcessorWordCounter() // Strategy Design Pattern
                                .setListener(new ResponseProcessor.DataProcessListener<HashMap<String, Integer>>() {
                                                 @Override
                                                 public void dataProcessedSuccessfully(HashMap<String, Integer> response) {
                                                     TextView txt=(TextView)findViewById(R.id.txt3);
                                                     int truecaller_counter=0;
                                                     if(response.containsKey("truecaller"))
                                                         truecaller_counter=response.get("truecaller");
                                                     txt.setText("Truecaller count: "+truecaller_counter+"\n"+response.toString());
                                                     hideProgress();
                                                 }
                                             }
                                )
                )
                .build();
    }

    public void hideProgress()
    {
        response_num++;
        if(response_num==3) {
            progressBar.setVisibility(View.GONE);
            response_num=0;
        }
    }

}
