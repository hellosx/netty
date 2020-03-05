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
        System.out.println("收到登陆请求");

        LoginPacketResponse loginPacketResponse = new LoginPacketResponse();

        if ("zhangsan".equals(msg.getUserName()) && "123456".equals(msg.getPassword())) {
            System.out.println("登陆成功");
            loginPacketResponse.setSuccess(true);
        }
        else {
            System.out.println("登陆失败");
            loginPacketResponse.setSuccess(false);
        }

        ctx.channel().writeAndFlush(loginPacketResponse);
    }
}
