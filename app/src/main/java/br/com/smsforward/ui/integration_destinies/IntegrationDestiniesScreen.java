package br.com.smsforward.ui.integration_destinies;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.smsforward.R;

public class IntegrationDestiniesScreen extends Fragment {
    public IntegrationDestiniesScreen() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLifecycle().addObserver(new IntegrationDestiniesScreenObserver(this));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_integration_destinies_screen, container, false);
    }
}