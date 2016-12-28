package io.github.froger.instamaterial.ui.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import io.github.froger.instamaterial.QueryManager;
import io.github.froger.instamaterial.R;
import io.github.froger.instamaterial.UserAccount;
import io.github.froger.instamaterial.Utils;
import io.github.froger.instamaterial.models.UserModel;


public class PublishActivity extends BaseActivity {
    public static final String ARG_TAKEN_PHOTO_URI = "arg_taken_photo_uri";
    //public static final String ARG_CHOOSE_PHOTO = "arg_choose_photo";

    @BindView(R.id.tbFollowers)
    ToggleButton tbFollowers;
    @BindView(R.id.tbDirect)
    ToggleButton tbDirect;
    @BindView(R.id.ivPhoto)
    ImageView ivPhoto;
    @BindView(R.id.etDescription)
    EditText etContent;

    private boolean propagatingToggleState = false;
    private Uri photoUri;
    private int photoSize;

    public static void openWithPhotoUri(Activity openingActivity, Uri photoUri) {
        Intent intent = new Intent(openingActivity, PublishActivity.class);
        intent.putExtra(ARG_TAKEN_PHOTO_URI, photoUri);
        openingActivity.startActivity(intent);
    }

    /*public static void openWithPhoto(Activity openingActivity, Bitmap bitmap)
    {
        HashMap map= new HashMap();
        map.put("photo",bitmap);
        Intent intent = new Intent(openingActivity, PublishActivity.class);
        intent.putExtra(ARG_CHOOSE_PHOTO,map);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_grey600_24dp);
        photoSize = getResources().getDimensionPixelSize(R.dimen.publish_photo_thumbnail_size);

        if (savedInstanceState == null) {
            photoUri = getIntent().getParcelableExtra(ARG_TAKEN_PHOTO_URI);
        } else {
            photoUri = savedInstanceState.getParcelable(ARG_TAKEN_PHOTO_URI);
        }

        /*if(photoUri == null)
        {
            if (savedInstanceState == null) {
                HashMap map = (HashMap)getIntent().getSerializableExtra(ARG_CHOOSE_PHOTO);
                photoBitmap = (Bitmap)map.get("photo");
            } else {
                HashMap map = (HashMap)savedInstanceState.getParcelable(ARG_CHOOSE_PHOTO);
                photoBitmap = (Bitmap)map.get("photo");
            }
        }*/

        updateStatusBarColor();

        ivPhoto.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                ivPhoto.getViewTreeObserver().removeOnPreDrawListener(this);
                loadThumbnailPhoto();
                return true;
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void updateStatusBarColor() {
        if (Utils.isAndroid5()) {
            getWindow().setStatusBarColor(0xff888888);
        }
    }

    private void loadThumbnailPhoto() {
        ivPhoto.setScaleX(0);
        ivPhoto.setScaleY(0);
        Picasso.with(this)
                    .load(photoUri)
                    .centerCrop()
                    .resize(photoSize, photoSize)
                    .into(ivPhoto, new Callback() {
                        @Override
                        public void onSuccess() {
                            ivPhoto.animate()
                                    .scaleX(1.f).scaleY(1.f)
                                    .setInterpolator(new OvershootInterpolator())
                                    .setDuration(400)
                                    .setStartDelay(200)
                                    .start();
                        }

                        @Override
                        public void onError() {
                        }
                    });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_publish, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_publish)
        {
            startQuery();
            bringMainActivityToTop();
            return true;
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }
    }

    private void bringMainActivityToTop() {
        /*Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setAction(MainActivity.ACTION_SHOW_LOADING_ITEM);
        startActivity(intent);*/
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(ARG_TAKEN_PHOTO_URI, photoUri);
        /*else
        {
            HashMap map = new HashMap();
            map.put("photo",photoBitmap);
            outState.putSerializable(ARG_CHOOSE_PHOTO,map);
        }*/
    }

    @OnCheckedChanged(R.id.tbFollowers)
    public void onFollowersCheckedChange(boolean checked) {
        if (!propagatingToggleState) {
            propagatingToggleState = true;
            tbDirect.setChecked(!checked);
            propagatingToggleState = false;
        }
    }

    @OnCheckedChanged(R.id.tbDirect)
    public void onDirectCheckedChange(boolean checked) {
        if (!propagatingToggleState) {
            propagatingToggleState = true;
            tbFollowers.setChecked(!checked);
            propagatingToggleState = false;
        }
    }

    private void startQuery()
    {
        UserModel um = UserAccount.getInstance().getUser();
        //Yao Gai!!!
        if(um == null)
        {
            um = new UserModel();
            um.nickname = "Test1";
            um.user_id = "0001";
            UserAccount.getInstance().setUser(um);
        }

        String content = etContent.getText().toString();
        if(photoUri != null && !content.isEmpty())
        {

            QueryManager qm = new QueryManager(this);
            /*Need To Change!!!*/
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] bytes =  baos.toByteArray();
                String imgstr =  Base64.encodeToString(bytes, 100);
                qm.execute("insertStory", "user_id", um.user_id, "content", content, "photo", imgstr);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void getResult(ArrayList<String> result)
    {

    }
}
