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
import com.zhongchuangtiyu.denarau.Entities.Provision;
import com.zhongchuangtiyu.denarau.Entities.Provisions;
import com.zhongchuangtiyu.denarau.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：WangMeng on 2015/10/27 18:02
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class ProvisionsGridAdapter extends BaseAdapter
{
    private List<Provision> list;
    private Context context;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    public ProvisionsGridAdapter(List<Provision> list, Context context)
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Provision provisions = list.get(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.provisions_grid_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else
        {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        imageLoader.displayImage(provisions.getImage(), viewHolder.foodImage);
        viewHolder.foodName.setText(provisions.getName());
        viewHolder.foodPrice.setText(provisions.getPrice());
        return view;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'provisions_grid_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder
    {
        @Bind(R.id.foodImage)
        ImageView foodImage;
        @Bind(R.id.foodName)
        TextView foodName;
        @Bind(R.id.foodPrice)
        TextView foodPrice;

        ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
    }
}
