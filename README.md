# apidesigner-for-android
this is android api designer framework

[中文](https://github.com/LiangMaYong/apidesigner-for-android/blob/master/zh.md)
## Gradle
```
dependencies {
    compile 'com.liangmayong:apidesigner:1.0.0'
}
```

## Start

**1 SETP**：Implements APIConstructor

```
public class APICon implements APIConstructor {

    ``````
}
```
**2 STEP**：Extends APIModule

```
public class UserAPI extends APIModule{
    //return Call
    public Call login(String username,String password) {
	
        String url = "/user/login.do";
        //parameter
        Parameter parameter = new Parameter();
        parameter.put("username", username);
        parameter.put("password", password);
        
        return createCall(APIMethod.POST,url,parameter);
    }
}
```
**3 STEP**: Request

```
// init
UserAPI userAPI = APIDesigner.constructor(APICon.class).create(UserAPI.class);
//get call
Call call = userAPI.login("user", "pass");
//request
call.asynchronousRequest(this, new OnApiEntityListener<UserInfo>() {

    @Override
    public void result(String code, String message, UserInfo entity, Response response) {
        // result
    }

    @Override
    public void failure(Throwable error) {
        // failure
    }
});
```
##Technical exchange
QQGroup：297798093

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
