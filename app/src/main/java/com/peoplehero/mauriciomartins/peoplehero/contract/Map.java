package com.peoplehero.mauriciomartins.peoplehero.contract;

import android.content.Context;

import com.peoplehero.mauriciomartins.peoplehero.ViewModel.MapModelView;
import com.peoplehero.mauriciomartins.peoplehero.model.domain.Helpless;

import java.util.List;

/**
 * Created by mauriciomartins on 15/07/17.
 */

public interface Map {
    static interface View{
        void showMessage(String message);
        void updateHelpless(MapModelView help);
        Context getContext();
        void showProgress(boolean show);
        void route(String latitude, String longitude);
        void showNotification(int notificationId, String notificationTitle, String notificationIfo);
        void showSentHelpMessage();

        void openCamera();
    }

    static interface Presenter{
        void setHelpAsk();
        void showMessage(String messsage);
        void refresh();
        void updateHelpless(List<Helpless> helplessList);
        void confirmHelp(String idmendingo);
        void saveLocation(double latitude, double longitude);
        void saveUserInfo(Long iduser, String uid);
        void route(String latitude, String longitude);
        double distance(double D2, double D3, double E2, double E3);
        void update();
        void showNotification(int notificationId, String notificationTitle, String notificationIfo);
        void showSentHelpMessage();
        void showProgress(boolean visible);
        void updateHelp(Helpless idmendingo);
        void openCamera();

    }

    static interface Interactor{

        void setHelpAsk(Long idUser,double latitude, double longitude);

        void refresh(double latitude, double longitude, double idUser);

        void confirmHelp(Helpless helpless);
    }
}
