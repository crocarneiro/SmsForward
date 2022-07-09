package br.com.smsforward;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import br.com.smsforward.model.origin.Origin;
import br.com.smsforward.services.OriginService;

public class MainViewModel extends ViewModel {
    private OriginService originService;

    public MainViewModel() {
        this.originService = new OriginService();
    }

    private MutableLiveData origins;
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
}
