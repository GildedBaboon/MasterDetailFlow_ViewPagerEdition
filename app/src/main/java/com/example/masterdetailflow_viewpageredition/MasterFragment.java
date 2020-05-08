package com.example.masterdetailflow_viewpageredition;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MasterFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";

    private String mParam1;
    private TextView mViewT1;
    private TextView mViewT2;
    private TextView mViewT3;
    private TextView mViewTasks;
    private Button mButtonUpdate;

    private Gson mGson;


    private MasterFragmentListener mCallback;

    public MasterFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static MasterFragment newInstance(String param1) {
        MasterFragment fragment = new MasterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_master, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewT1 = view.findViewById(R.id.v_task1);
        mViewT2 = view.findViewById(R.id.v_task2);
        mViewT3 = view.findViewById(R.id.v_task3);
        mViewTasks = view.findViewById(R.id.currentTasks);
        mButtonUpdate = view.findViewById(R.id.update_button);

        mViewT1.setOnClickListener(this);
        mViewT2.setOnClickListener(this);
        mViewT3.setOnClickListener(this);

        mGson = new GsonBuilder().create();

        updateTasks();

        mButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTasks();
            }
        });

    }

    @Override
    public void onClick(View v) {

        int itemNo = -1;

        switch (v.getId()) {
            case R.id.v_task1:
                itemNo = 0;
                break;
            case R.id.v_task2:
                itemNo = 1;
                break;
            case R.id.v_task3:
                itemNo = 2;
                break;
            default:
                break;
        }
        mCallback.onMasterFragmentData(itemNo);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MasterFragmentListener) {
            mCallback = (MasterFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " (MainActivity) must implement MasterFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface MasterFragmentListener {
        void onMasterFragmentData(int i);
    }

    public void updateTasks() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MasterDetailFlow", Context.MODE_PRIVATE);
        String task1 = sharedPreferences.getString("Task1", "");
        String task2 = sharedPreferences.getString("Task2", "");
        String task3 = sharedPreferences.getString("Task3", "");

        mViewTasks.setText("Task 1: " + task1 + "\n Task 2: " + task2 + "\n Task 3: " + task3);
    }
}
