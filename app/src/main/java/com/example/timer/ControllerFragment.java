package com.example.timer;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ControllerFragment extends Fragment implements View.OnClickListener {

    private Button start;
    private Button lap;
    private Button reset;
    public TextView timer;

    private OnFragmentListener listener;

    public ControllerFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_controller, container, false);
        start = (Button) view.findViewById(R.id.start);
        lap= (Button) view.findViewById(R.id.lap);
        reset = (Button) view.findViewById(R.id.reset);
        timer= (TextView) view.findViewById(R.id.timer);

        //add listeners
        start.setOnClickListener(this);
        lap.setOnClickListener(this);
        reset.setOnClickListener(this);
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof OnFragmentListener){
            this.listener= (OnFragmentListener) context;
        }else{
            throw new RuntimeException(context.toString()+" must implement OnFragmentInteractionListener");
        }

    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == start.getId()){

            listener.onButtonClicked(0);

        }else if (view.getId() == lap.getId()){

            listener.onButtonClicked(1);

        } else if(view.getId() == reset.getId() ){

            listener.onButtonClicked(2);
        }
    }

    public interface OnFragmentListener{
        void onButtonClicked(int ID);
    }

    public void changeToStop(){
        start.setText("Stop");
    }

    public void changeToStart(){
        start.setText("Start");
    }
}