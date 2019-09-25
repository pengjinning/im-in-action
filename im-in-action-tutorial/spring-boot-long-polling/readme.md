
# Long-Polling 长轮询

- [Asynchronous Requests](https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-async)

## 基于 DeferredResult 实现

DeferredResult和Callable都是为了异步生成返回值提供基本的支持。简单来说就是一个请求进来，如果你使用了DeferredResult或者Callable，
在没有得到返回数据之前，DispatcherServlet和所有Filter就会退出Servlet容器线程，但响应保持打开状态，一旦返回数据有了，这个DispatcherServlet就会被再次调用并且处理，
以异步产生的方式，向请求端返回值。
这么做的好处就是请求不会长时间占用服务连接池，提高服务器的吞吐量。

## 为什么使用DeferredResult？

Controller处理耗时任务，并且需要耗时任务的返回结果时；

当一个请求到达API接口，如果该API接口的return返回值是DeferredResult，在没有超时或者DeferredResult对象设置setResult时，接口不会返回，但是Servlet容器线程会结束，DeferredResult另起线程来进行结果处理(即这种操作提升了服务短时间的吞吐能力)，并setResult，如此以来这个请求不会占用服务连接池太久，如果超时或设置setResult，接口会立即返回。

#### 使用DeferredResult的流程：

浏览器发起异步请求
请求到达服务端被挂起
向浏览器进行响应，分为两种情况：
3.1 调用DeferredResult.setResult()，请求被唤醒，返回结果
3.2 超时，返回一个你设定的结果
浏览得到响应，再次重复1，处理此次响应结果
 

给人一种异步处理业务，但是却同步返回的感觉。

### 场景
浏览器向A系统发起请求，该请求需要等到B系统(如ＭＱ)给A推送数据时，A才会立刻向浏览器返回数据；

如果指定时间内Ｂ未给Ａ推送数据，则返回超时。