# apidesigner-for-android
this is android api designer framework
##gradle
```
dependencies {
    compile 'com.liangmayong:apidesigner:1.0.0'
}
```

##START

**1 SETP**：implements APIConstructor

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
	public boolean isSuccess(APIResponse response) {
		//request success?
	}
	
	@Override
	public String parseData(APIResponse response) {
		//parse data
	}

	@Override
	public <T> T parseEntity(Class<T> entityClass, APIResponse response) {
		//parse entity
		return null;
	}

	@Override
	public <T> List<T> parseEntitys(Class<T> entityClass, APIResponse response) {
		//parse entitys
		return null;
	}

	@Override
	public String parseMessage(APIResponse response) {
		//parse message
	}

	@Override
	public String parseCode(APIResponse response) {
		//parse code
	}

	@Override
	public void destroy(Context context) {
		//destroy request
	}

	@Override
	public void asynchronousRequest(Context context, APIMethod method, String url, APIParameter parameter,
			OnApiRequestListener listener) {
		//asynchronous request
	}

	@Override
	public APIResponse synchronizationRequest(Context context, APIMethod method, String url, APIParameter parameter)
			throws APIErrorException {
		//synchronization request
		return null;
	}

}

```
**2 STEP**：extends APIModule

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

	//return Call
	public Call login(String username,String password) {
	
		String url = "/user/login.do";
		//or
		//String url = "http://192.168.1.100/user/login.do";
		
		//parameter
		APIParameter parameter = new APIParameter();
		parameter.put("username", username);
		parameter.put("password", password);
		
		return createCall(APIMethod.POST,url,parameter);
	}

}

```
**3 STEP**use

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
		// init
		UserAPI userAPI = APIDesigner.constructor(APICon.class).create(UserAPI.class);
		//get call
		Call call = userAPI.login("user", "pass");
		//request
		call.asynchronousRequest(this, new OnApiEntityListener<UserInfo>() {

			@Override
			public void result(String code, String message, UserInfo entity, APIResponse response) {
				// result
			}

			@Override
			public void failure(Throwable error) {
				// failure
			}
		});
	}

}

```
##技术交流
交流：QQ群297798093，博主QQ591694077

email：ibeam@qq.com
##License
```
Copyright 2016 LiangMaYong

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
