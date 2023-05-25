package gdut.edu.datingforballsports.view.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {
    protected void buttonClick(View button, View.OnClickListener listener) {
        button.setOnClickListener(listener);
    }

    protected void buttonClick(int id, View.OnClickListener listener) {
        getView().findViewById(id).setOnClickListener(listener);
    }
}
