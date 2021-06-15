import netty.ProviderBootstrap;

/**
 * 启动主类
 *
 * @author chen
 * @date 2021/5/2
 **/
public class Provider {

	public static void main(String[] args) throws InterruptedException {
		ProviderBootstrap server = new ProviderBootstrap();
		server.start();
	}
}
