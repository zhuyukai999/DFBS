package gdut.edu.datingforballsports.presenter;

import java.lang.ref.WeakReference;

import gdut.edu.datingforballsports.model.HomePageModel;
import gdut.edu.datingforballsports.model.MessageModel;
import gdut.edu.datingforballsports.view.HomePageView;
import gdut.edu.datingforballsports.view.MessageView;
import gdut.edu.datingforballsports.view.View_;

public class MessagePresenter extends BasePresenter{
    public MessagePresenter(MessageView messageView) {
        this.model = new MessageModel();
        this.viewReference = new WeakReference<View_>(messageView);
    }
}
