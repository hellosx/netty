package server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protocol.*;

/**
 * @author sunxin@yunrong.cn
 * @version V2.1
 * @since 2.1.0 2020/3/3 16:07
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.getINSTANCE().decode(byteBuf);

        LoginPacketResponse response = new LoginPacketResponse();

        if (packet instanceof LoginPacketRequest) {
            LoginPacketRequest packetRequest = (LoginPacketRequest) packet;
            if (packetRequest.getUserName().equals("zhangsan") && packetRequest.getPassword().equals("123456")) {
                System.out.println("登陆成功");
                response.setSuccess(true);
                ctx.channel().writeAndFlush(PacketCodeC.getINSTANCE().enCode(response));
            }

            else {
                System.out.println("登陆失败");
                response.setSuccess(false);
                ctx.channel().writeAndFlush(PacketCodeC.getINSTANCE().enCode(response));
            }
        }

        if (packet instanceof MessagePackRequest) {
            MessagePackRequest messagePackRequest = (MessagePackRequest) packet;

            System.out.println("服务端收到的消息是：" + messagePackRequest.getContent());
        }
    }
}
