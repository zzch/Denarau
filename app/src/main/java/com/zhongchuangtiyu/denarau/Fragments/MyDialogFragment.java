package com.zhongchuangtiyu.denarau.Fragments;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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
    private ListView listView;
    private AlertDialog.Builder builder;
    private int reserved_at;
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

        final String token = CacheUtils.getString(getActivity(), "token", null);
        final String club_uuid = CacheUtils.getString(getActivity(), "clubuuid", null);
        final String uuid = CacheUtils.getString(getActivity(), "privateCourseUuid", null);
        final Map<String, String> map = new HashMap<>();
        MyApplication.volleyGET(APIUrls.PRIVATE_COURSES + "token=" + token + "&" + "club_uuid=" + club_uuid + "&" + "uuid=" + uuid, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(final String response)
            {
                final PrivateCourses data = PrivateCourses.instance(response);
                final List<PrivateCourses> list = data.generateListTodayInfo();
                final int i = Integer.valueOf(CacheUtils.getString(getActivity(), "whichDay", null));
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
                            CustomToast.showToast(getActivity(), "此时间已被预约，请重新选择有效时间");
                        } else if (result.get(position).getState().equals("available"))
                        {
                            String time = result.get(position).getTime();
//                            CustomToast.showToast(getActivity(), time);
//                            CustomToast.showToast(getActivity(), String.valueOf(i));
                            String startTwo = time.substring(0, 2);
                            String endTwo = time.substring(3, 5);
                            Xlog.d("startTwo" + "===" + startTwo);
                            Xlog.d("endTwo" + "===" + endTwo);
                            int hour = Integer.valueOf(startTwo);
                            int minute = Integer.valueOf(endTwo);
                            reserved_at = data.getRecently_schedule().get(i).getDate() + hour * 3600 + minute + 60;
                            Map<String, String> map1 = new HashMap<String, String>();
                            map1.put("token", token);
                            map1.put("club_uuid", club_uuid);
                            map1.put("course_uuid", uuid);
                            map1.put("reserved_at",String.valueOf(reserved_at));
                            Xlog.d("token" + "===" + token);
                            Xlog.d("club_uuid" + "===" + club_uuid);
                            Xlog.d("course_uuid" + "===" + uuid);
                            Xlog.d("reserved_at" + "===" + reserved_at);
                            MyApplication.volleyPOST(APIUrls.PRIVATE_COURSES_RESERVE, map1, new MyApplication.VolleyCallBack() {
                                @Override
                                public void netSuccess(String response) {
                                    if (response.contains("20004"))
                                    {
                                        builder = new AlertDialog.Builder(getActivity());
                                        builder.setTitle("重复预约");
                                        builder.setMessage("您已经预约过当天的打位");
                                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                                        { //设置确定按钮
                                            @Override
                                            public void onClick(DialogInterface dialog, int which)
                                            {
                                                dialog.dismiss(); //关闭dialog
//                            Toast.makeText(PositionOrderActivity.this, "确认" + which, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        builder.setCancelable(false);
                                        builder.create();
                                        builder.show();
                                        Xlog.d(response + "response------------------------------------------");

                                    }else
                                    {
                                        dismiss();
                                        String formatedDate = DateUtils.getDateToString7(Long.valueOf(reserved_at));
                                        builder = new AlertDialog.Builder(getActivity());
                                        builder.setTitle("预定成功");
                                        builder.setMessage("你已经成功预定" + formatedDate + "的课程");
                                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss(); //关闭dialog
                                            }
                                        });
                                        builder.setCancelable(false);
                                        builder.create();
                                        builder.show();
                                        Xlog.d(response + "response------------------------------------------");
                                    }
                                }

                                @Override
                                public void netFail(VolleyError error) {
                                    CustomToast.showToast(getActivity(),"预约失败，请检查网络连接");
                                dismiss();
                                }
                            });
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
