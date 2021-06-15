import netty.ConsumerBootstrap;

/**
 * @author chen
 * @date 2021/5/2
 **/
public class Consumer {
	public static void main(String[] args) throws InterruptedException {
		ConsumerBootstrap clent = new ConsumerBootstrap();
		clent.start();
	}
}
