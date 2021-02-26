# netty学习记录

## BIO
缺点：
1. 并发较大的时候需要创建大量的线程
2. 没有数据传输的时候线程会闲置阻塞，浪费资源  
![avatar](src/main/resources/img/compare.png)
# NIO
nio实现非阻塞的核心为Buffer，通过缓冲区缓冲，
![avatar](src/main/resources/img/nio-core.png)