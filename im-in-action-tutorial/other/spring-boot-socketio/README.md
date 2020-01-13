# An example of real-time chat application 

Built with 
- [netty-socketio 1.7.17](https://github.com/mrniko/netty-socketio) 
- Spring Boot 2.1.1.RELEASE
- socket.io-client 2.2.0

In this example, `SocketIONamespace` is used for declaring modules. 

This example project is inspired by the following projects.
- https://github.com/Heit/netty-socketio-spring
- https://github.com/mrniko/netty-socketio-demo

# Usage

## Server end

- Run server by command `mvn spring-boot:run`   
- Or build single executable jar file with `mvn package` and un single jar `java -jar rt-server.jar`

## Client end

- Put the `/client` directory under an HTTP server. Then open it from the browser after starting the server side.

# License

MIT

# Demo scenarios

By default you will run a chat which communcate with server via json objects.
There are several demo scenarios available:

 `Class` - `Web client page`

 `com.corundumstudio.socketio.demo.ChatLauncher` - `/client/index.html`

 `com.corundumstudio.socketio.demo.EventChatLauncher` - `/client/event-index.html`

 `com.corundumstudio.socketio.demo.SslChatLauncher` - `/client/ssl-event-index.html`

 `com.corundumstudio.socketio.demo.NamespaceChatLauncher` - `/client/namespace-index.html`

 `com.corundumstudio.socketio.demo.AckChatLauncher` - `/client/ack-index.html`

 `com.corundumstudio.socketio.demo.BinaryEventLauncher` - `/client/binary-event-index.html`
 
 