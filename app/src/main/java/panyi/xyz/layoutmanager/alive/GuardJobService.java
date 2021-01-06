package panyi.xyz.layoutmanager.alive;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class GuardJobService extends JobService {
    public static void startGuardJob(Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context
                .JOB_SCHEDULER_SERVICE);
//        setPersisted 在设备重启依然执行
        JobInfo.Builder builder = new JobInfo.Builder(10, new ComponentName(context
                .getPackageName(), GuardJobService.class
                .getName())).setPersisted(true);
        //小于7.0
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            // 每隔1s 执行一次 job
            builder.setPeriodic(1_000);
        } else {
            //延迟执行任务
            builder.setMinimumLatency(1_000);
        }
        jobScheduler.schedule(builder.build());
    }

    private static final String TAG = "MyJobService";

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.e(TAG, "开启job");
        //如果7.0以上 轮训
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            startGuardJob(this);
        }
//        boolean isLocalRun = Utils.isRunningService(this, LocalGuardService.class.getName());
//        boolean isRemoteRun = Utils.isRunningService(this, RemoteGuardService.class.getName());
//        if (!isLocalRun || !isRemoteRun) {
//            startService(new Intent(this, LocalGuardService.class));
//            startService(new Intent(this, RemoteGuardService.class));
//        }
        startService(new Intent(this , KeepLiveService.class));
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
