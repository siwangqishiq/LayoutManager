package panyi.xyz.layoutmanager.alive;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class KeepLiveService extends Service {
    private static int count;

    public static void start(Context context){
        Intent it = new Intent(context ,  KeepLiveService.class);
        context.startService(it);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(()->{
            runLoop();
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("im onStartCommand ");
        return START_STICKY;
    }

    private void runLoop(){
        while(true){
            count++;
            System.out.println("im still alive cout => " + count);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }//end while
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        System.out.println("im service onDestroy count=" + count);
        super.onDestroy();
    }
}
