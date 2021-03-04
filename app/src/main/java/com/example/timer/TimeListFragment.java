package com.example.timer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class TimeListFragment extends Fragment {

    private TextView listOne;
    private TextView listTwo;
    private TextView listThree;
    private TextView listFour;
    private TextView listFive;
    private ArrayList<TextView> textList = new ArrayList<TextView>();


    public TimeListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_time_list, container, false);

        listOne = view.findViewById(R.id.listOne);
        textList.add(listOne);
        listTwo = view.findViewById(R.id.listTwo);
        textList.add(listTwo);
        listThree = view.findViewById(R.id.listThree);
        textList.add(listThree);
        listFour = view.findViewById(R.id.listFour);
        textList.add(listFour);
        listFive = view.findViewById(R.id.listFive);
        textList.add(listFive);

        return view;
    }

    public void setText(ArrayList<String> stringList) {

        for(int i = 0; i < 5; i++){
            textList.get(i).setText(stringList.get(i));
        }

    }
}