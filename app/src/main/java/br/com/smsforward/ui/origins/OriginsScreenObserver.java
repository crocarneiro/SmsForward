package br.com.smsforward.ui.origins;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import br.com.smsforward.MainViewModel;
import br.com.smsforward.R;
import br.com.smsforward.model.origin.EmptyAddressException;
import br.com.smsforward.model.origin.NotNumericAddressException;
import br.com.smsforward.model.origin.NullAddressException;
import br.com.smsforward.services.OriginService;

public class OriginsScreenObserver implements LifecycleEventObserver {
    private final AppCompatActivity activity;
    private final MainViewModel mainViewModel;
    private final OriginService originService;

    public OriginsScreenObserver(Fragment fragment) {
        this.activity = (AppCompatActivity) fragment.getActivity();
        this.mainViewModel = new ViewModelProvider(activity).get(MainViewModel.class);
        this.originService = new OriginService();
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        if(event.getTargetState() == Lifecycle.State.STARTED || event.getTargetState() == Lifecycle.State.RESUMED) {
            loadOrigins();
            setAddOriginFabListener();
        }
    }

    private void setAddOriginFabListener() {
        FloatingActionButton addOriginFab = activity.findViewById(R.id.origins_screen_add_origin_fab);
        addOriginFab.setOnClickListener(v -> {
            LayoutInflater layoutInflater = activity.getLayoutInflater();
            View dialogView = layoutInflater.inflate(R.layout.origin_add_dialog, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder
                    .setView(dialogView)
                    .setTitle(R.string.add_origin_dialog_title)
                    .setPositiveButton(R.string.add_origin_dialog_positive_btn, null)
                    .setNegativeButton(R.string.add_origin_dialog_negative_btn, (dialog, id) -> dialog.dismiss());
            AlertDialog dialog = builder.create();

            dialog.setOnShowListener(dialogInterface -> {
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(view -> {
                    try {
                        EditText addressEt = positiveButton.getRootView().findViewById(R.id.origin_dialog_origin_et);
                        String address = addressEt.getText().toString();

                        saveOrigin(address);

                        dialog.dismiss();

                        Snackbar.make(view, activity.getString(R.string.add_origin_success_message), Snackbar.LENGTH_LONG).show();

                        mainViewModel.reloadOrigins();
                    } catch(Exception e) {
                        Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                });
            });

            dialog.show();
        });
    }

    private void saveOrigin(String address) throws Exception {
        try {
            originService.insertOrigin(address);
        } catch (NullAddressException | EmptyAddressException e) {
            throw new Exception(activity.getString(R.string.add_origin_empty_address_error_message));
        } catch (NotNumericAddressException e) {
            throw new Exception(activity.getString(R.string.add_origin_not_numeric_address_error_message));
        }
    }

    public void loadOrigins() {
        mainViewModel.getOrigins().observe(activity, origins -> {
            OriginAdapter adapter = new OriginAdapter(activity, origins);
            RecyclerView recyclerView = activity.findViewById(R.id.origins_screen_rv);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        });
    }
}