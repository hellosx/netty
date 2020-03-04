package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.LoginPacketRequest;
import protocol.LoginPacketResponse;
import protocol.Packet;
import util.LoginUtil;

/**
 * @author sunxin@yunrong.cn
 * @version V2.1
 * @since 2.1.0 2020/3/4 15:58
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginPacketResponse> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端开始登陆");

        Packet packet = new LoginPacketRequest();
        ((LoginPacketRequest) packet).setUserId(1);
        ((LoginPacketRequest) packet).setUserName("zhangsan");
        ((LoginPacketRequest) packet).setPassword("123456");

        ctx.channel().writeAndFlush(packet);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginPacketResponse msg) throws Exception {
        if (msg.getSuccess()) {
            LoginUtil.markLogin(ctx.channel());
            System.out.println("客户端登陆成功");
        } else {
            System.out.println("客户端登陆失败");
        }
    }
}
