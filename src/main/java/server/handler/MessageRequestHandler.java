package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.MessagePackRequest;

/**
 * @author sunxin@yunrong.cn
 * @version V2.1
 * @since 2.1.0 2020/3/4 15:49
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessagePackRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessagePackRequest msg) throws Exception {
        System.out.println("客户端发送的消息是:" + msg.getContent());
    }
}
