package br.com.smsforward.ui.integration_destinies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.smsforward.R;
import br.com.smsforward.model.integration_destiny.IntegrationDestiny;

public class IntegrationDestinyAdapter extends RecyclerView.Adapter<IntegrationDestinyAdapter.ViewHolder> {
    private final AppCompatActivity activity;
    private final List<IntegrationDestiny> integrationDestinies;

    public IntegrationDestinyAdapter(AppCompatActivity activity, List<IntegrationDestiny> integrationDestinies) {
        this.activity = activity;
        this.integrationDestinies = integrationDestinies;
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
    }

    @Override
    public int getItemCount() {
        return integrationDestinies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView descriptionTv;
        TextView urlTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            descriptionTv = itemView.findViewById(R.id.integration_destiny_row_description_tv);
            urlTv = itemView.findViewById(R.id.integration_destiny_row_url_tv);
        }
    }
}
