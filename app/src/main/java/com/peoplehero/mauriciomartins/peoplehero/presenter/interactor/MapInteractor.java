package com.peoplehero.mauriciomartins.peoplehero.presenter.interactor;

import android.util.Log;

import com.peoplehero.mauriciomartins.peoplehero.contract.Map;
import com.peoplehero.mauriciomartins.peoplehero.model.domain.Helpless;
import com.peoplehero.mauriciomartins.peoplehero.model.dto.HelpDTO;
import com.peoplehero.mauriciomartins.peoplehero.model.dto.HelplessListDTO;
import com.peoplehero.mauriciomartins.peoplehero.model.service.PeopleHeroService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by mauriciomartins on 15/07/17.
 */

public class MapInteractor implements Map.Interactor {

    private final Map.Presenter presenter;

    public MapInteractor(Map.Presenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void refresh(double latitude, double longitude, double idUser) {
        PeopleHeroService peopleHeroService = new PeopleHeroService();
        Call<HelplessListDTO> repos = peopleHeroService.setHelp(latitude,longitude,idUser);

        repos.enqueue(new Callback<HelplessListDTO>() {
            @Override
            public void onResponse(Call<HelplessListDTO> call, Response<HelplessListDTO> response) {

                if(response!=null&&response.raw()!=null&& response.raw().code()==200){
                    HelplessListDTO help = (HelplessListDTO) response.body();
                    presenter.updateHelpless(help.getHelp());
                }
                else{
                    presenter.showMessage("Retrofit Service - Erro : "+response.raw().code());
                }
            }

            @Override
            public void onFailure(Call<HelplessListDTO> call, Throwable t) {
                Log.i("Retrofit Service","Response:"+t.getMessage());
                presenter.showMessage("Retrofit Service - Response:"+t.getMessage());
            }
        });
    }

    @Override
    public void setHelpAsk(Long idUser,double latitude, double longitude) {
        PeopleHeroService peopleHeroService = new PeopleHeroService();
        Call<HelpDTO> repos = peopleHeroService.setHelpAsk(idUser ,latitude,longitude);
        repos.enqueue(new Callback<HelpDTO>() {
            @Override
            public void onResponse(Call<HelpDTO> call, Response<HelpDTO> response) {

                if(response!=null&&response.raw()!=null&& response.raw().code()==200){
                    HelpDTO help = response.body();
                    presenter.showMessage("Pedido de ajuda realizado com sucesso!!!");
                }
                else{
                    Log.i("Retrofit Service","Erro : "+response.raw().code());
                    presenter.showMessage("Retrofit Service - Erro : "+response.raw().code());
                }
            }

            @Override
            public void onFailure(Call<HelpDTO> call, Throwable t) {
                Log.i("Retrofit Service","Response:"+t.getMessage());
                presenter.showMessage("Retrofit Service - Response:"+t.getMessage());
            }
        });
    }

    @Override
    public void confirmHelp(Helpless helpless) {
        PeopleHeroService peopleHeroService = new PeopleHeroService();
        Call<Helpless> repos = peopleHeroService.confirmHelp(helpless);

        repos.enqueue(new Callback<Helpless>() {
            @Override
            public void onResponse(Call<Helpless> call, Response<Helpless> response) {

                if(response!=null&&response.raw()!=null&& response.raw().code()==200){
                    Helpless helpless = response.body();
                    if(helpless!=null){
                        presenter.updateHelp(helpless);
                    }
                    presenter.showSentHelpMessage();
                }
                else{
//                    presenter.showSentHelpMessage();
                    Log.i("Retrofit Service","Erro : "+response.raw().code());
                    presenter.showMessage("Retrofit Service - Erro : "+response.raw().code());
                }
            }

            @Override
            public void onFailure(Call<Helpless> call, Throwable t) {
                Log.i("Retrofit Service","Response:"+t.getMessage());
                presenter.showSentHelpMessage();
                //presenter.showMessage("Retrofit Service - Response:"+t.getMessage());
            }
        });
    }
}
