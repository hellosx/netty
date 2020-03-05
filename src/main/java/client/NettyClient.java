package client;

import client.handler.LoginResponseHandler;
import decoder.Spliter;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import protocol.MessagePackRequest;
import protocol.PacketCodeC;
import protocol.PacketDecoder;
import protocol.PacketEncoder;
import util.LoginUtil;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author by sunxin(sunxinphycho@gmail.com)
 * @version on 2019/10/10.
 */
public class NettyClient {

    public static final int MAX_RETRY = 10;

    public static void main(String[] args) {

        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap()
                .group(workGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new Spliter());
//                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4));
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });


        doClient(bootstrap, MAX_RETRY);

    }


    private static void doClient(Bootstrap bootstrap, int maxRetry) {
        bootstrap.connect("127.0.0.1", 9090).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功");

                Channel channel = ((ChannelFuture)future).channel();

                sendMsg(channel);

            } else if (maxRetry == 0) {
                System.out.println("重试次数已用完");
            } else {
                int delay = MAX_RETRY - maxRetry;
                System.out.println("连接失败，这是第" + delay + "次重连");

                delay = 1 << delay;
                System.out.println("重连需要时间：" + delay + "秒");

                bootstrap.config()
                        .group().
                        schedule(() -> doClient(bootstrap, maxRetry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }

    private static void sendMsg(Channel channel) {
        new Thread(() -> {
            while (!Thread.interrupted()) {

//                if (LoginUtil.hasLogin(channel)) {
                    System.out.println("请输入消息发送");
                    Scanner scanner = new Scanner(System.in);

                    String content = scanner.nextLine();

                    MessagePackRequest request = new MessagePackRequest();
                    request.setContent(content);

                    ByteBuf byteBuf = PacketCodeC.getINSTANCE().enCode(request);
                    channel.writeAndFlush(byteBuf);
//                }

            }
        }).start();
    }

}
