package br.com.smsforward.ui.origins;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.smsforward.R;

public class OriginsScreen extends Fragment {
    public OriginsScreen() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLifecycle().addObserver(new OriginsScreenObserver(this));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_origins_screen, container, false);
    }
}