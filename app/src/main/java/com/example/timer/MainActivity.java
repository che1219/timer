package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ControllerFragment.OnFragmentListener{


    private TimeListFragment timeListFragment;
    private ControllerFragment controllerFragment;
    private ArrayList<String> record = new ArrayList<String>();
    private int index = 0;
    MyAsyncTask myAsyncTask;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controllerFragment= (ControllerFragment) getSupportFragmentManager().findFragmentById(R.id.ControllerFrag);
        timeListFragment= (TimeListFragment) getSupportFragmentManager().findFragmentById(R.id.ListFrag);
        myAsyncTask= new MyAsyncTask();
        for(int i = 0; i < 5; i++){
            record.add((i+1)+".00:00:00");
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("MyCount", count);
        outState.putStringArrayList("MyRecord",record);
        outState.putInt("Myindex", index);
        if (myAsyncTask.getStatus() == AsyncTask.Status.RUNNING){
            outState.putBoolean("running",true);

        }
        else{
            outState.putBoolean("running",false);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        count = savedInstanceState.getInt("MyCount");
        index = savedInstanceState.getInt("Myindex");
        controllerFragment.timer.setText(countToTime(count));
        record = savedInstanceState.getStringArrayList("MyRecord");
        if(timeListFragment!=null && timeListFragment.isInLayout()){
            timeListFragment.setText(record);
        }
        if(savedInstanceState.getBoolean("running")){
            controllerFragment.changeToStop();
            myAsyncTask.cancel(true);
            myAsyncTask = new MyAsyncTask();
            myAsyncTask.execute(60*60*24);
        }
    }
    @Override
    protected void onDestroy() {
        //checking if asynctask is still runnning
        if(myAsyncTask!=null && myAsyncTask.getStatus()== AsyncTask.Status.RUNNING){
            //cancel the task before destroying activity
            myAsyncTask.cancel(true);
            myAsyncTask= null;
        }
        super.onDestroy();
    }



    public void listSwitchClicked(View view){
        Intent intent = new Intent(this, TimeListActivity.class);
        intent.putExtra("info", record);
        startActivity(intent);
    }



    @Override
    public void onButtonClicked(int ID) {
        if(ID == 0) {
            if (myAsyncTask.getStatus() != AsyncTask.Status.RUNNING) {
                controllerFragment.changeToStop();
                myAsyncTask = new MyAsyncTask();
                myAsyncTask.execute(60*60*24);
            }
            else{
                controllerFragment.changeToStart();
                myAsyncTask.cancel(true);
            }
        }
        if(ID == 1){
            record.set(index % 5, (index + 1) + "." + this.countToTime(count));
            index++;
            if(timeListFragment!=null && timeListFragment.isInLayout()){
                timeListFragment.setText(record);
            }
        }
        if(ID == 2){
            count = 0;
            myAsyncTask.cancel(true);
            controllerFragment.timer.setText("00:00:00");
            controllerFragment.changeToStart();
            record.clear();
            for(int i = 0; i < 5; i++){
                record.add((i+1)+".00:00:00");
            }
            index = 0;
            if(timeListFragment!=null && timeListFragment.isInLayout()){
                timeListFragment.setText(record);
            }
        }
    }

    private class MyAsyncTask extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            while(count < params[0]){
                try{
                    //checking if the asynctask has been cancelled, end loop if so
                    if(isCancelled()) break;

                    Thread.sleep(1000);

                    count++;

                    //send count to onProgressUpdate to update UI
                    publishProgress(count);

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //setting count to 0 and setting textview to 0 after doInBackground finishes running
            count= 0;
            controllerFragment.timer.setText("00:00:00");

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            controllerFragment.timer.setText(countToTime(count));

        }



    }
    public String countToTime(int number){
        int hour = number / (60*60);
        int min = (number - hour * 60*60)/ 60;
        int sec = number % 60;

        String timeString = String.format("%02d:%02d:%02d",hour,min,sec);
        return timeString;
    }
}