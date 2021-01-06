package panyi.xyz.layoutmanager.alive;

import android.accounts.Account;
import android.app.Service;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class SyncService extends Service {
    private SyncAdapter mSyncAdapter;

    @Override
    public void onCreate() {
        System.out.println("SyncService on create!");
        mSyncAdapter = new SyncAdapter(this , true);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("SyncService  onStartCommand!");
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mSyncAdapter.getSyncAdapterBinder();
    }

    public static class SyncAdapter extends AbstractThreadedSyncAdapter {
        public SyncAdapter(Context context, boolean autoInitialize) {
            super(context, autoInitialize);
        }

        @Override
        public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
            System.out.println("SyncService  onPerformSync type = " + account.type +"  name = " + account.name);
        }
    }
}
