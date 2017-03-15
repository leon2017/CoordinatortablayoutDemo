package com.wangj.coordinatortablayoutdemo;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import static com.wangj.coordinatortablayoutdemo.R.id.tablayout;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTablayout;
    private String[]  TITLE = {"推荐","房源","体验","攻略"};
    private Toolbar mToolbar;
    private ImageView mImageview;
    private CollapsingToolbarLayout mToolbarlayout;
    private AppBarLayout mAppBarLayout;
    CollapsingToolbarLayoutState mLayoutState = CollapsingToolbarLayoutState.EXPANDED;
    private LinearLayout mLlContent;
    private ImageView mIvArrow;
    private LinearLayout mLlTitle;
    private AnimationSet mHideSet;
    private AnimationSet mShowSet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        mToolbarlayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        mImageview = (ImageView) findViewById(R.id.imageview);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTablayout = (TabLayout) findViewById(tablayout);
        mLlContent = (LinearLayout) findViewById(R.id.ll_content);
        mIvArrow = (ImageView) findViewById(R.id.iv_arrow);
        mLlTitle = (LinearLayout) findViewById(R.id.ll_title);
        inittablayout();
        initData();
    }

    private void initData() {
        collapsingListener();
        showAnim();
        hideAnim();
    }


    private void inittablayout() {
        mTablayout.addTab(mTablayout.newTab().setText(TITLE[0]));
        mTablayout.addTab(mTablayout.newTab().setText(TITLE[1]));
        mTablayout.addTab(mTablayout.newTab().setText(TITLE[2]));
        mTablayout.addTab(mTablayout.newTab().setText(TITLE[3]));
    }


    /**
     * 控制CollapsingToolbarLayout状态
     */
    private void collapsingListener() {

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (verticalOffset == 0) {
                    if (mLayoutState != CollapsingToolbarLayoutState.EXPANDED) {
                        mLayoutState = CollapsingToolbarLayoutState.EXPANDED;//修改状态标记为展开
                        mLlTitle.setVisibility(View.GONE);
                        mIvArrow.setVisibility(View.VISIBLE);
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (mLayoutState != CollapsingToolbarLayoutState.COLLAPSED) {
                        mLlContent.startAnimation(mHideSet);
                        mLlContent.setVisibility(View.GONE);//隐藏
                        mLlTitle.startAnimation(mShowSet);
                        mLlTitle.setVisibility(View.VISIBLE);
                        mIvArrow.setVisibility(View.GONE);
                        mLayoutState = CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠
                    }
                } else {
                    if (mLayoutState != CollapsingToolbarLayoutState.INTERNEDTATE) {
                        if (mLayoutState == CollapsingToolbarLayoutState.COLLAPSED) {
                            mLlContent.startAnimation(mShowSet);
                            mLlContent.setVisibility(View.VISIBLE);//由折叠变为中间状态时隐藏
                            mLlTitle.startAnimation(mHideSet);
                            mLlTitle.setVisibility(View.GONE);
                        }
                        mLayoutState = CollapsingToolbarLayoutState.INTERNEDTATE;//修改状态标记为中间
                    }
                }
            }
        });
    }

    /**
     * 定义出CollapsingToolbarLayout展开、折叠、中间三种状态
     */
    private enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
        INTERNEDTATE
    }


    private void showAnim() {
        mShowSet = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        TranslateAnimation showAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowSet.addAnimation(alphaAnimation);
        mShowSet.addAnimation(scaleAnimation);
//        mShowSet.addAnimation(showAction);
        mShowSet.setDuration(500);
    }

    private void hideAnim() {
        mHideSet = new AnimationSet(true);
        mHideSet.setInterpolator(new LinearInterpolator());
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        TranslateAnimation  hiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f);
        mHideSet.addAnimation(alphaAnimation);
        mHideSet.addAnimation(hiddenAction);
        mHideSet.setDuration(300);
    }
}
