package com.zhongchuangtiyu.denarau.event;


import com.youzan.sdk.YouzanSDK;
import com.youzan.sdk.YouzanUser;
import com.youzan.sdk.web.bridge.IBridgeEnv;
import com.youzan.sdk.web.event.UserInfoEvent;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;


/**
 * ����    : ͬ��ע�������û���Ϣ(�߽�ʵ��)
 *
 * ʹ�ó���: �����޵���ҳ�п��ܻ����ڿͻ����û���¼(����һЩAPP��ǿ���û��ȵ�¼��ʹ��),
 * ��������: ҳ���Զ�����
 * ˵��    : һ���û���Ҫ������APP��ͨ�����޵�SDK����������׹���, ��Ҫ�����޵ĺ�̨ע��һ���û�.
 * ����˵��:
 *
 * {@link com.youzan.sdk.YouzanUser}
 *          UserId :    �����ֶ�
 *                  ����: �û�ID,���ڱ�ʶ���û���APP����Ψһ��(�Ƽ�ʹ���û����ֻ��ŵ�),
 *                  ����: �ַ���
 *
 *          Avatar :    �Ǳ�Ҫ�ֶ�
 *                  ����: ͷ��ͼ������
 *                  ����: �ַ���
 *
 *          Gender :    �Ǳ�Ҫ�ֶ�
 *                  ����: �Ա�
 *                  ����: ����, 1��������, 0����Ů��
 *
 *          NickName :  �Ǳ�Ҫ�ֶ�
 *                  ����: �ǳ�
 *                  ����: �ַ���
 *
 *          Telephone :  �Ǳ�Ҫ�ֶ�
 *                  ����: �ֻ���
 *                  ����: �ַ���
 *
 *          UserName :  �Ǳ�Ҫ�ֶ�
 *                  ����: �û���
 *                  ����: �ַ���
 *
 *  {@link IBridgeEnv}
 *          ����: ����һЩ�����Ļ�����Ϣ
 *          ˵��: {@code IBridgeEnv.getWebView()}���Ի�ȡWebView����
 *                {@code IBridgeEnv.getActivity()}���Ի�ȡActivity����
 *
 */
public final class UserEvent extends UserInfoEvent
{
    @Override
    public void call(IBridgeEnv env) {
        YouzanUser user = new YouzanUser();
        String userId = CacheUtils.getString(MyApplication.getAppContext(), "userId", null);
//        user.setUserId("1234566");
//        user.setAvatar("http://..");
//        user.setGender(1);
//        user.setNickName("王萌");
//        user.setTelephone("12345678901");
//        user.setUserName("aaaa");

        String userUuid = CacheUtils.getString(MyApplication.getAppContext(), "userUuid", null);
        user.setUserId(userUuid);
        String userPortrait = CacheUtils.getString(MyApplication.getAppContext(), "userPortrait", null);
        user.setAvatar(userPortrait);
        String gender = CacheUtils.getString(MyApplication.getAppContext(), "gender", null);
        if (gender.equals("male"))
        {
            user.setGender(1);
        } else if (gender.equals("female"))
        {
            user.setGender(0);
        }
        String nickName = CacheUtils.getString(MyApplication.getAppContext(), "name", null);
        user.setNickName(nickName);
        String telephone = CacheUtils.getString(MyApplication.getAppContext(), "telephone", null);
        user.setTelephone(telephone);
        user.setUserName(nickName);

        YouzanSDK.syncRegisterUser(env.getWebView(), user);
    }
}