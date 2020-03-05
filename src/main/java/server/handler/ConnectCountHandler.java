package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sunxin@yunrong.cn
 * @version V2.1
 * @since 2.1.0 2020/3/5 14:23
 */
public class ConnectCountHandler extends ChannelInboundHandlerAdapter {

    public static AtomicInteger atomicInteger = new AtomicInteger();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        atomicInteger.incrementAndGet();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        atomicInteger.decrementAndGet();
    }
}
