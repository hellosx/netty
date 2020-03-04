package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.LoginPacketRequest;
import protocol.LoginPacketResponse;

/**
 * @author sunxin@yunrong.cn
 * @version V2.1
 * @since 2.1.0 2020/3/4 15:44
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginPacketRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginPacketRequest msg) throws Exception {

        LoginPacketResponse loginPacketResponse = new LoginPacketResponse();

        if ("zhangsan".equals(msg.getUserName()) && "123456".equals(msg.getPassword())) {
            loginPacketResponse.setSuccess(true);
        }
        else {
            loginPacketResponse.setSuccess(false);
        }

        ctx.channel().writeAndFlush(loginPacketResponse);

    }
}
