## 基本概念

### 消息模型

RocketMQ主要有三部分组成：

* Producer: 负责生产消息。消息生产者会把业务应用系统里产生的消息发送到broker服务器。RocketMQ提供多种发送方式：
  * 同步发送(Sync)：会等待发送结果后才返回；

  * 异步发送(Async)：发送完后，立刻返回。Client在拿到Broker的响应结果后，会回调指定的callback。这个API也可以指定Timeout，不指定也是默认的3000ms.

  * 单向发送(OneWay)：比较简单，发出去后，什么都不管直接返回。

    同步发送和异步发送均需要Broker返回确认信息，单向发送不需要。

* Broker: 负责存储、转发消息；

* Consumer: 负责消费消息。提供两种消费形式：`拉式消费`和`推式消费`。

* Topic: 表示一类消息的集合，每个主题包含若干条消息，每条消息只能属于一个主题，是RocketMQ进行消息订阅的基本单位。

* NameServer: 充当路由消息的提供者。生产者或消费者能够通过NameServer查找各主题相应的BrokerIP列表。多个NameSrv实例组成集群，但相互独立，没有信息交换。

* ProducerGroup: 同一类Producer的集合，如果原始生产者在发送之后奔溃，则Broker服务器会联系同一生产者组的其他生产者实例以提交或者回溯消费。

* ConsumerGroup: 同一类Consumer的集合，消费组使得消息在消费时可以实现负载均衡和容错的目标变得容器。RockerMQ支持两种消费模式：

  * 集群消费(Clustering)：相同ConsumerGroup的每个Consumer实例平均分摊消息；
  * 广播消费(Broadcasting)：相同ConsumerGroup的每个Consumer实例都接收全量的消息。


