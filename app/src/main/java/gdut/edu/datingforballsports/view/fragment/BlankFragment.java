package gdut.edu.datingforballsports.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import gdut.edu.datingforballsports.R;

public class BlankFragment extends Fragment {
    private static final String ARG_TEXT = "param1";
    private String mTextString;
    View rootView;
    public BlankFragment() {
    }
    public static BlankFragment newInstance(String param1){
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT,param1);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTextString = getArguments().getString(ARG_TEXT);
        }
    }
/*    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null){
            rootView = inflater.inflate(R.layout.fragment_blank,container,false);
        }
        InitView();
        return rootView;
    }
    private void InitView() {
        TextView textView = rootView.findViewById(R.id.text);
        textView.setText(mTextString);
    }*/
}
