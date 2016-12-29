package io.github.froger.instamaterial.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import io.github.froger.instamaterial.QueryManager;
import io.github.froger.instamaterial.R;
import io.github.froger.instamaterial.ResultParce;
import io.github.froger.instamaterial.UserAccount;
import io.github.froger.instamaterial.Utils;
import io.github.froger.instamaterial.models.ComtBusiModel;
import io.github.froger.instamaterial.models.UserModel;
import io.github.froger.instamaterial.ui.adapter.CommentsAdapter;
import io.github.froger.instamaterial.ui.view.SendCommentButton;


public class CommentsActivity extends BaseDrawerActivity implements SendCommentButton.OnSendClickListener {
    public static final String ARG_DRAWING_START_LOCATION = "arg_drawing_start_location";
    public static final String ARG_STORY_ID = "arg_story_id";
    @BindView(R.id.contentRoot)
    LinearLayout contentRoot;
    @BindView(R.id.rvComments)
    RecyclerView rvComments;
    @BindView(R.id.llAddComment)
    LinearLayout llAddComment;
    @BindView(R.id.etComment)
    EditText etComment;
    @BindView(R.id.btnSendComment)
    SendCommentButton btnSendComment;

    private CommentsAdapter commentsAdapter;
    private int drawingStartLocation;
    private String mstory_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        mstory_id = getIntent().getStringExtra(ARG_STORY_ID);
        setupComments();
        setupSendCommentButton();

        drawingStartLocation = getIntent().getIntExtra(ARG_DRAWING_START_LOCATION, 0);
        if (savedInstanceState == null) {
            contentRoot.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    contentRoot.getViewTreeObserver().removeOnPreDrawListener(this);
                    startIntroAnimation();
                    return true;
                }
            });
        }
        if (mstory_id != null) {
            startQuery();
        }

    }

    private void setupComments() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvComments.setLayoutManager(linearLayoutManager);
        rvComments.setHasFixedSize(true);

        commentsAdapter = new CommentsAdapter(this);
        rvComments.setAdapter(commentsAdapter);
        rvComments.setOverScrollMode(View.OVER_SCROLL_NEVER);
        rvComments.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    commentsAdapter.setAnimationsLocked(true);
                }
            }
        });
    }

    private void setupSendCommentButton() {
        btnSendComment.setOnSendClickListener(this);
    }

    private void startIntroAnimation() {
        ViewCompat.setElevation(getToolbar(), 0);
        contentRoot.setScaleY(0.1f);
        contentRoot.setPivotY(drawingStartLocation);
        llAddComment.setTranslationY(200);

        contentRoot.animate()
                .scaleY(1)
                .setDuration(200)
                .setInterpolator(new AccelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ViewCompat.setElevation(getToolbar(), Utils.dpToPx(8));
                        animateContent();
                    }
                })
                .start();
    }

    private void animateContent() {
        //commentsAdapter.updateItems();
        llAddComment.animate().translationY(0)
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(200)
                .start();
    }

    @Override
    public void onBackPressed() {
        ViewCompat.setElevation(getToolbar(), 0);
        contentRoot.animate()
                .translationY(Utils.getScreenHeight(this))
                .setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        CommentsActivity.super.onBackPressed();
                        overridePendingTransition(0, 0);
                    }
                })
                .start();
    }

    @Override
    public void onSendClickListener(View v) {
        if (validateComment()) {
            String content = etComment.getText().toString();
            ComtBusiModel item = new ComtBusiModel();
            item.content = content;
            item.story_id = mstory_id;
            startInsert(content);
            commentsAdapter.addItem(item);
            commentsAdapter.setAnimationsLocked(false);
            commentsAdapter.setDelayEnterAnimation(false);
            rvComments.smoothScrollBy(0, rvComments.getChildAt(0).getHeight() * commentsAdapter.getItemCount());

            etComment.setText(null);
            btnSendComment.setCurrentState(SendCommentButton.STATE_DONE);
        }
    }

    private boolean validateComment() {
        if (TextUtils.isEmpty(etComment.getText())) {
            btnSendComment.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_error));
            return false;
        }

        return true;
    }

    private void startQuery()
    {
        //mstory_id = getIntent().getStringExtra(ARG_STORY_ID);
        QueryManager qm = new QueryManager(this);
        qm.execute("queryCommentByStoryID","story_id",mstory_id);
    }

    private void startInsert(String content)
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
        QueryManager qm = new QueryManager(this);
        qm.execute("insertComment","story_id",mstory_id,"user_id",um.user_id,"content",content);
    }

    @Override
    public void getResult(ArrayList<String> result)
    {
        String methodName = result.get(0);
        switch(methodName)
        {
            case "queryCommentByStoryID":
                try {
                    ArrayList<ComtBusiModel> cm = ResultParce.parseComment(result.get(1));
                    commentsAdapter.updateItems(cm);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

                break;
            default:
                break;

        }
    }
}
