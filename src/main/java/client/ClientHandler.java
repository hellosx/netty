package client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protocol.LoginPacketRequest;
import protocol.LoginPacketResponse;
import protocol.Packet;
import protocol.PacketCodeC;
import util.LoginUtil;

/**
 * @author sunxin@yunrong.cn
 * @version V2.1
 * @since 2.1.0 2020/3/3 16:06
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端开始登陆");

        Packet packet = new LoginPacketRequest();
        ((LoginPacketRequest) packet).setUserId(1);
        ((LoginPacketRequest) packet).setUserName("zhangsan");
        ((LoginPacketRequest) packet).setPassword("123456");

        ByteBuf byteBuf = PacketCodeC.getINSTANCE().enCode(packet);

        ctx.channel().writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.getINSTANCE().decode(byteBuf);

        if (packet instanceof LoginPacketResponse) {
            LoginPacketResponse loginPacketResponse = (LoginPacketResponse) packet;

            if (loginPacketResponse.getSuccess()) {
                LoginUtil.markLogin(ctx.channel());
                System.out.println("客户端登陆成功");
            } else {
                System.out.println("客户端登陆失败");
            }
        }

    }
}
