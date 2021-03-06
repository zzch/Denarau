package com.zhongchuangtiyu.denarau.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zhongchuangtiyu.denarau.Entities.Coaches;
import com.zhongchuangtiyu.denarau.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：WangMeng on 2015/10/26 14:56
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class CoachListAdapter extends BaseAdapter
{
    private static final int TYPE_FEATURED = 0;//高级教练
    private static final int TYPE_NORMAL = 1;//普通教练
    private static final int TYPE_COUNT = 2;//item类型的总数
    private int currentType;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private List<Coaches> list;
    private Context context;

    public CoachListAdapter(List<Coaches> list, Context context)
    {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Object getItem(int position)
    {
        if (getItemViewType(position) == TYPE_FEATURED)
        {
            return list.get(position);
        } else
        {
            return list.get(position);
        }
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public int getItemViewType(int position)
    {
        if (Coaches.FEATURED.equals(list.get(position).getCoachType()))
        {
            return TYPE_FEATURED;
        } else if (Coaches.NORMAL.equals(list.get(position).getCoachType()))
        {
            return TYPE_NORMAL;
        } else
        {
            return 100;
        }
    }

    @Override
    public int getViewTypeCount()
    {
        return TYPE_COUNT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View typeFeatured;
        View typeNormal;
        FeaturedViewHolder featuredViewHolder;
        NormalViewHolder normalViewHolder;
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        currentType = getItemViewType(position);
        if (currentType == TYPE_FEATURED)
        {
            if (convertView == null)
            {
                typeFeatured = LayoutInflater.from(context).inflate(R.layout.coach_tutorial_list_item1, null);
                featuredViewHolder = new FeaturedViewHolder(typeFeatured);
                typeFeatured.setTag(featuredViewHolder);
                convertView = typeFeatured;
            } else
            {
                typeFeatured = convertView;
                featuredViewHolder = (FeaturedViewHolder) typeFeatured.getTag();
            }
            if (list.get(position).getPortrait() != null)
            {
                imageLoader.displayImage(list.get(position).getPortrait().toString(), featuredViewHolder.headerCoachImage);
            }
            featuredViewHolder.headerCoachName.setText(list.get(position).getName());
            featuredViewHolder.headerCoachType.setText(list.get(position).getTitle());
            featuredViewHolder.headerCoachIntro.setText(list.get(position).getDescription());
            featuredViewHolder.headerCoachPrice.setText("￥" + list.get(position).getStarting_price());
        } else if (currentType == TYPE_NORMAL)
        {
            if (convertView == null)
            {
                typeNormal = LayoutInflater.from(context).inflate(R.layout.coach_tutorial_list_item2, null);
                normalViewHolder = new NormalViewHolder(typeNormal);
                typeNormal.setTag(normalViewHolder);
                convertView = typeNormal;
            } else
            {
                typeNormal = convertView;
                normalViewHolder = (NormalViewHolder) typeNormal.getTag();
            }
            if (list.get(position).getPortrait() != null)
            {
                imageLoader.displayImage(list.get(position).getPortrait().toString(), normalViewHolder.listCoachImage);
            }
            normalViewHolder.listCoachName.setText(list.get(position).getName());
            normalViewHolder.listCoachType.setText(list.get(position).getTitle());
            normalViewHolder.listCoachPrice.setText("￥" + list.get(position).getStarting_price());
        }
        return convertView;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'coach_tutorial_list_item1.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class FeaturedViewHolder
    {
        @Bind(R.id.headerCoachImage)
        ImageView headerCoachImage;
        @Bind(R.id.headerCoachName)
        TextView headerCoachName;
        @Bind(R.id.headerCoachType)
        TextView headerCoachType;
        @Bind(R.id.headerCoachIntro)
        TextView headerCoachIntro;
        @Bind(R.id.headerCoachPrice)
        TextView headerCoachPrice;


        FeaturedViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'coach_tutorial_list_item2.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class NormalViewHolder
    {
        @Bind(R.id.listCoachImage)
        ImageView listCoachImage;
        @Bind(R.id.listCoachName)
        TextView listCoachName;
        @Bind(R.id.listCoachType)
        TextView listCoachType;
        @Bind(R.id.listCoachPrice)
        TextView listCoachPrice;

        NormalViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
    }


}
