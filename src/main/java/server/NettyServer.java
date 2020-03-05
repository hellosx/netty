package server;

import decoder.Spliter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import protocol.PacketDecoder;
import protocol.PacketEncoder;
import server.handler.AuthHandler;
import server.handler.ConnectCountHandler;
import server.handler.LoginRequestHandler;
import server.handler.MessageRequestHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author sunxin@yunrong.cn
 * @version V2.1
 * @since 2.1.0 2020/2/26 17:38
 */
public class NettyServer {

    public static void main(String[] args) {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap()
                .group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {

            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ch.pipeline().addLast(new Spliter());
                ch.pipeline().addLast(new ConnectCountHandler());
                ch.pipeline().addLast(new PacketDecoder());
                ch.pipeline().addLast(new LoginRequestHandler());
                ch.pipeline().addLast(new AuthHandler());
                ch.pipeline().addLast(new MessageRequestHandler());
                ch.pipeline().addLast(new PacketEncoder());
            }
        });

        autoBind(serverBootstrap, 9090);

        bossGroup.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(ConnectCountHandler.atomicInteger);
            }
        }, 5, 1, TimeUnit.SECONDS);
    }


    public static void autoBind(ServerBootstrap serverBootstrap, int port) {

        ChannelFuture channelFuture = serverBootstrap.bind(port);

        channelFuture.addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("服务端启动成功，端口是" + port);
            }
            else {
                autoBind(serverBootstrap, port + 1);
            }
        });
    }

}
