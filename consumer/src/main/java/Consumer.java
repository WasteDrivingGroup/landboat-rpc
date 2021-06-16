import com.wastedrivinggroup.consumer.netty.ConsumerBootstrap;
import com.wastedrivinggroup.consumer.rpc.InvokeProxyHandler;
import com.wastedrivinggroup.consumer.service.TestService;

/**
 * @author chen
 * @date 2021/5/2
 **/
public class Consumer {
	public static void main(String[] args) throws InterruptedException {
		ConsumerBootstrap clent = new ConsumerBootstrap();
		clent.start();
		final TestService proxy = InvokeProxyHandler.createProxy(TestService.class);
		proxy.incr("Hello");
	}
}
