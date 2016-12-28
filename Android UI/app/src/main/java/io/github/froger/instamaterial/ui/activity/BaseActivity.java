package io.github.froger.instamaterial.ui.activity;

import android.content.ClipData;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.BindView;
import io.github.froger.instamaterial.R;
import io.github.froger.instamaterial.UserAccount;
import io.github.froger.instamaterial.models.UserModel;



// Chu Le Main Yi Wai De Activity Zhi Neng You Yi Ge!!
public abstract class BaseActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Nullable
    @BindView(R.id.ivLogo)
    ImageView ivLogo;

    private MenuItem inboxMenuItem;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        bindViews();
    }

    protected void bindViews() {
        ButterKnife.bind(this);
        setupToolbar();
    }

    public void setContentViewWithoutInject(int layoutResId) {
        super.setContentView(layoutResId);
    }

    protected void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_menu_white);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        inboxMenuItem = menu.findItem(R.id.action_inbox);
        inboxMenuItem.setActionView(R.layout.menu_item_view);
        /*UserModel um = UserAccount.getInstance().getUser();
        if(um == null)
        {
             //item.setTitle("Login");
        }
        else{
            //item.setTitle("Logout");
        }*/
        return true;
    }
    public Toolbar getToolbar() {
        return toolbar;
    }

    public MenuItem getInboxMenuItem() {
        return inboxMenuItem;
    }

    public ImageView getIvLogo() {
        return ivLogo;
    }

    public abstract void getResult(ArrayList<String> result);
}
