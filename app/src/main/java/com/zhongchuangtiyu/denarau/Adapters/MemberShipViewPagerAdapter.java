package com.zhongchuangtiyu.denarau.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.zhongchuangtiyu.denarau.Entities.MemberShipViewPagerCourseEntity;
import com.zhongchuangtiyu.denarau.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by WangMeng on 2015/10/14.
 */
public class MemberShipViewPagerAdapter extends PagerAdapter
{
    @Bind(R.id.membershipViewPagerCourseImage)
    ImageView membershipViewPagerCourseImage;
    @Bind(R.id.membershipViewPagerCourseName)
    TextView membershipViewPagerCourseName;
    @Bind(R.id.membershipViewPagerCardType)
    TextView membershipViewPagerCardType;
    @Bind(R.id.membershipViewPagerCardNumber)
    TextView membershipViewPagerCardNumber;
    @Bind(R.id.membershipViewPagerCardBalance)
    TextView membershipViewPagerCardBalance;
    @Bind(R.id.membershipCardViewPagerRoot)
    RelativeLayout membershipCardViewPagerRoot;
    private List<View> pagerViews;
    private List<MemberShipViewPagerCourseEntity> memberShipViewPagerEntities;
    private Context context;
    private ImageLoader imageLoader;

    public MemberShipViewPagerAdapter(Context context, List<MemberShipViewPagerCourseEntity> memberShipViewPagerEntities)
    {
        this.memberShipViewPagerEntities = memberShipViewPagerEntities;
        this.context = context;
        this.imageLoader = ImageLoader.getInstance();
        initData();
    }

    private void initData()
    {
        pagerViews = new ArrayList<View>();

        for (int i = 0; i < memberShipViewPagerEntities.size(); i++)
        {
            // 填充显示图片的页面布局
            View view = View.inflate(context, R.layout.membership_viewpager_item, null);
            pagerViews.add(view);
        }
    }

    @Override
    public int getCount()
    {
        if (memberShipViewPagerEntities.size() > 1)
        {
            return Integer.MAX_VALUE;
        }
        return memberShipViewPagerEntities.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        int index = position % memberShipViewPagerEntities.size();
        View view = pagerViews.get(index);
        MemberShipViewPagerCourseEntity entity = memberShipViewPagerEntities.get(index);

        String url = entity.getMembershipViewPagerCourseImageUrl();

        imageLoader.loadImage(url, new ImageLoadingListener()
        {

            @Override
            public void onLoadingStarted(String imageUri, View view)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void onLoadingFailed(String imageUri, View view,
                                        FailReason failReason)
            {
                // TODO Auto-generated method stub

            }


            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
            {
                membershipViewPagerCourseImage.setImageBitmap(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view)
            {
                // TODO Auto-generated method stub

            }
        });
        membershipViewPagerCourseName.setText(entity.getMembershipViewPagerCourseName());
        membershipViewPagerCardType.setText(entity.getMembershipViewPagerCardType());
        membershipViewPagerCardNumber.setText(entity.getEmbershipViewPagerCardNumber());
        membershipViewPagerCardBalance.setText(entity.getMembershipViewPagerCardBalance());
        membershipCardViewPagerRoot.setBackgroundColor(Integer.valueOf(entity.getMembershipViewPagerBackgroundRgb()));
        container.addView(view);
        return view;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public MemberShipViewPagerCourseEntity getEntity(int position) {
        return memberShipViewPagerEntities.get(position % memberShipViewPagerEntities.size());
    }

    public Bitmap getBitmap(int position) {
        Bitmap bitmap = null;
        View view = pagerViews.get(position % pagerViews.size());
        Drawable drawable = membershipViewPagerCourseImage.getDrawable();
        if(drawable != null && drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            bitmap = bd.getBitmap();
        }

        return bitmap;
    }
}
