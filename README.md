# 微服务项目实战练习

### 说明：
1. 如果您之前从未接触过Spring Cloud，建议先以[程序猿DD-Dalston版](http://blog.didispace.com/spring-cloud-learning/) 开始学习，或者您可以浏览我的学习Demo[SpringCloud-Learing](https://github.com/mwk719/SpringCloud-Learing)
2. 如果您想学习nacos在项目中应用，阿里出厂的nacos：一个更易于构建云原生应用的动态服务发现、配置管理和服务管理平台；用Spring Cloud组件理解的话，约等于eureka+spring cloud config
3. 如果您想学习seata在项目中应用，阿里出厂的seata：一款开源的分布式事务解决方案，致力于在微服务架构下提供高性能和简单易用的分布式事务服务
4. 以上项目组件您都有接触，但是想练习这些组件如何在项目中有机结合使用，那您真是来对地方了

*因作者技术水平有限，如您发现可能存在的问题，欢迎指正，以便相互学习！*

### 介绍
1. microservice-mobile：移动端接口服务（可做app/小程序/网页端服务）
2. microservice-web：web管理端接口服务
3. microservice-scheduled：处理定时任务
4. microservice-tool：项目基础依赖工具
5. microservice-repository：项目基础数据依赖

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

   microservice-mobile---DistributedTransactionController->saveTest()  

   --服务调用-->

   microservice-web---DistributedTransactionController->saveTest()  

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
3. install microservice-tool
4. install microservice-repository
5. start microservice-mobile/microservice-scheduled/microservice-web

### 功能事项

#### 缩包部署

- microservice-mobile、microservice-scheduled、microservice-web都已使用maven插件打包时将依赖jar包单独复制到lib目录，
  lib包可以单独放在服务器的和项目的相对路径，以后部署时就用上传jar包，减少部署项目上传的时间。

#### microservice-gateway

##### 开放性

1. 项目所有接口不对外开放，请求走microservice-gateway进行访问；
2. 项目使用swagger做接口文档，可用于接口测试；microservice-gateway集成统一调用microservice-mobile和microservice-web的swagger-ui；
   启动后可请求http://localhost:8888/swagger-ui.html进行访问；

##### 权限认证/参数校验/日志打印

1. 由GlobalRequestFilter#logTrace负责接口的访问权限认证和参数校验；
2. IgnoreUri忽略路径，忽略访问权限和返回参数包装的uri；可在nacos中进行配置；

##### 响应体包装

1. 由ModifyResponseBodyFilter调用ResponseBodyAdvice对返回body进行包装；

#### microservice-mobile

1. 从microservice-gateway进来的请求标记URIPrefixEnum#EXTERNAL为外部请求；
2. 从microservice-mobile到microservice-web的请求，即内部请求，非来自microservice-gateway的请求会在feign中标记URIPrefixEnum#INTERIOR为内部请求；内部服务调用不包装返回体；//后续可做拦截处理等

#### microservice-web

*说明：系统用户首先登陆，登陆后可以调用接口获取自己的权限菜单*

##### 访问日志

1. 对敏感权限使用LogHandlerInterceptor#postHandle记录操作日志，存储在sys_log表中；

##### 权限管理

1. 使用shiro做权限管理；

### 注意事项

1. 新增接口时保持接口路径与sys_permission表所存储url路径基本一致，如接口路径approve/config/titleList，url存储为configTitleList
2. 接口功能为新增/修改/删除/锁定时，sys_permission表中权限名称则以该关键字符开头