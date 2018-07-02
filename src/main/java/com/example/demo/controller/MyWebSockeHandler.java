package com.example.demo.controller;

import com.example.demo.config.NettyConfig;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 接受/处理/响应客户端websocke请求的核心业务处理类
 */
public class MyWebSockeHandler extends SimpleChannelInboundHandler<Object> {
    private static final Logger logger = LoggerFactory.getLogger(MyWebSockeHandler.class);
    private WebSocketServerHandshaker webSocketServerHandshaker;
    private static final String WEB_SOCKET_URL = "ws://localhost:8888/webSocket";

    //客户端与服务端创建链接的时候调用
    @Override
    public void channelActive(ChannelHandlerContext context) throws Exception {

        logger.info("客户端与服务端第一次连接"+context.toString());
    }

    //客户端与服务端断开连接的时候调用
    @Override
    public void channelInactive(ChannelHandlerContext context) throws Exception {
        NettyConfig.remove(context.channel());

        System.out.println("客户端与服务端连接断开");
    }

    //服务端接收客户端发送过来的数据结束之后调用
    @Override
    public void channelReadComplete(ChannelHandlerContext context) throws Exception {
        //TODO 从队列中读取缓存数据
        readCahe();

        context.flush();
    }

    //工程出现异常的时候调用
    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable throwable) throws Exception {
        throwable.printStackTrace();
        context.close();
    }

    //服务端处理客户端websocke请求的核心方法
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        //处理客户端向服务端发起的http握手请求
        if (o instanceof FullHttpRequest) {
            handHttpRequest(channelHandlerContext, (FullHttpRequest) o);
        } else if (o instanceof WebSocketFrame) {//处理websocket链接业务
            handWebSocketFrame(channelHandlerContext, (WebSocketFrame) o);
        }
    }

    /**
     * 处理客户端与服务端之间的websocket业务
     *
     * @param context
     * @param webSocketFrame
     */
    private void handWebSocketFrame(ChannelHandlerContext context, WebSocketFrame webSocketFrame) {
        if (webSocketFrame instanceof CloseWebSocketFrame) {//判断是否是关闭websocket的指令
            webSocketServerHandshaker.close(context.channel(), (CloseWebSocketFrame) webSocketFrame.retain());
        }
        if (webSocketFrame instanceof PingWebSocketFrame) {//判断是否是ping消息
            context.channel().write(new PongWebSocketFrame(webSocketFrame.content().retain()));
            return;
        }

        if (!(webSocketFrame instanceof TextWebSocketFrame)) {//判断是否是二进制消息
            System.out.println("不支持二进制消息");
            throw new RuntimeException(this.getClass().getName());
        }
        //返回应答消息
        //获取客户端向服务端发送的消息
        String request = ((TextWebSocketFrame) webSocketFrame).text();
        System.out.println("服务端收到客户端的消息：" + request);
        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(context.channel().id() + ":" + request);
        //服务端向每个连接上来的客户端发送消息
        if (null != NettyConfig.groupMap.get(request)) {
            NettyConfig.groupMap.get(request).add(context.channel());
        }

    }


    /**
     * 处理客户端向服务端发起http握手请求业务
     *
     * @param context
     * @param fullHttpRequest
     */
    private void handHttpRequest(ChannelHandlerContext context, FullHttpRequest fullHttpRequest) {
        //这个是处理http
        if (!fullHttpRequest.getDecoderResult().isSuccess() || !("websocket".equals(fullHttpRequest.headers().get("Upgrade")))) {//判断是否http握手请求
            sendHttpResponse(context, fullHttpRequest, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        WebSocketServerHandshakerFactory webSocketServerHandshakerFactory = new WebSocketServerHandshakerFactory(WEB_SOCKET_URL, null, false);
        webSocketServerHandshaker = webSocketServerHandshakerFactory.newHandshaker(fullHttpRequest);
        if (webSocketServerHandshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(context.channel());
        } else {
            webSocketServerHandshaker.handshake(context.channel(), fullHttpRequest);
        }
    }

    /**
     * 服务端想客户端发送响应消息
     *
     * @param context
     * @param fullHttpRequest
     * @param defaultFullHttpResponse
     */
    private void sendHttpResponse(ChannelHandlerContext context, FullHttpRequest fullHttpRequest, DefaultFullHttpResponse defaultFullHttpResponse) {
        if (defaultFullHttpResponse.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(defaultFullHttpResponse.getStatus().toString(), CharsetUtil.UTF_8);
            defaultFullHttpResponse.content().writeBytes(buf);
            buf.release();
        }
        //服务端向客户端发送数据
        ChannelFuture future = context.channel().writeAndFlush(defaultFullHttpResponse);
        if (defaultFullHttpResponse.getStatus().code() != 200) {
            future.addListener(ChannelFutureListener.CLOSE);
        }

    }

    private void divideGroup() {

    }

    private void readCahe() {

    }


}
