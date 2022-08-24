package com.itheima.reggie.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import java.util.Random;

/**
 * 短信发送工具类
 */
public class SMSUtils {

	/**
	 * 发送短信
	 * @param signName 签名
	 * @param templateCode 模板
	 * @param phoneNumbers 手机号
	 * @param param 参数
	 */
	public static void sendMessage(String signName, String templateCode,String phoneNumbers,String param){
		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "", "");
		IAcsClient client = new DefaultAcsClient(profile);

		SendSmsRequest request = new SendSmsRequest();
		request.setSysRegionId("cn-hangzhou");
		request.setPhoneNumbers(phoneNumbers);
		request.setSignName(signName);
		request.setTemplateCode(templateCode);
		request.setTemplateParam("{\"code\":\""+param+"\"}");
		try {
			SendSmsResponse response = client.getAcsResponse(request);
			System.out.println("短信发送成功");
		}catch (ClientException e) {
			e.printStackTrace();
		}
	}
//	private SendSmsResponse SendVerificationCode(String phone,String name,String hphm,String cbsjStr) throws ClientException {
//
//		//设置超时时间-可自行调整
//		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
//		System.setProperty("sun.net.client.defaultReadTimeout", "10000");
//		//初始化ascClient需要的几个参数
//		final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
//		final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
//		//替换成你的AK
//		final String accessKeyId = "你短信的accessKeyId";//短信的accessKeyId
//		final String accessKeySecret = "你短信的accessKeySecret";//短信的accessKeySecret
//		//初始化ascClient,暂时不支持多region（请勿修改）
//		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,accessKeySecret);
//		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
//		IAcsClient acsClient = new DefaultAcsClient(profile);
//		//组装请求对象
//		SendSmsRequest request1 = new SendSmsRequest();
//		//使用post提交
//		request1.setMethod(MethodType.POST);
//		//必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
//		request1.setPhoneNumbers(phone);
//		//必填:短信签名-可在短信控制台中找到
//		request1.setSignName("签名，就是短信中大括号里的那个名称。例如：菜鸟驿站");
//		//必填:短信模板-可在短信控制台中找到
//		request1.setTemplateCode("SMS_171855032");
//		//可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
//		//友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
//		//向短信模板中传入参数
//
//		//生成几位的验证码
//		int n = 6;
//		StringBuilder code = new StringBuilder();
//		Random ran = new Random();
//		for (int i = 0; i < n; i++) {
//			code.append(Integer.valueOf(ran.nextInt(10)).toString());
//		}
//
//		request1.setTemplateParam("{\"code\":\""+code.toString()+"\"}");
//
////		request1.setTemplateParam("{\"name\":\""+name+"\"}");
////		request1.setTemplateParam("{\"hphm\":\""+hphm+"\"}");
////		request1.setTemplateParam("{\"cbsj\":\""+cbsjStr+"\"}");
//
//		//		request1.setTemplateParam("{\"name\":\""+name+"\",\"hphm\":\""+hphm+"\",\"cbsj\":\""+cbsjStr+"\"}");
//
//		//请求失败这里会抛ClientException异常
//		SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request1);
//
//		return sendSmsResponse;
//
//	}

}
