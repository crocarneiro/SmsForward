package br.com.smsforward.ui.integration_destinies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import java.util.List;
import br.com.smsforward.MainViewModel;
import br.com.smsforward.R;
import br.com.smsforward.model.integration_destiny.IntegrationDestiny;
import br.com.smsforward.services.IntegrationDestinyService;

public class IntegrationDestinyAdapter extends RecyclerView.Adapter<IntegrationDestinyAdapter.ViewHolder> {
    private final AppCompatActivity activity;
    private final List<IntegrationDestiny> integrationDestinies;
    private final IntegrationDestinyService integrationDestinyService;
    private final MainViewModel mainViewModel;

    public IntegrationDestinyAdapter(AppCompatActivity activity, List<IntegrationDestiny> integrationDestinies) {
        this.activity = activity;
        this.integrationDestinies = integrationDestinies;
        this.integrationDestinyService = new IntegrationDestinyService();
        this.mainViewModel = new ViewModelProvider(activity).get(MainViewModel.class);
    }

    @NonNull
    @Override
    public IntegrationDestinyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.integration_destiny_rv_row, parent, false);

        return new IntegrationDestinyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IntegrationDestinyAdapter.ViewHolder holder, int position) {
        IntegrationDestiny integrationDestiny = integrationDestinies.get(position);

        holder.descriptionTv.setText(integrationDestiny.getDescription());
        holder.urlTv.setText(integrationDestiny.getUrl());

        holder.deleteIb.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                    .setTitle(activity.getString(R.string.delete_integration_destiny_dialog_title))
                    .setMessage(activity.getString(R.string.delete_integration_destiny_dialog_message))
                    .setNegativeButton(activity.getString(R.string.delete_integration_destiny_dialog_negative_btn), (dialog, id) -> dialog.dismiss())
                    .setPositiveButton(activity.getString(R.string.delete_integration_destiny_dialog_positive_btn), (dialog, id) -> {
                        integrationDestinyService.deleteIntegrationDestiny(integrationDestiny);
                        mainViewModel.reloadIntegrationDestinies();
                    })
                    ;
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        holder.editIb.setOnClickListener(v -> {
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

                View rootView = positiveButton.getRootView();
                EditText descriptionEt = rootView.findViewById(R.id.integration_destiny_dialog_description_et);
                EditText urlEt = rootView.findViewById(R.id.integration_destiny_dialog_url_et);
                EditText headersEt = rootView.findViewById(R.id.integration_destiny_dialog_headers_et);

                descriptionEt.setText(integrationDestiny.getDescription());
                urlEt.setText(integrationDestiny.getUrl());
                headersEt.setText(integrationDestiny.getHeaders());

                positiveButton.setOnClickListener(view -> {
                    try {
                        String description = descriptionEt.getText().toString();
                        String url = urlEt.getText().toString();
                        String headers = headersEt.getText().toString();

                        integrationDestiny.setDescription(description);
                        integrationDestiny.setUrl(url);
                        integrationDestiny.setHeaders(headers);

                        integrationDestinyService.updateIntegrationDestiny(integrationDestiny);

                        Snackbar.make(v, activity.getString(R.string.update_integration_destiny_success_message), Snackbar.LENGTH_LONG).show();

                        dialog.dismiss();

                        mainViewModel.reloadIntegrationDestinies();
                    } catch (Exception e) {
                        Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                });
            });

            dialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return integrationDestinies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView descriptionTv;
        TextView urlTv;
        ImageButton editIb;
        ImageButton deleteIb;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            descriptionTv = itemView.findViewById(R.id.integration_destiny_row_description_tv);
            urlTv = itemView.findViewById(R.id.integration_destiny_row_url_tv);
            editIb = itemView.findViewById(R.id.integration_destiny_row_edit_ib);
            deleteIb = itemView.findViewById(R.id.integration_destiny_row_delete_ib);
        }
    }
}
