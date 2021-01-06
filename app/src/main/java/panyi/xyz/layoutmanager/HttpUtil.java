package panyi.xyz.layoutmanager;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {
    public static final MediaType JSON  = MediaType.get("application/json; charset=utf-8");

    static OkHttpClient client = new OkHttpClient();

    private static Handler uiHandler = new Handler(Looper.getMainLooper());
    private static ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() << 1);


    public interface NetCallback<T>{
        void onSuccess(T data);
        void onError(int errorCode);
        T parseData(String srcResp);
    }

    public static Future sendGet(final String url , final NetCallback cb){
        final Future result = threadPool.submit(()->{
            Request request = new Request.Builder().url(url).build();
            try {
                Call call = client.newCall(request);
                final Response resp = call.execute();
                if(resp.isSuccessful()){
                    final Object respData = cb.parseData(resp.body().string());
                    uiHandler.post(()->{
                        cb.onSuccess(respData);
                    });
                }else{
                    uiHandler.post(()->{
                        cb.onError(resp.code());
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
                uiHandler.post(()->{
                    cb.onError(-1);
                });
            }
        });
        return result;
    }
}
