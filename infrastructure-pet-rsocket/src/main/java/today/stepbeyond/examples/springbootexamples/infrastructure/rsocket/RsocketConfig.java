package today.stepbeyond.examples.springbootexamples.infrastructure.rsocket;

import io.rsocket.transport.netty.client.TcpClientTransport;
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;
import org.springframework.boot.rsocket.messaging.RSocketStrategiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.protobuf.ProtobufDecoder;
import org.springframework.http.codec.protobuf.ProtobufEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;

@Configuration
@PropertySource("classpath:/defaults.properties")
public class RsocketConfig {


    @Bean
    RSocketRequester rSocketRequester(RSocketRequester.Builder builder, RSocketProperties properties) {
        return builder.transport(TcpClientTransport.create(6565));
    }

    @Bean
    @Order(0)
    RSocketStrategiesCustomizer protobufStrategy() {
        return (strategy) -> {
            strategy.decoder(new ProtobufDecoder());
            strategy.encoder(new ProtobufEncoder());
        };
    }

}
