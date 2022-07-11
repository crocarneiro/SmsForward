package br.com.smsforward.ui.integration_destinies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.smsforward.MainViewModel;
import br.com.smsforward.R;
import br.com.smsforward.model.integration_destiny.IntegrationDestiny;
import br.com.smsforward.services.IntegrationDestinyService;

public class IntegrationDestinyAdapter extends RecyclerView.Adapter<IntegrationDestinyAdapter.ViewHolder> {
    private final AppCompatActivity activity;
    private final List<IntegrationDestiny> integrationDestinies;
    private final IntegrationDestinyService integrationDestinyService;

    public IntegrationDestinyAdapter(AppCompatActivity activity, List<IntegrationDestiny> integrationDestinies) {
        this.activity = activity;
        this.integrationDestinies = integrationDestinies;
        this.integrationDestinyService = new IntegrationDestinyService();
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
                        MainViewModel viewModel = new ViewModelProvider(activity).get(MainViewModel.class);
                        viewModel.reloadIntegrationDestinies();
                    })
                    ;
            AlertDialog dialog = builder.create();
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
