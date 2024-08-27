package edu.cust;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{
    // @Autowired
    // private AuthHandshakeInterceptor authHandshakeInterceptor;

    // @Autowired
    // private MyHandshakeHandler myHandshakeHandler;

    // @Autowired
    // private MyChannelInterceptor myChannelInterceptor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp-websocket").withSockJS();

        //registry.addEndpoint("/chat-websocket")
                // .addInterceptors(authHandshakeInterceptor)
                // .setHandshakeHandler(myHandshakeHandler)
                //.withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //客户端需要把消息发送到/message/xxx地址
    	//客户端向服务端发消息时使用
        registry.setApplicationDestinationPrefixes("/message");
        //服务端广播消息的路径前缀，客户端需要相应订阅/topic/yyy这个地址的消息
        //STOMP是服务订阅模式，广播的订阅地址也可用于点对点消息，只需要地址里带有唯一标识就行
        registry.enableSimpleBroker("/topic");
        //给指定用户发送消息的路径前缀，默认值是/user/
        //因此发送给指定用户时，前缀得是/user/topic
        registry.setUserDestinationPrefix("/user/");
    }

    // @Override
    // public void configureClientInboundChannel(ChannelRegistration registration) {
    //     registration.interceptors(myChannelInterceptor);
    // }

}
