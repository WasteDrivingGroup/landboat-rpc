import netty.ServerBootstrap;

/**
 * 启动主类
 *
 * @author chen
 * @date 2021/5/2
 **/
public class Main {


    public static void main(String[] args) throws InterruptedException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.start();
    }
}
