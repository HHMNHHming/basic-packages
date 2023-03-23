# 全局信息处理模块

### 注:
*当在其它应用中使用此模块时，需要注意如果应用的启动类所在目录不是且不包含com.gwm.zjpt.basicpackages.global
时，应当在启动类上增加如下注解*
>@SpringBootApplication(scanBasePackages = {"当前应用目录","com.gwm.avpdatacloud.basicpackages.global"})
###### 解释：
SpringBoot默认加载启动类所在目录下的所有组件，如果global与启动类不在同一目录下，则不会被加载。
也可以进一步指定：com.gwm.avpdatacloud.basicpackages.global.advice[或aop或filter或interceptor];
> aop：全局日志切面
> 
> advice：全局异常处理
> 
> filter：过滤各个服务之间通过feign的调用
> 
> interceptor：拦截器，为feign全局配置header AUTHORIZATION
#### 获取当前登录用户信息
```text
*在controller的接口中可以通过以下方式获取当前访问用户信息：
* @ApiOperation(value = "获取当前角色所属资源",httpMethod = "GET")
* @GetMapping("/findMyResource")
* @ResponseBody
* public ResultData findMyResource(@RequestAttribute String userName,@RequestAttribute String[] roleNames,@RequestAttribute Long[] roleIds){
*     ......
* }
* 在接口中增加参数userName,roleNames获取当前用户名称以及角色信息
```
### AOP
#### 配置项 com.gwm.avpdatacloud.basicpackages.log.level: debug,info,warn,error
> Default: debug , 日志打印请求参数值，以及返回结果。
> 目前只对debug敏感。
#### 当类或方法注解@GDC时，AOP才会生效