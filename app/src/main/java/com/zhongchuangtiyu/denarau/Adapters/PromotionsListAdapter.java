package com.zhongchuangtiyu.denarau.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zhongchuangtiyu.denarau.Entities.Promotions;
import com.zhongchuangtiyu.denarau.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：WangMeng on 2015/11/3 14:15
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class PromotionsListAdapter extends BaseAdapter
{
    private List<Promotions> list;
    private Context context;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    public PromotionsListAdapter(List<Promotions> list, Context context)
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
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    public void addData(List<Promotions> datas)
    {
        this.list.addAll(datas);
        notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Promotions promotions = list.get(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.promotions_list_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else
        {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        if (!imageLoader.isInited()) {
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        }
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        viewHolder.promotionsListItemImage.setMinimumWidth(wm.getDefaultDisplay().getWidth());
        viewHolder.promotionsListItemImage.setMinimumHeight(wm.getDefaultDisplay().getWidth() / 3);
        viewHolder.promotionsListItemImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageLoader.displayImage(promotions.getImage(), viewHolder.promotionsListItemImage);
        return view;
    }
    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'promotions_list_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder
    {
        @Bind(R.id.promotionsListItemImage)
        ImageView promotionsListItemImage;

        ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
    }
}
