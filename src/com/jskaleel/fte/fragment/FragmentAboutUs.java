package com.jskaleel.fte.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jskaleel.fte.R;
import com.jskaleel.fte.common.BasicFragment;

public class FragmentAboutUs extends BasicFragment {

    
    public FragmentAboutUs() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aboutus, null);
    
        
        return view;
    }
}
