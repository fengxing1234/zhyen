package com.zhyen.test.ui.test_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zhyen.test.R;


public class TestCustomViewFragment extends Fragment {

    public static final String ARGS_SAMPLE_LAYOUT_RES = "sampleLayoutRes";
    public static final String ARGS_PRACTICE_LAYOUT_RES = "practiceLayoutRes";
    private int sampleLayoutRes;
    private int practiceLayoutRes;

    public static TestCustomViewFragment getInstance(int sampleLayoutRes, int practiceLayoutRes) {
        TestCustomViewFragment fragment = new TestCustomViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_SAMPLE_LAYOUT_RES, sampleLayoutRes);
        args.putInt(ARGS_PRACTICE_LAYOUT_RES, practiceLayoutRes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();

        if (args != null) {
            sampleLayoutRes = args.getInt(ARGS_SAMPLE_LAYOUT_RES);
            practiceLayoutRes = args.getInt(ARGS_PRACTICE_LAYOUT_RES);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_view_fragment_page, container, false);

        ViewStub sampleStub = (ViewStub) view.findViewById(R.id.sampleStub);
        sampleStub.setLayoutResource(sampleLayoutRes);
        sampleStub.inflate();

//        ViewStub practiceStub = (ViewStub) view.findViewById(R.id.practiceStub);
//        practiceStub.setLayoutResource(practiceLayoutRes);
//        practiceStub.inflate();

        return view;
    }
}
