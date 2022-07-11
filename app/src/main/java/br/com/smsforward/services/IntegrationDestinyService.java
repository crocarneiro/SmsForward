package br.com.smsforward.services;

import java.util.List;

import br.com.smsforward.data.Database;
import br.com.smsforward.model.integration_destiny.IntegrationDestiny;
import br.com.smsforward.repositories.IntegrationDestinyRepository;

public class IntegrationDestinyService {
    private final IntegrationDestinyRepository integrationDestinyRepository;

    public IntegrationDestinyService() {
        this.integrationDestinyRepository = Database.getDatabase().integrationDestinyRepository();
    }

    public long insertIntegrationDestiny(String description, String url, String headers) {
        IntegrationDestiny integrationDestiny = new IntegrationDestiny(description, url, headers);
        return integrationDestinyRepository.insertIntegrationDestiny(integrationDestiny);
    }

    public int updateIntegrationDestiny(IntegrationDestiny integrationDestiny) {
        return integrationDestinyRepository.updateIntegrationDestiny(integrationDestiny);
    }

    public List<IntegrationDestiny> findAllIntegrationDestinies() {
        return integrationDestinyRepository.findAllIntegrationDestinies();
    }

    public int deleteIntegrationDestiny(IntegrationDestiny integrationDestiny) {
        return integrationDestinyRepository.deleteIntegrationDestiny(integrationDestiny);
    }
}
