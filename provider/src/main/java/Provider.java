import com.wastedrivinggroup.provider.netty.ProviderBootstrap;
import com.wastedrivinggroup.provider.service.ServiceHolder;
import com.wastedrivinggroup.provider.service.TestService;
import com.wastedrivinggroup.service.naming.ServiceRegisterChain;
import lombok.extern.slf4j.Slf4j;

/**
 * 启动主类
 *
 * @author chen
 * @date 2021/5/2
 **/
@Slf4j
public class Provider {

	public static void main(String[] args) throws InterruptedException {
		ServiceHolder.getInstance().loadService(TestService.class);
		ProviderBootstrap server = new ProviderBootstrap();
		server.start();
		ServiceRegisterChain.getInstance().registered("TEST");
	}
}
