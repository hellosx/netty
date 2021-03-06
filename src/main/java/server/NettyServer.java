package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

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
                ch.pipeline().addLast(new ServerHandler());
            }
        });

        autoBind(serverBootstrap, 9090);
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
