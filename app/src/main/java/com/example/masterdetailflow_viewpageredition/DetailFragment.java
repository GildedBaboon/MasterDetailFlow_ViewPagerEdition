package com.example.masterdetailflow_viewpageredition;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DetailFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private String mDetailsString;

    private DetailFragmentListener mCallback;

    private TextView mTextView;
    private Button mBackButton, mSaveButton;
    private EditText mAnswer;
    private Toast mtoast;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(String ds) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, ds);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDetailsString = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // get the views
        mTextView = view.findViewById(R.id.text_view);
        mBackButton = view.findViewById(R.id.back_button);
        mSaveButton = view.findViewById(R.id.save_button);
        mAnswer = view.findViewById(R.id.edit_text);

        // configure display & animation
        updateDisplay(mDetailsString);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ans = mAnswer.getText().toString();
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MasterDetailFlow", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(mDetailsString, ans);
                editor.apply();

                mtoast = Toast.makeText(getActivity().getApplicationContext(),
                        "Input Saved!",
                        Toast.LENGTH_SHORT);

                mtoast.show();
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnswer.setText("");
                mCallback.onDetailFragmentAction();
            }
        });
    }

    /* ------------------------------------*/
    /*   custom helper method              */
    /*   this is called by MainActivity    */

    public void updateDisplay(String s){
        mTextView.setText("What would you like to do for " + s + "");
        mDetailsString = s;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DetailFragmentListener) {
            mCallback = (DetailFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " (Main Activity) must implement DetailFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    public interface DetailFragmentListener {
        void onDetailFragmentAction();
    }
}
