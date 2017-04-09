package com.kg.vista.beeserviceclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kg.vista.beeserviceclient.R;
import com.kg.vista.beeserviceclient.activity.LoginActivity;
import com.kg.vista.beeserviceclient.manager.SessionManager;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Vista on 14.02.2017.
 */

public class MyProfileFragment extends Fragment {

    @BindView(R.id.my_profile_name)
    TextView mProfileName;
    @BindView(R.id.my_profile_pass)
    TextView mProfilePass;

    @BindView(R.id.my_profile_logout) Button mProfileLogout;
    private Unbinder unbinder;
    SessionManager session;


    public MyProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);


        unbinder = ButterKnife.bind(this, view);

        session = new SessionManager(getActivity());

        HashMap<String, String> user = session.getUserDetails();
        String name = user.get(SessionManager.KEY_NAME);
        String verification_code = user.get(SessionManager.KEY_VERIFICATION_CODE);

        mProfileName.setText(name);

        mProfilePass.setText(verification_code);

        mProfileLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                session.logoutUser();
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);



            }
        });


        return view;
    }
}
