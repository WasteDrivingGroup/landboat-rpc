import com.wastedrivinggroup.consumer.rpc.InvokeProxy;
import com.wastedrivinggroup.consumer.service.TestService;
import com.wastedrivinggroup.netty.ConsumerBootstrap;
import com.wastedrivinggroup.service.naming.consul.ConsulServiceDiscovery;

/**
 * @author chen
 * @date 2021/5/2
 **/
public class Consumer {

	public static final String serviceName = "TestIncr";

	public static void main(String[] args) throws InterruptedException {
		// 启动 Netty 客户端
		ConsumerBootstrap clent = new ConsumerBootstrap();
		clent.start();

		// 服务发现
		new ConsulServiceDiscovery().discovery(serviceName);

		final TestService proxy = InvokeProxy.createProxy(TestService.class);
		final Integer hello = proxy.incr("Hello");
		System.out.println(hello);
	}
}
