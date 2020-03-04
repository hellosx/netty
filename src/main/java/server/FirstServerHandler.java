package server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

/**
 * @author sunxin@yunrong.cn
 * @version V2.1
 * @since 2.1.0 2020/2/28 14:25
 */
public class FirstServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 收到数据
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println(byteBuf.toString(Charset.forName("UTF-8")));

        // 回复数据
        ByteBuf byteBuf1 = ctx.alloc().buffer();

        byteBuf1.writeBytes("服务端写出数据".getBytes(Charset.forName("UTF-8")));

        ctx.channel().writeAndFlush(byteBuf1);
    }
}
