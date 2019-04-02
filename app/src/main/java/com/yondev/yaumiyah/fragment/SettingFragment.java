package com.yondev.yaumiyah.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yondev.yaumiyah.R;

/**
 * Created by ThinkPad on 5/6/2017.
 */

public class SettingFragment extends Fragment{
    private View viewroot;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        viewroot = inflater.inflate(R.layout.view_setting, container, false);

        return viewroot;
    }
}
