package com.peoplehero.mauriciomartins.peoplehero.presenter;


import com.peoplehero.mauriciomartins.peoplehero.ViewModel.MapModelView;
import com.peoplehero.mauriciomartins.peoplehero.contract.Map;
import com.peoplehero.mauriciomartins.peoplehero.model.domain.Helpless;
import com.peoplehero.mauriciomartins.peoplehero.presenter.interactor.MapInteractor;

import java.util.List;

/**
 * Created by mauriciomartins on 15/07/17.
 */

public class MapPresenter implements Map.Presenter {
    private final Map.Interactor interactor;
    private Map.View view;
    private double latitude;
    private double longitude;
    private Long iduser;
    private String uid;
    private List<Helpless> helplessList;

    public MapPresenter(Map.View view){
        this.view = view;
        this.interactor = new MapInteractor(this);
    }
    @Override
    public void setHelpAsk() {
        this.view.showProgress(true);
        this.interactor.setHelpAsk(this.iduser,latitude,longitude);

    }

    @Override
    public void showMessage(String messsage) {
        this.view.showMessage(messsage);
    }

    @Override
    public void refresh() {
        this.interactor.refresh(latitude,longitude,iduser);
    }

    @Override
    public void updateHelpless(List<Helpless> helplessList) {
        this.helplessList = helplessList;
        this.view.updateHelpless(new MapModelView(this.view.getContext(),this,helplessList,this.latitude,this.longitude));
    }


    @Override
    public void confirmHelp(String idmendingo) {
        this.view.showProgress(true);

        final int index = this.helplessList.indexOf(new Helpless(idmendingo));
        if(index>-1) {
            final Helpless helpless = this.helplessList.get(index);
            helpless.setIduser(String.valueOf(this.iduser));
            this.interactor.confirmHelp(helpless);
        }
    }

    @Override
    public void saveLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public void saveUserInfo(Long iduser, String uid) {
        this.iduser = iduser;
        this.uid    = uid;
    }

    @Override
    public void route(String packageName, String url) {
        this.view.route(packageName,url);
    }

    @Override
    public double distance(double fromLatitude, double fromLongitude, double toLatitude, double toLongitude) {
        final int R = 6371; // Radius of the earth
        return R*Math.acos(Math.cos(Math.PI*(90-toLatitude)/180)*Math.cos((90-fromLatitude)*Math.PI/180)+Math.sin((90-toLatitude)*Math.PI/180)*Math.sin((90-fromLatitude)*Math.PI/180)*Math.cos((fromLongitude-toLongitude)*Math.PI/180));
    }

    @Override
    public void update() {
        if(this.helplessList!=null){
            this.view.updateHelpless(new MapModelView(this.view.getContext(),this,helplessList,this.latitude,this.longitude));
        }
    }

    @Override
    public void showNotification(int notificationId, String notificationTitle, String notificationIfo) {
        this.view.showNotification(notificationId, notificationTitle, notificationIfo);
    }

    @Override
    public void showSentHelpMessage() {
        this.view.showSentHelpMessage();
    }

    @Override
    public void showProgress(boolean visible) {
        this.view.showProgress(visible);
    }

    @Override
    public void updateHelp(Helpless helpless) {
        final int index = this.helplessList.indexOf(helpless);
        if(index>-1){
            this.helplessList.get(index).setContadorAjuda(1);
            this.updateHelpless(this.helplessList);
        }
    }

    @Override
    public void openCamera() {
        this.view.openCamera();
    }


    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }


}
