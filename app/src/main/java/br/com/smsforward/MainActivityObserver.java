package br.com.smsforward;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.navigation.NavigationView;

public class MainActivityObserver implements LifecycleEventObserver {
    private AppCompatActivity activity;

    public MainActivityObserver(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        if(event.getTargetState() == Lifecycle.State.CREATED) {
            NavigationView navigationView = activity.findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(menuItem -> {
                NavController navController = Navigation.findNavController(activity, R.id.nav_host_fragment);
                switch (menuItem.getItemId()) {
                    default:
                        break;
                }

                DrawerLayout drawerLayout = activity.findViewById(R.id.drawer_layout);
                drawerLayout.close();
                return false;
            });
        }
    }
}
