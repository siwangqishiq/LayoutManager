package panyi.xyz.layoutmanager.alive;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import panyi.xyz.layoutmanager.R;

public class AuthenticatorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        AccountManager accountMgr = (AccountManager)getSystemService(ACCOUNT_SERVICE);
        Account[] accounts  = accountMgr.getAccounts();

        for(Account account : accounts){
            System.out.println("account : " + account.name +"   " + account.type);
        }//end for each

        findViewById(R.id.btn).setOnClickListener((v)->{
            Account newAccount = new Account(getString(R.string.app_name) , getPackageName());
            boolean addSuccess = accountMgr.addAccountExplicitly(newAccount , null , null);
            System.out.println("addAccountExplicitly => " + addSuccess);
        });
    }
}
