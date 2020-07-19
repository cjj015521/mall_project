# mall_project
    该电商项目是一个综合性的B2C平台，类似京东商城、天猫商城。该项目采用分布式系统架构，子系统之间都是调用服务来实现系统之间的通信，使
用http协议传递json数据方式实现。这样降低了系统之间的耦合度，提高了系统的扩展性。为了提高系统的性能使用Redis做系统缓存，并使用Redis
实现session共享。为了保证Redis的性能使用Redis的集群。搜索功能使用Solr做搜索引擎。使用Nginx来作为Http代理服务器。