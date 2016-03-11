package com.zhongchuangtiyu.denarau.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;

/**
 * 范例
 * BaseWebActivity里面会动态生成一个WebView对象, 你也可以通过布局文件获取
 * 参考{@link android.webkit.WebViewFragment}中对WebView生命周期的控制
 */
public abstract class BaseWebActivity extends Activity
{
    private WebView mWebView;
    private FrameLayout rootView;
    private boolean mIsWebViewAvailable;

    /**
     * Called to instantiate the view. Creates and returns the WebView.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        destroyView();
        rootView = new FrameLayout(this);
        mWebView = new WebView(this);
        rootView.addView(mWebView);
        mIsWebViewAvailable = true;
        addContentView(rootView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    /**
     * Called when the fragment is visible to the user and actively running. Resumes the WebView.
     */
    @Override
    public void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    /**
     * Called when the fragment is no longer resumed. Pauses the WebView.
     */
    @Override
    public void onResume() {
        mWebView.onResume();
        super.onResume();
    }

    /**
     * Called when the fragment is no longer in use. Destroys the internal state of the WebView.
     */
    @Override
    public void onDestroy() {
        mIsWebViewAvailable = false;
        destroyView();
        super.onDestroy();
    }

    /**
     * destroy views
     */
    private void destroyView() {
        if (mWebView != null) {
            if (rootView != null) {
                rootView.removeView(mWebView);
            }
            mWebView.stopLoading();
            mWebView.removeAllViews();
            mWebView.destroy();
        }
        mWebView = null;
        rootView = null;
    }

    /**
     * Gets the WebView.
     */
    protected WebView getWebView() {
        return mIsWebViewAvailable ? mWebView : null;
    }

}
