package br.com.smsforward.services;

import java.util.List;
import br.com.smsforward.data.Database;
import br.com.smsforward.model.origin.Origin;
import br.com.smsforward.repositories.OriginRepository;

public class OriginService {
    private OriginRepository originRepository;

    public OriginService() {
        this.originRepository = Database.getDatabase().originRepository();
    }

    public void insertOrigin(String address) {
        Origin origin = new Origin(address);
        Database.getDatabase().originRepository().insertOrigin(origin);
    }

    public Origin findOriginByAddress(String address) {
        return originRepository.findOriginByAddress(address);
    }

    public void deleteOrigin(Origin origin) {
        originRepository.deleteOrigin(origin);
    }

    public List<Origin> findAllOrigins() {
        return originRepository.findAllOrigins();
    }
}
