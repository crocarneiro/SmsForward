package br.com.smsforward;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import br.com.smsforward.model.integration_destiny.IntegrationDestiny;
import br.com.smsforward.model.origin.Origin;
import br.com.smsforward.services.IntegrationDestinyService;
import br.com.smsforward.services.OriginService;

public class MainViewModel extends ViewModel {
    private final OriginService originService;
    private final IntegrationDestinyService integrationDestinyService;

    public MainViewModel() {
        this.originService = new OriginService();
        this.integrationDestinyService = new IntegrationDestinyService();
    }

    private MutableLiveData<List<Origin>> origins;
    public LiveData<List<Origin>> getOrigins() {
        if(origins == null) {
            origins = new MutableLiveData<>();
            origins.setValue(originService.findAllOrigins());
        }

        return origins;
    }

    public void setOrigins(List<Origin> origins) {
        this.origins.setValue(origins);
    }

    public void reloadOrigins() {
        origins.setValue(originService.findAllOrigins());
    }

    private MutableLiveData<List<IntegrationDestiny>> integrationDestinies;
    public LiveData<List<IntegrationDestiny>> getIntegrationDestinies() {
        if(integrationDestinies == null) {
            integrationDestinies = new MutableLiveData<>();
            integrationDestinies.setValue(integrationDestinyService.findAllIntegrationDestinies());
        }

        return integrationDestinies;
    }

    public void reloadIntegrationDestinies() {
        integrationDestinies.setValue(integrationDestinyService.findAllIntegrationDestinies());
    }
}
