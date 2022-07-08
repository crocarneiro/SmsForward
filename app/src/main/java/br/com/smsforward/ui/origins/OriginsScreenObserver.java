package br.com.smsforward.ui.origins;

import android.content.DialogInterface;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import br.com.smsforward.R;
import br.com.smsforward.data.Database;
import br.com.smsforward.model.origin.EmptyAddressException;
import br.com.smsforward.model.origin.NotNumericAddressException;
import br.com.smsforward.model.origin.NullAddressException;
import br.com.smsforward.model.origin.Origin;

public class OriginsScreenObserver implements LifecycleEventObserver {
    private final AppCompatActivity activity;

    public OriginsScreenObserver(Fragment fragment) {
        this.activity = (AppCompatActivity) fragment.getActivity();
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        if(event.getTargetState() == Lifecycle.State.STARTED || event.getTargetState() == Lifecycle.State.RESUMED) {
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
                        } catch(Exception e) {
                            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                        }
                    });
                });

                dialog.show();
            });
        }
    }

    private void saveOrigin(String address) throws Exception {
        try {
            Origin origin = new Origin(address);
            Database.getDatabase().originRepository().insertOrigin(origin);
        } catch (NullAddressException | EmptyAddressException e) {
            throw new Exception(activity.getString(R.string.add_origin_empty_address_error_message));
        } catch (NotNumericAddressException e) {
            throw new Exception(activity.getString(R.string.add_origin_not_numeric_address_error_message));
        }
    }
}