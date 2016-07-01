# apidesigner-for-android
this is android api designer framework
#use
Maven
```
<dependency>
  <groupId>com.liangmayong</groupId>
  <artifactId>apidesigner</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```
Gradle
```
compile 'com.liangmayong:apidesigner:1.0.0'
```

##编写接口

**第一步**：创建接口构造器（实现APIConstructor接口），看代码

```
package com.liangmayong.api;

import java.util.List;

import org.json.JSONObject;

import com.liangmayong.apidesigner.entity.APIParameter;
import com.liangmayong.apidesigner.entity.APIResponse;
import com.liangmayong.apidesigner.exception.APIErrorException;
import com.liangmayong.apidesigner.interfaces.APIConstructor;
import com.liangmayong.apidesigner.listener.OnApiRequestListener;

import android.content.Context;

public class APICon implements APIConstructor {

	@Override
	public String getBaseUrl() {
		return "http://192.168.1.100/";
	}

	@Override
	public String parseData(APIResponse response) {
	  //编写解析规则
		JSONObject json = (JSONObject) response.getResponse();
		try {
			return json.getString("data");
		} catch (Exception e) {
			return "";
		}
	}

	@Override
	public <T> T parseEntity(Class<T> entityClass, APIResponse response) {
	  //在这里，你来决定使用gson还是fastjson，或者其他解析库
		return null;
	}

	@Override
	public <T> List<T> parseEntitys(Class<T> entityClass, APIResponse response) {
	  //在这里，你来决定使用gson还是fastjson，或者其他解析库
		return null;
	}

	@Override
	public String parseMessage(APIResponse response) {
	  //编写解析规则
		JSONObject json = (JSONObject) response.getResponse();
		try {
			return json.getString("message");
		} catch (Exception e) {
			return "";
		}
	}

	@Override
	public String parseCode(APIResponse response) {
		JSONObject json = (JSONObject) response.getResponse();
		try {
			return json.getString("code");
		} catch (Exception e) {
			return "";
		}
	}

	@Override
	public void destroy(Context context) {
		//这里写上停止请求的方法
	}

	@Override
	public void asynchronousRequest(Context context, APIMethod method, String url, APIParameter parameter,
			OnApiRequestListener listener) {
		//这里是异步请求具体实现，通过listener返回请求结果
		//在这里你来决定用Volley还是okhttp,或者其他的请求库
		//method 是请求方式，包括GET,POST,DELETE,PUT
	}

	@Override
	public APIResponse synchronizationRequest(Context context, APIMethod method, String url, APIParameter parameter)
			throws APIErrorException {
		//这里是同步请求具体实现，直接返回结果
		//在这里你来决定用Volley还是okhttp,或者其他的请求库
		//method 是请求方式，包括GET,POST,DELETE,PUT
		return null;
	}

}

```
**第二步**：定义接口（继承APIModule，可以根据不同的模块，定义对应的接口Module）

```
package com.liangmayong.api;

import java.lang.annotation.Retention;

import com.liangmayong.api.bean.UserInfo;
import com.liangmayong.apidesigner.APIModule;
import com.liangmayong.apidesigner.entity.APIParameter;
import com.liangmayong.apidesigner.entity.APIResponse;
import com.liangmayong.apidesigner.exception.APIErrorException;
import com.liangmayong.apidesigner.interfaces.APIConstructor.APIMethod;
import com.liangmayong.apidesigner.listener.OnApiEntityListener;

import android.content.Context;

public class UserAPI extends APIModule{

	//定义Call接口，Call接口是在调用时选择同步请求还是异步请求
	public Call login(String username,String password) {
		//使用相对接口地址，会自动与APICon中的getBaseUrl()返回值拼接
		String url = "/user/login.do";

		//使用相对接口地址，会自动与APICon中的getBaseUrl()返回值拼接
		//String url = "http://192.168.1.100/user/login.do";
		
		//封装请求参数
		APIParameter parameter = new APIParameter();
		parameter.put("username", username);
		parameter.put("password", password);
		return createCall(APIMethod.POST,url,parameter);
	}

	//定义异步接口,自动根据APICon定义的自动解析出对应的结果
	public void login(Context context,String username,String password,OnApiEntityListener<UserInfo> listener) {

		//使用相对接口地址，会自动与APICon中的getBaseUrl()返回值拼接
		String url = "/user/login.do";

		//使用相对接口地址，会自动与APICon中的getBaseUrl()返回值拼接
		//String url = "http://192.168.1.100/user/login.do";
		APIParameter parameter = new APIParameter();
		parameter.put("username", username);
		parameter.put("password", password);
		createCall(APIMethod.POST,url,parameter).asynchronousRequest(context, listener);
	}
	

	public APIResponse login(Context context, String username, String password) throws APIErrorException {

		// 使用相对接口地址，会自动与APICon中的getBaseUrl()返回值拼接
		String url = "/user/login.do";

		// 使用相对接口地址，会自动与APICon中的getBaseUrl()返回值拼接
		// String url = "http://192.168.1.100/user/login.do";
		APIParameter parameter = new APIParameter();
		parameter.put("username", username);
		parameter.put("password", password);
		return createCall(APIMethod.POST, url, parameter).synchronousRequest(context);
	}
}

```
**第三步**使用接口

```
package com.liangmayong.api;

import com.liangmayong.api.bean.UserInfo;
import com.liangmayong.apidesigner.APIDesigner;
import com.liangmayong.apidesigner.APIModule.Call;
import com.liangmayong.apidesigner.entity.APIResponse;
import com.liangmayong.apidesigner.exception.APIErrorException;
import com.liangmayong.apidesigner.listener.OnApiEntityListener;

import android.app.Activity;
import android.os.Bundle;

public class APIActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 创建接口模块
		UserAPI userAPI = APIDesigner.constructor(APICon.class).create(UserAPI.class);
		// 1，通过Call调用
		Call call = userAPI.login("user", "pass");
		//异步请求
		call.asynchronousRequest(this, new OnApiEntityListener<UserInfo>() {

			@Override
			public void result(String code, String message, UserInfo entity, APIResponse response) {
				// 请求成功
			}

			@Override
			public void failure(Throwable error) {
				// 请求失败
			}

			@Override
			public void loading() {
				// 开始请求

			}
		});
		//同步请求
		try {
			APIResponse response = call.synchronousRequest(this);
			//请求成功
			//获得解析器，解析结果Code
			response.getParser().getCode();
			//获得解析器，解析结果Message
			response.getParser().getMessage();
			//获得解析器，解析结果Message
			UserInfo info = response.getParser().getEntity(UserInfo.class);
		} catch (APIErrorException e) {
			//请求失败
		}
		
	}

}

```
##技术交流
交流：QQ群297798093，博主QQ591694077

email：ibeam@qq.com
