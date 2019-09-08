# 基于ElasticSearch6.8.2学习

## 项目描述
基于Docker环境运行的ElasticSearch6.8.2,目前项目中集成了3种与ElasticSearch交互的API:
1. 使用Spring Data ElasticSearch 3.1.10
2. 使用Rest High Level Client 6.8.2
3. 使用Jest API 6.3.1

项目暂时未使用脚手架的方式开发Vue前端界面,采用了原始的方式进行开发


## 计划及实现
目前计划从80s网站\猫眼电影票房上进行爬虫，爬取电影信息，然后存储到ES中，并实现相关操作

目前实现：
1. 从80s网站爬虫电影信息,使用Jsoup
2. 页面的分页查询操作,使用Spring Data ElasticSearch与Jest API
3. 全部删除操作(Jest Api使用deleteByQuery，Spring Data API可能是分页删除的)
4. 单条删除操作

待计划：
1. 从猫眼电影票房信息上进行爬虫
2. 80s模块的一些基本操作
3. 一些查询条件学习

发现的问题：
1. 对文档操作时需要注意refresh问题，如果删除完数据后,马上进行查询,结果不会立即刷新，目前基于jest api底层代码增加了refresh擦参数，基于spring data模块的采用前端延迟查询方式

## 技术架构

1. JDK 1.8
2. Spring Boot 2.1.7
3. Spring 5.1.9
4. Tomcat 9.0.22
5. Docker 18.09.1
6. ElasticSearch 6.8.2
7. Spring Data ElasticSearch 3.1.10
8. Rest High Level Client 6.8.2
9. Jest API 6.3.1
10. Vue2.6 + Vue Router + Element UI 2.11.1 + Axios + Qs


## Java API对比
|  API名称   | 端口  | 协议  | 说明  |
|  ----  | ----  | ----   | ----  |
|  Spring Data ElasticSearch   | 9300  | TCP  | 强依赖、上手容易、操作简单、依赖ES驱动包  |
|  High Level RestClient  | 9200  | HTTP  |  官方推荐、依赖ES驱动包  |
|  Jest   | 9200  | HTTP  | 弱依赖、可以不使用ES的驱动包  |


## 启动ElasticSearch服务

拉取ElasticSearch镜像
````
docker pull elasticsearch:6.8.2
````

创建容器并启动,指定内存512M，暴露9200与9300端口
````
docker run -e ES_JAVA_OPTS="-Xms512m -Xmx512m" -d -p 9200:9200 -p 9300:9300 --name study_es6 dbf758a9f11b
````
注意：该命令输入时未指定集群的名称，所以默认的名称为：docker-cluster，需要与项目中的application.yml文件中配置的一致


## 更改配置并启动

1. 对于使用ElasticSearchTemplate与Rest High Level Client的修改application.yml文件

执行StartMainApplication.java文件

