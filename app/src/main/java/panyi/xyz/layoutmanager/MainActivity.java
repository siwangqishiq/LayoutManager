package panyi.xyz.layoutmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import panyi.xyz.layoutmanager.alive.AccountProvider;
import panyi.xyz.layoutmanager.alive.GuardJobService;
import panyi.xyz.layoutmanager.alive.KeepLiveService;
import panyi.xyz.layoutmanager.layout.MyLayout;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    private RecyclerView mRecylerListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecylerListView = findViewById(R.id.list);
        mRecylerListView.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL , false));
        mRecylerListView.setLayoutManager(new MyLayout());
        HttpUtil.sendGet("http://panyi.xyz:1997/sections?pagesize=30", new HttpUtil.NetCallback<SectionData>() {
            @Override
            public void onSuccess(SectionData data) {
                mRecylerListView.setAdapter(new ImageAdapter(data));
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

//        addAuthAccount();
//        KeepLiveService.start(this);
//
//        GuardJobService.startGuardJob(this);
    }

    private final class ImageAdapter extends RecyclerView.Adapter<ImageViewHolder>{
        private SectionData data;
        private ImageAdapter(SectionData d){
            data = d;
        }

        @NonNull
        @Override
        public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ImageView itemView = new ImageView(MainActivity.this);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(parent.getLayoutParams().height , parent.getLayoutParams().height);
            itemView.setLayoutParams(params);
            return new ImageViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
            SectionData.Section section = data.getData().get(position);
            Glide.with(MainActivity.this)
                    .load(section.getImage())
                    .circleCrop()
                    .into(holder.imgView);
        }

        @Override
        public int getItemCount() {
            return data.getData().size();
        }
    }

    private final class ImageViewHolder extends RecyclerView.ViewHolder{
        ImageView imgView;
        TextView textView;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView = (ImageView) itemView;
//            textView = (TextView) itemView;
        }
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