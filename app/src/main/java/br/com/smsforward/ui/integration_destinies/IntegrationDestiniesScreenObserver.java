package br.com.smsforward.ui.integration_destinies;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import br.com.smsforward.MainViewModel;
import br.com.smsforward.R;
import br.com.smsforward.services.IntegrationDestinyService;

public class IntegrationDestiniesScreenObserver implements LifecycleEventObserver {
    private final AppCompatActivity activity;
    private final MainViewModel mainViewModel;
    private final IntegrationDestinyService integrationDestinyService;

    public IntegrationDestiniesScreenObserver(Fragment fragment) {
        this.activity = (AppCompatActivity) fragment.getActivity();
        this.mainViewModel = new ViewModelProvider(activity).get(MainViewModel.class);
        this.integrationDestinyService = new IntegrationDestinyService();
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        if(event.getTargetState() == Lifecycle.State.STARTED || event.getTargetState() == Lifecycle.State.RESUMED) {
            loadIntegrationDestinies();
            setAddIntegrationDestinyFabListener();
        }
    }

    private void setAddIntegrationDestinyFabListener() {
        FloatingActionButton addIntegrationDestinyFab = activity.findViewById(R.id.integration_destinies_screen_add_integration_destiny_fab);
        addIntegrationDestinyFab.setOnClickListener(v -> {
            LayoutInflater layoutInflater = activity.getLayoutInflater();
            View dialogView = layoutInflater.inflate(R.layout.integration_destiny_add_dialog, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder
                .setView(dialogView)
                .setTitle(R.string.add_integration_destiny_dialog_title)
                .setPositiveButton(R.string.add_integration_destiny_dialog_positive_btn, null)
                .setNegativeButton(R.string.add_integration_destiny_dialog_negative_btn, (dialog, id) -> dialog.dismiss());
            AlertDialog dialog = builder.create();

            dialog.setOnShowListener(dialogInterface -> {
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(view -> {
                    try {
                        View rootView = positiveButton.getRootView();
                        EditText descriptionEt = rootView.findViewById(R.id.integration_destiny_dialog_description_et);
                        EditText urlEt = rootView.findViewById(R.id.integration_destiny_dialog_url_et);
                        EditText headersEt = rootView.findViewById(R.id.integration_destiny_dialog_headers_et);

                        String description = descriptionEt.getText().toString();
                        String url = urlEt.getText().toString();
                        String headers = headersEt.getText().toString();

                        saveIntegrationDestiny(description, url, headers);
                        Snackbar.make(v, activity.getString(R.string.add_integration_destiny_success_message), Snackbar.LENGTH_LONG).show();

                        dialog.dismiss();
                    } catch (Exception e) {
                        Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                });
            });

            dialog.show();
        });
    }

    private void saveIntegrationDestiny(String description, String url, String headers) {
        integrationDestinyService.insertIntegrationDestiny(description, url, headers);
    }

    private void loadIntegrationDestinies() {
        mainViewModel.getIntegrationDestinies().observe(activity, integrationDestinies -> {
        });
    }
}