````
   .   ____          _            __ _ _
  /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
 ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
  \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
   '  |____| .__|_| |_|_| |_\__, | / / / /
  =========|_|==============|___/=/_/_/_/
  :: Spring Boot ::        (v2.1.7.RELEASE)
 
 2019-08-29 23:45:38.194  INFO 9272 --- [  restartedMain] c.c.elasticsearch.StartMainApplication   : Starting StartMainApplication on longyu-mac.local with PID 9272 (/develop/IdeaWork/elasticsearchstudy/target/classes started by longyu in /develop/IdeaWork/elasticsearchstudy)
 2019-08-29 23:45:38.197  INFO 9272 --- [  restartedMain] c.c.elasticsearch.StartMainApplication   : No active profile set, falling back to default profiles: default
 2019-08-29 23:45:38.225  INFO 9272 --- [  restartedMain] .e.DevToolsPropertyDefaultsPostProcessor : Devtools property defaults active! Set 'spring.devtools.add-properties' to 'false' to disable
 2019-08-29 23:45:38.226  INFO 9272 --- [  restartedMain] .e.DevToolsPropertyDefaultsPostProcessor : For additional web related logging consider setting the 'logging.level.web' property to 'DEBUG'
 2019-08-29 23:45:38.670  INFO 9272 --- [  restartedMain] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data repositories in DEFAULT mode.
 2019-08-29 23:45:38.687  INFO 9272 --- [  restartedMain] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 13ms. Found 0 repository interfaces.
 2019-08-29 23:45:39.152  INFO 9272 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 7071 (http)
 2019-08-29 23:45:39.172  INFO 9272 --- [  restartedMain] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
 2019-08-29 23:45:39.172  INFO 9272 --- [  restartedMain] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.22]
 2019-08-29 23:45:39.241  INFO 9272 --- [  restartedMain] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
 2019-08-29 23:45:39.242  INFO 9272 --- [  restartedMain] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 1016 ms
 2019-08-29 23:45:39.424  INFO 9272 --- [  restartedMain] o.elasticsearch.plugins.PluginsService   : no modules loaded
 2019-08-29 23:45:39.425  INFO 9272 --- [  restartedMain] o.elasticsearch.plugins.PluginsService   : loaded plugin [org.elasticsearch.index.reindex.ReindexPlugin]
 2019-08-29 23:45:39.425  INFO 9272 --- [  restartedMain] o.elasticsearch.plugins.PluginsService   : loaded plugin [org.elasticsearch.join.ParentJoinPlugin]
 2019-08-29 23:45:39.425  INFO 9272 --- [  restartedMain] o.elasticsearch.plugins.PluginsService   : loaded plugin [org.elasticsearch.percolator.PercolatorPlugin]
 2019-08-29 23:45:39.425  INFO 9272 --- [  restartedMain] o.elasticsearch.plugins.PluginsService   : loaded plugin [org.elasticsearch.script.mustache.MustachePlugin]
 2019-08-29 23:45:39.425  INFO 9272 --- [  restartedMain] o.elasticsearch.plugins.PluginsService   : loaded plugin [org.elasticsearch.transport.Netty4Plugin]
 2019-08-29 23:45:40.010  INFO 9272 --- [  restartedMain] o.s.d.e.c.TransportClientFactoryBean     : Adding transport node : 127.0.0.1:9300
 2019-08-29 23:45:40.470  INFO 9272 --- [  restartedMain] io.searchbox.client.AbstractJestClient   : Setting server pool to a list of 1 servers: [http://127.0.0.1:9200]
 2019-08-29 23:45:40.470  INFO 9272 --- [  restartedMain] io.searchbox.client.JestClientFactory    : Using multi thread/connection supporting pooling connection manager
 2019-08-29 23:45:40.488  INFO 9272 --- [  restartedMain] io.searchbox.client.JestClientFactory    : Using custom GSON instance
 2019-08-29 23:45:40.488  INFO 9272 --- [  restartedMain] io.searchbox.client.JestClientFactory    : Node Discovery disabled...
 2019-08-29 23:45:40.489  INFO 9272 --- [  restartedMain] io.searchbox.client.JestClientFactory    : Idle connection reaping disabled...
 2019-08-29 23:45:40.643  INFO 9272 --- [  restartedMain] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
 2019-08-29 23:45:40.696  INFO 9272 --- [  restartedMain] o.s.b.a.w.s.WelcomePageHandlerMapping    : Adding welcome page: class path resource [static/index.html]
 2019-08-29 23:45:40.799  INFO 9272 --- [  restartedMain] o.s.b.d.a.OptionalLiveReloadServer       : LiveReload server is running on port 35729
 2019-08-29 23:45:40.836  INFO 9272 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 7071 (http) with context path ''
 2019-08-29 23:45:40.839  INFO 9272 --- [  restartedMain] c.c.elasticsearch.StartMainApplication   : Started StartMainApplication in 2.98 seconds (JVM running for 3.707)

````

## 测试访问

1. 对于测试使用ElasticSearchTemplate的:
````
http://localhost:7071/es/saveBook
...
````
2. 对于测试使用RestHighLevelClient的:
````
http://localhost:7071/highlevel/save
...
````
3. 对于访问主页面的(目前前端实现查询\爬虫\全部删除操作):
````
http://localhost:7071/
...
````
## 目前效果图
1. 基于Spring Data API的截图
![Image text](./images/t1.png)
2. 基于Jest API的截图
![Image text](./images/t2.png)
