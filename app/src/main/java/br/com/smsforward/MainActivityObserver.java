package br.com.smsforward;

import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.google.android.material.navigation.NavigationView;
import java.util.concurrent.Executor;

import br.com.smsforward.data.Database;
import br.com.smsforward.services.SyncMessagesService;

public class MainActivityObserver implements LifecycleEventObserver {
    private AppCompatActivity activity;

    public MainActivityObserver(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        if(event.getTargetState() == Lifecycle.State.CREATED) {
            Log.i(getClass().getCanonicalName(), "Initializing database...");
            Database.initDatabase(activity);

            Log.i(getClass().getCanonicalName(), "Checking sms permission...");
            checkSmsPermission();

            Log.i(getClass().getCanonicalName(), "Setting drawer navigation");
            setDrawerNavigation();

            Log.i(getClass().getCanonicalName(), "Synchronizing SMSs...");
            syncSms();

            Log.i(getClass().getCanonicalName(), "Main activity successfully created.");
        }
    }

    private void checkSmsPermission() {
        if(!hasSmsPermission())
            askForSmsPermission();
    }

    private Boolean hasSmsPermission() {
        return ContextCompat.checkSelfPermission(activity, "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED;
    }

    private void askForSmsPermission() {
        final int REQUEST_CODE_ASK_PERMISSIONS = 123;
        ActivityCompat.requestPermissions(activity, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
    }

    private void setDrawerNavigation() {
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

    private void syncSms() {
        SyncMessagesService service = new SyncMessagesService();
        Executor executor = Application.getExecutorService();
        ContentResolver contentResolver = activity.getApplicationContext().getContentResolver();
        service.syncAll(contentResolver, executor);
    }
}
