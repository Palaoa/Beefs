package io.github.froger.instamaterial.ui.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.BindDimen;
import butterknife.BindString;
import io.github.froger.instamaterial.R;
import io.github.froger.instamaterial.UserAccount;
import io.github.froger.instamaterial.models.UserModel;
import io.github.froger.instamaterial.ui.utils.CircleTransformation;


public abstract class BaseDrawerActivity extends BaseActivity {

    private AppCompatActivity aca = this;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.vNavigation)
    NavigationView vNavigation;

    @BindDimen(R.dimen.global_menu_avatar_size)
    int avatarSize;
    @BindString(R.string.user_profile_photo)
    String profilePhoto;

    //Cannot be bound via Butterknife, hosting view is initialized later (see setupHeader() method)
    private ImageView ivMenuUserProfilePhoto;
    private final int REQUEST_ALBUM_OK = 2;
    @Override
    public void setContentView(int layoutResID) {
        super.setContentViewWithoutInject(R.layout.activity_drawer);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.flContentRoot);
        LayoutInflater.from(this).inflate(layoutResID, viewGroup, true);
        bindViews();
        setupHeader();
        /*UserModel um = UserAccount.getInstance().getUser();
        if(um == null)
        {
            menuSetting.setTitle("Login");
            //item.setTitle("Login");
        }
        else{
            menuSetting.setTitle("Logout");
        }*/
        vNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem item){
                Intent intent;
                switch(item.getItemId())
                {
                    case R.id.menu_feed:
                        Toast.makeText(aca,"A",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        intent = new Intent();
                        intent.setClassName(getApplicationContext(),"io.github.froger.instamaterial.ui.activity.MyFeedActivity");
                        intent.putExtra("methodName","queryUserStory");
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        //Toast.makeText(this, "A", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.menu_direct:
                        Toast.makeText(aca, "B", Toast.LENGTH_LONG).show();
                        Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
                        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(albumIntent, REQUEST_ALBUM_OK);

                        break;
                    case R.id.menu_news:
                        Toast.makeText(aca, "C", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.menu_popular:
                        Toast.makeText(aca, "D", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.menu_photos_nearby:
                        Toast.makeText(aca, "E", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.menu_photo_you_liked:
                        Toast.makeText(aca, "F", Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        intent = new Intent();
                        intent.setClassName(getApplicationContext(),"io.github.froger.instamaterial.ui.activity.MyFeedActivity");
                        intent.putExtra("methodName","queryStoryLiked");
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.menu_settings:
                        Toast.makeText(aca, "G", Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        intent = new Intent();
                        intent.setClassName(getApplicationContext(),"io.github.froger.instamaterial.ui.activity.LoginnActivity");
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        break;
                }
                return true;
            }
        });
    }



    @Override
    protected void setupToolbar() {
        super.setupToolbar();
        if (getToolbar() != null) {
            getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
            });
        }
    }

    private void setupHeader() {
        View headerView = vNavigation.getHeaderView(0);
        ivMenuUserProfilePhoto = (ImageView) headerView.findViewById(R.id.ivMenuUserProfilePhoto);
        headerView.findViewById(R.id.vGlobalMenuHeader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGlobalMenuHeaderClick(v);
            }
        });

        Picasso.with(this)
                .load(profilePhoto)
                .placeholder(R.drawable.img_circle_placeholder)
                .resize(avatarSize, avatarSize)
                .centerCrop()
                .transform(new CircleTransformation())
                .into(ivMenuUserProfilePhoto);
    }

    public void onGlobalMenuHeaderClick(final View v) {
        drawerLayout.closeDrawer(Gravity.LEFT);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int[] startingLocation = new int[2];
                v.getLocationOnScreen(startingLocation);
                startingLocation[0] += v.getWidth() / 2;
                UserProfileActivity.startUserProfileFromLocation(startingLocation, BaseDrawerActivity.this, "0001");
                overridePendingTransition(0, 0);
            }
        }, 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {

            case REQUEST_ALBUM_OK:
                try{
                    Uri originalUri = data.getData();
                    PublishActivity.openWithPhotoUri(this,originalUri);
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }


        }
    }


    /*@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);
        switch(item.getItemId())
        {
            case R.id.menu_feed:
                Toast.makeText(this, "A", Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_direct:
                Toast.makeText(this, "B", Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_news:
                Toast.makeText(this, "C", Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_popular:
                Toast.makeText(this, "D", Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_photos_nearby:
                Toast.makeText(this, "E", Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_photo_you_liked:
                Toast.makeText(this, "F", Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_settings:
                Toast.makeText(this, "G", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }*/

}
