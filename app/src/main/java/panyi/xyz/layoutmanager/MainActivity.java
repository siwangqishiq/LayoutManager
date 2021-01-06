package panyi.xyz.layoutmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import panyi.xyz.layoutmanager.alive.AccountProvider;
import panyi.xyz.layoutmanager.alive.GuardJobService;
import panyi.xyz.layoutmanager.alive.KeepLiveService;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);

        HttpUtil.sendGet("http://panyi.xyz:1997/sections", new HttpUtil.NetCallback<SectionData>() {
            @Override
            public void onSuccess(SectionData data) {
                Gson gson = new Gson();
                textView.setText(gson.toJson(data));
            }

            @Override
            public void onError(int errorCode) {

            }

            @Override
            public SectionData parseData(String srcResp) {
                Gson gson = new Gson();
                return gson.fromJson(srcResp , SectionData.class);
            }
        });

        addAuthAccount();
        KeepLiveService.start(this);

        GuardJobService.startGuardJob(this);
    }

    private void addAuthAccount(){
        AccountManager accountMgr = (AccountManager)getSystemService(ACCOUNT_SERVICE);
        Account newAccount = new Account(getString(R.string.app_name) , getPackageName());
        boolean addSuccess = accountMgr.addAccountExplicitly(newAccount , null , null);
        System.out.println("addAccountExplicitly => " + addSuccess);


        Bundle bundle= new Bundle();
        ContentResolver.setIsSyncable(newAccount, AccountProvider.AUTHORITY, 1);
        ContentResolver.setSyncAutomatically(newAccount, AccountProvider.AUTHORITY,true);
        ContentResolver.addPeriodicSync(newAccount, AccountProvider.AUTHORITY,bundle, 60);    // 间隔时间为60秒
    }
}