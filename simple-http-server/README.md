# simple-http-server

## 数据封装
连接相关的数据封装在HttpRequest、HttpResponse和HttpConnection中。
其中HttpConnection记录了本次连接NIO绑定的SelectionKey，使用的通信管道SocketChannel以及HttpRequest和HttpResponse信息。
## 连接
simple-http-server系统使用了NIO和客户端数据交互。HttpConnector用于管理selector监听的连接并开启一个业务处理线程进行处理。HttpProcessor用于业务处理。
## Servlet
servlet是直接与浏览器交互的类。可以通过在配置文件中将uri和servlet进行绑定然后通过uri请求到某一个具体的servlet完成对应的业务操作。
## 配置
在测试包中有一个web.xml文件用于配置开启的http服务的相关配置，主要包括了http服务名称、开启端口，session过期时间以及uri和处理的servlet的映射关系。
## TODO List
1. cookie/session机制
2. 过滤器/拦截器机制