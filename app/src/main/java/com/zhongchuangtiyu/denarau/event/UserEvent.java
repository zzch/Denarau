package com.zhongchuangtiyu.denarau.event;

import com.youzan.sdk.YouzanSDK;
import com.youzan.sdk.YouzanUser;
import com.youzan.sdk.web.bridge.IBridgeEnv;
import com.youzan.sdk.web.event.UserInfoEvent;


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
        user.setUserId("12345");
        user.setAvatar("http://..");
        user.setGender(1);
        user.setNickName("С�����ǳ�");
        user.setTelephone("12345678901");
        user.setUserName("С��");

        //ִ��ͬ��ע�������û�����
        YouzanSDK.syncRegisterUser(env.getWebView(), user);
    }
}