# 分布式DAG调度系统
### aries——分布式DAG调度系统（Distributed DAG scheduling）
#### 1、说明

##### 	现有很多有DAG调度的框架，但是都是基于业务实现的，有时候很难复用，因为调度会和任务有严重的耦合问题，毕竟开发一个DAG调度平台是为任务服务的，所以我抽离了这个业务，分布式调度不依赖任务本身，分布式执行也不依赖任务本身

#### 2、分布式调度执行整体架构如下

![分布式DAG调度系统](https://github.com/yafeiwang1240/aries/blob/master/%E5%88%86%E5%B8%83%E5%BC%8FDAG%E8%B0%83%E5%BA%A6%E7%B3%BB%E7%BB%9F.png)
