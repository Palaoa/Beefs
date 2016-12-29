package io.github.froger.instamaterial.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import io.github.froger.instamaterial.QueryManager;
import io.github.froger.instamaterial.R;
import io.github.froger.instamaterial.ResultParce;
import io.github.froger.instamaterial.UserAccount;
import io.github.froger.instamaterial.Utils;
import io.github.froger.instamaterial.models.UserModel;
import io.github.froger.instamaterial.ui.adapter.FeedAdapter;
import io.github.froger.instamaterial.ui.adapter.FeedItemAnimator;
import io.github.froger.instamaterial.ui.view.FeedContextMenu;
import io.github.froger.instamaterial.ui.view.FeedContextMenuManager;

/**
 * Created by dell on 2016/12/22.
 */

public class MyFeedActivity extends BaseActivity implements FeedAdapter.OnFeedItemClickListener,
        FeedContextMenu.OnFeedContextMenuItemClickListener
        {
    public static final String ACTION_SHOW_LOADING_ITEM = "action_show_loading_item";

    private static final int ANIM_DURATION_TOOLBAR = 300;
    //private static final int ANIM_DURATION_FAB = 400;
    BaseActivity ba;
    @BindView(R.id.rvfeedFeed)
    RecyclerView rvFeed;
    /*@BindView(R.id.btnCreate)
    FloatingActionButton fabCreate;*/
    @BindView(R.id.feedcontent)
    CoordinatorLayout clContent;

    private FeedAdapter feedAdapter;

    private boolean pendingIntroAnimation;
    private boolean isAnimated = false;

            private String methodName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfeed);
        setupFeed();

        ba = this;
        methodName = getIntent().getStringExtra("methodName");
        if (savedInstanceState == null) {
            pendingIntroAnimation = true;
        } else {
            isAnimated = false;
            startQuery();
        }
    }

    private void setupFeed() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        rvFeed.setLayoutManager(linearLayoutManager);

        feedAdapter = new FeedAdapter(this);
        feedAdapter.setOnFeedItemClickListener(this);
        rvFeed.setAdapter(feedAdapter);
        rvFeed.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                FeedContextMenuManager.getInstance().onScrolled(recyclerView, dx, dy);
            }
        });
        rvFeed.setItemAnimator(new FeedItemAnimator());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (ACTION_SHOW_LOADING_ITEM.equals(intent.getAction())) {
            showFeedLoadingItemDelayed();
        }
    }

    private void showFeedLoadingItemDelayed() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rvFeed.smoothScrollToPosition(0);
                feedAdapter.showLoadingView();
            }
        }, 500);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (pendingIntroAnimation) {
            pendingIntroAnimation = false;
            startIntroAnimation();
        }
        return true;
    }

    private void startIntroAnimation() {
        //fabCreate.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.btn_fab_size));

        int actionbarSize = Utils.dpToPx(56);
        getToolbar().setTranslationY(-actionbarSize);
        getIvLogo().setTranslationY(-actionbarSize);
        getInboxMenuItem().getActionView().setTranslationY(-actionbarSize);

        getToolbar().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);
        getIvLogo().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(400);
        getInboxMenuItem().getActionView().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        startContentAnimation();
                    }
                })
                .start();
    }

    private void startContentAnimation() {
        /*fabCreate.animate()
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setStartDelay(300)
                .setDuration(ANIM_DURATION_FAB)
                .start();*/
        isAnimated = true;
        startQuery();
    }

    @Override
    public void onCommentsClick(View v, int position) {
        final Intent intent = new Intent(this, CommentsActivity.class);
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        intent.putExtra(CommentsActivity.ARG_DRAWING_START_LOCATION, startingLocation[1]);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onMoreClick(View v, int itemPosition) {
        FeedContextMenuManager.getInstance().toggleContextMenuFromView(v, itemPosition, this);
    }

    @Override
    public void onProfileClick(View v) {
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        startingLocation[0] += v.getWidth() / 2;
        String user_id;
        //FeedAdapter.FeedItem fi = feedAdapter.getItemId();
        UserProfileActivity.startUserProfileFromLocation(startingLocation, this, "0001");
        overridePendingTransition(0, 0);
    }

    @Override
    public void onReportClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onSharePhotoClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onCopyShareUrlClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onCancelClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    /*@OnClick(R.id.btnCreate)
    public void onTakePhotoClick() {
        int[] startingLocation = new int[2];
        fabCreate.getLocationOnScreen(startingLocation);
        startingLocation[0] += fabCreate.getWidth() / 2;
        TakePhotoActivity.startCameraFromLocation(startingLocation, this);
        overridePendingTransition(0, 0);
    }*/

    public void showLikedSnackbar() {
        Snackbar.make(clContent, "Liked!", Snackbar.LENGTH_SHORT).show();
    }

    public void showDislikedSnakebar()
    {
        Snackbar.make(clContent, "Disliked!", Snackbar.LENGTH_SHORT).show();
    }

    public void changeLikedSituation(boolean islike, String story_id)
    {
                QueryManager qm = new QueryManager(ba);
                UserModel um = UserAccount.getInstance().getUser();
                //Yao Gai!!!
                if(um == null)
                {
                    um = new UserModel();
                    um.nickname = "Test1";
                    um.user_id = "0001";
                    UserAccount.getInstance().setUser(um);
                }

                if(islike)
                {
                    qm.execute("addStoryLike","user_id",um.user_id,"story_id",story_id);
                    return;
                }
                qm.execute("deleteStoryLike","user_id",um.user_id,"story_id",story_id);

    }

    @Override
    public void getResult(ArrayList<String> result)
    {
            try
            {
                //ArrayList<FeedAdapter.FeedItem> feedList = new ArrayList<>();
                ArrayList<FeedAdapter.FeedItem> feedList = ResultParce.parseFeed(result.get(1));
                feedAdapter.updateItems(isAnimated,feedList);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
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
        if(um == null)
        {
            Intent intent = new Intent();
            intent.setClassName(getApplicationContext(),"io.github.froger.instamaterial.ui.activity.LoginnActivity");
            startActivityForResult(intent, 101);
            // Xian Deng Lu
        }
        QueryManager qm = new QueryManager(ba);
        qm.execute(methodName,"user_id",um.user_id);
        return;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        /*
        !!!!!!!!!!!!!!!!!!!!!!!!
        !!!!!!!!!!!!!!!!!!!!!!!!
        */
        //101 info 102 write story 103 register

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101 && resultCode == 201)  // deng lu
        {
            startQuery();
        }
    }
}
