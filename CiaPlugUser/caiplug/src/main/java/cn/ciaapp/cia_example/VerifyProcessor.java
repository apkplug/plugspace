package cn.ciaapp.cia_example;

import android.content.Intent;
import android.util.Log;

import org.osgi.framework.BundleContext;

import java.net.URI;
import java.util.HashMap;

import cn.ciaapp.sdk.CIAService;
import cn.ciaapp.sdk.VerificationListener;

/**
 * Created by qinfeng on 2016/8/26.
 */
public class VerifyProcessor extends BaseProcessor {
    public VerifyProcessor(BundleContext context) {
        super(context);
    }

    @Override
    public void Receive(URI uri, HashMap<String, Object> hashMap) {

        String phoneNumber = (String) hashMap.get("phone");
        String appid = (String) hashMap.get("AppId");
        String authKey = (String) hashMap.get("AuthKey");
        Log.e("init",appid+" "+authKey);

        CIAService.init(context.getBundleContext(), appid, authKey);

        CIAService.startVerification(phoneNumber, new VerificationListener() {
            /**
             * 校验结果回调
             * @param status        验证状态码
             * @param msg           验证状态的描述
             * @param transId       当前验证请求的流水号，服务器可以根据流水号查询验证的状态
             */
            @Override
            public void onStateChange(int status, String msg, String transId) {

                /**
                 * status 是返回的状态码，CIAService包含了一些常量
                 * @see CIAService.VERIFICATION_SUCCESS 验证成功
                 * @see CIAService.VERIFICATION_FAIL 验证失败，请查看 msg 参数描述，例如手机号码格式错误，手机号格式一般需要开发者先校验
                 * @see CIAService.SECURITY_CODE_MODE   验证码模式
                 *      验证码模式：需要提示用户输入验证码，调用
                 *      @see CIAService.getSecurityCode()    获取当前的验证码，格式类似05311234****，需要提示用户****部分是输入的验证码内容
                 * @see CIAService.REQUEST_EXCEPTION    发生异常，msg 是异常描述，例如没有网络连接，网络连接状况一般需要开发者先判断
                 *
                 * 其他情况，status不在上述常量中，是服务器返回的错误，查看 msg 描述，例如 appId 和 authKey 错误。
                 */

                switch (status) {
                    case CIAService.VERIFICATION_SUCCESS: // 验证成功
                        // TODO 进入下一步业务逻辑
                        dispatchAgent.reply(getMsgId(),true,"验证成功");
                        break;
                    case CIAService.SECURITY_CODE_MODE: // 验证码模式
                        // 进入输入验证码的页面，并提示用户输入验证码
                        Intent intent = new Intent(context.getBundleContext(), SecurityCodeActivity.class);
                        intent.putExtra("msgid",getMsgId());
                        context.getBundleContext().startActivity(intent);
                        break;
                    case CIAService.VERIFICATION_FAIL:
                        dispatchAgent.reply(getMsgId(),false,"验证失败：" + msg);
                        break;
                    case CIAService.REQUEST_EXCEPTION:
                        dispatchAgent.reply(getMsgId(),false,"请求异常：" + msg);
                        break;
                    default:
                        // 服务器返回的错误
                        //dispatchAgent.reply(getMsgId(),false,msg);
                }
            }
        });
    }
}
