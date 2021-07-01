# simple-spring
## 开发文档
待补充...
## TODO List
1. jdk代理从接口实现的方法的AOP织入注解无效，但是已经生成了代理对象，在DefaultMethodInterceptor中的方法判断是否有AOP相关注解时会变成false。
目前在Spring较早的版本中发现了这个问题，后续研究一下怎么改进
2. 如何实现Transactional事物注解（大致想法）
> 2.1 使用一个TransactionTemplate类管理ORM框架
> 2.2 每次调用ORM的框架时都将ORM框架被调用的方法转化为sql语句，交给TransactionTemplate类执行
> 2.3 TransactionTemplate类执行期先从连接池获取一个连接，如果有Transactional注解就关闭自动提交
> 2.4 把执行的结果封装后返回给ORM框架调用的方法