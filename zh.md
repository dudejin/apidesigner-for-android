# apidesigner-for-android
this is android api designer framework

## Gradle
```
dependencies {
    compile 'com.liangmayong:apidesigner:1.0.0'
}
```

##开始使用

**第 1 步**：实现接口<code> APIConstructor </code>

```
public class APICon implements APIConstructor {

    ``````
}
```
**第 2 步**：声明请求接口，继承于<code> APIModule </code>

```
public class UserAPI extends APIModule{
    //return Call
    public Call login(String username,String password) {
	
        String url = "/user/login.do";
        //parameter
        APIParameter parameter = new APIParameter();
        parameter.put("username", username);
        parameter.put("password", password);
        
        return createCall(APIMethod.POST,url,parameter);
    }
}
```
**第 3 步**: 请求接口

```
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
```
##技术交流
QQGroup：297798093

email: ibeam@qq.com
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
