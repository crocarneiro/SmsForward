package br.com.smsforward.ui.origins;

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
import br.com.smsforward.model.origin.Origin;
import br.com.smsforward.services.OriginService;

public class OriginAdapter extends RecyclerView.Adapter<OriginAdapter.ViewHolder> {
    private final AppCompatActivity activity;
    private final List<Origin> origins;
    private final OriginService originService;

    public OriginAdapter(AppCompatActivity activity, List<Origin> origins) {
        this.activity = activity;
        this.origins = origins;
        this.originService = new OriginService();
    }

    @NonNull
    @Override
    public OriginAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.origin_rv_row, parent, false);

        return new OriginAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OriginAdapter.ViewHolder holder, int position) {
        final Origin origin = origins.get(position);

        holder.addressTv.setText(origin.getAddress());
        holder.deleteIb.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                    .setTitle(activity.getString(R.string.delete_origin_dialog_title))
                    .setMessage(activity.getString(R.string.delete_origin_dialog_message))
                    .setNegativeButton(activity.getString(R.string.delete_origin_dialog_negative_btn), (dialog, id) -> dialog.dismiss())
                    .setPositiveButton(activity.getString(R.string.delete_origin_dialog_positive_btn), (dialog, id) -> {
                        originService.deleteOrigin(origin);
                        MainViewModel viewModel = new ViewModelProvider(activity).get(MainViewModel.class);
                        viewModel.reloadOrigins();
                    })
                    ;
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return origins.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView addressTv;
        ImageButton deleteIb;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            addressTv = itemView.findViewById(R.id.origin_row_address_tv);
            deleteIb = itemView.findViewById(R.id.origin_row_delete_ib);
        }
    }
}
