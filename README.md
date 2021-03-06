# netty学习记录

## 1. BIO
缺点：
1. 并发较大的时候需要创建大量的线程
2. 没有数据传输的时候线程会闲置阻塞，浪费资源  
![avatar](src/main/resources/img/compare.png)
## 2. NIO
nio实现非阻塞的核心为Buffer，通过缓冲区缓冲，
![avatar](src/main/resources/img/nio-core.png)


### Buffer
#### buffer读写
```
public final Buffer flip() {
        limit = position;
        position = 0;
        mark = -1;
        return this;
    }
```
常见方法
![avatar](src/main/resources/img/buffer-methods.png)
#### ByteBuffer
buffer中最常用的为ByteBuffer,其中最常见的方法有如下：
![avatar](src/main/resources/img/byte-buffer-methods.png)

### Selector
![avatar](src/main/resources/img/selector.png)
#### 其常用方法如下:
![avatar](src/main/resources/img/selector-methods.png)
#### selector及selectionKey等关系
![avatar](src/main/resources/img/selector-componets.png)

#### NIOServer NIOClinet demo
服务端: com.fh.nio.NIOServer
客户端: com.fh.nio.NIOClient

#### NIO群聊
服务端: com.fh.nio.GroupChatServer
客户端: com.fh.nio.GroupChatClient

## 3. 零拷贝
零拷贝：没有CPU拷贝，但是还有DMA拷贝 
DMA: Direct Memory Access(直接内存拷贝)
![avatar](src/main/resources/img/copy.png)
![avatar](src/main/resources/img/copy-difference.png

## 4. netty
模型
传统阻塞模型
Reactor(三种叫法：反应器模式、分发者模式Dispatcher、通知者模式notifier)

* 单Reactor单线程
* 单Reactor多线程
* 主从Reactor多线程

pipline业务逻辑中阻塞的部分用taskQueue去异步处理



