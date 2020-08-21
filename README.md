# 微服务项目实战

### 介绍
1. cxd-mobile：移动端接口服务（可做app/小程序/网页端服务）
2. cxd-web：web管理端接口服务
3. cxd-scheduled：处理定时任务
4. cxd-tool：项目基础依赖工具
5. cxd-repository：项目基础数据依赖

### 框架结构
1. web端使用shiro做用户权限管理

2. 使用Nacos实现服务注册与发现

3. 使用Nacos作为配置中心

4. 使用gateway做服务网关

5. 使用feign做服务之间api调用

6. 使用hystrix做服务容错处理

7. 使用gateway网关对服务内swagger统一管理，从<http://localhost:8888/swagger-ui.html>

8. 使用zipkin实现分布式跟踪系统，需要下载zipkin.jar => https://github.com/openzipkin/zipkin

9. 使用seata实现分布式事务：https://seata.io/zh-cn/docs/user/configurations.html

   调用实现：

   cxd-mobile---DistributedTransactionController->saveTest()  

   --服务调用-->

   cxd-web---DistributedTransactionController->saveTest()  

### 软件结构
````
├─controller //提供外部接口
├─dao 
│  └─queryFactory //自定义查询
│  └─repository //jpa查询
├─pojo
│  ├─bo //业务对象
│  └─vo 
│      ├─req //前端请求对象
│      └─resp //前端返回对象
├─process
│  └─impl //接口的实现过程
└─service //具体过程的业务功能实现
````

### 安装教程

*说明：在开始使用之前建议您已了解并熟悉上述**框架结构**中所涉及到的技术模块*

1. 安装并启动nacos，官网安装配置教程：[Nacos Spring Cloud快速开始](https://nacos.io/zh-cn/docs/quick-start-spring-cloud.html)
2. 安装并启动seata，官网安装配置教程：[Seata新手部署指南](https://seata.io/zh-cn/docs/ops/deploy-guide-beginner.html) 不装也可以，只不过会一直打印日志，注册不上seata
3. install cxd-tool
4. install cxd-repository
5. start cxd-mobile/cxd-scheduled/cxd-web

### 注意事项

#### cxd-gateway

##### 开放性

1. 项目所有接口不对外开放，请求走cxd-gateway进行访问；
2. 项目使用swagger做接口文档，可用于接口测试；cxd-gateway集成统一调用cxd-mobile和cxd-web的swagger-ui；
   启动后可请求http://localhost:8888/swagger-ui.html进行访问；

##### 权限认证/参数校验/日志打印

1. 由GlobalRequestFilter#logTrace负责接口的访问权限认证和参数校验；
2. IgnoreUri忽略路径，忽略访问权限和返回参数包装的uri；可在nacos中进行配置；

##### 响应体包装

1. 由ModifyResponseBodyFilter调用ResponseBodyAdvice对返回body进行包装；

#### cxd-mobile

1. 从cxd-gateway进来的请求标记URIPrefixEnum#EXTERNAL为外部请求；
2. 从cxd-mobile到cxd-web的请求，即内部请求，非来自cxd-gateway的请求会在feign中标记URIPrefixEnum#INTERIOR为内部请求；内部服务调用不包装返回体；//后续可做拦截处理等

#### cxd-web

*说明：系统用户首先登陆，登陆后可以调用接口获取自己的权限菜单*

##### 访问日志

1. 对敏感权限使用LogHandlerInterceptor#postHandle记录操作日志，存储在sys_log表中；

##### 权限管理

1. 使用shiro做权限管理；

##### 注意事项

1. 新增接口时保持接口路径与sys_permission表所存储url路径基本一致，如接口路径approve/config/titleList，url存储为configTitleList
2. 接口功能为新增/修改/删除/锁定时，sys_permission表中权限名称则以该关键字符开头