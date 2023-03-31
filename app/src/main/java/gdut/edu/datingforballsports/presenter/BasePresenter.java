package gdut.edu.datingforballsports.presenter;

import java.lang.ref.WeakReference;

import gdut.edu.datingforballsports.model.Model_;
import gdut.edu.datingforballsports.view.View_;

public class BasePresenter {
    protected Model_ model;
    protected WeakReference<View_> viewReference;
}
