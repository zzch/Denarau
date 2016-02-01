package com.zhongchuangtiyu.denarau.Fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Cache;
import com.android.volley.VolleyError;
import com.zhongchuangtiyu.denarau.Adapters.PrivateCourseOrderFragmentListAdapter;
import com.zhongchuangtiyu.denarau.Adapters.PrivateCoursesGridAdapter;
import com.zhongchuangtiyu.denarau.Entities.PrivateCourses;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.CustomToast;
import com.zhongchuangtiyu.denarau.Utils.DateUtils;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;
import com.zhongchuangtiyu.denarau.Utils.Xlog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

/**
 * 作者：WangMeng on 2016/2/1 15:59
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class MyDialogFragment extends DialogFragment
{
    ListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.dialog_fragment, null, false);
        listView = (ListView) view.findViewById(R.id.listView2);
        getDialog().requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        String token = CacheUtils.getString(getActivity(), "token", null);
        String club_uuid = CacheUtils.getString(getActivity(), "clubuuid", null);
        String uuid = CacheUtils.getString(getActivity(), "privateCourseUuid", null);
        Map<String, String> map = new HashMap<>();
        MyApplication.volleyGET(APIUrls.PRIVATE_COURSES + "token=" + token + "&" + "club_uuid=" + club_uuid + "&" + "uuid=" + uuid, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(final String response)
            {
                final PrivateCourses data = PrivateCourses.instance(response);
                final List<PrivateCourses> list = data.generateListTodayInfo();
                int i = Integer.valueOf(CacheUtils.getString(getActivity(), "whichDay", null));
                final List<PrivateCourses.RecentlyScheduleEntity.ScheduleEntity> result = list.get(i).getScheduleEntity();
                PrivateCourseOrderFragmentListAdapter adapter = new PrivateCourseOrderFragmentListAdapter(getActivity(), result);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        if (result.get(position).getState().equals("unavailable"))
                        {
                            CustomToast.showToast(getActivity(), "此时已被预约，请重新选择有效时间");
                        } else if (result.get(position).getState().equals("available"))
                        {
                            CustomToast.showToast(getActivity(), "available");

                        }
                    }
                });
            }

            @Override
            public void netFail(VolleyError error)
            {
                CustomToast.showToast(getActivity(), "网络连接失败，请稍后再试");
            }
        });



        getDialog().getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
    }

}
