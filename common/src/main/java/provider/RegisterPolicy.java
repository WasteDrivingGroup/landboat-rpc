package provider;

/**
 * @author chen
 * @date 2021-04-16
 **/
@FunctionalInterface
public interface RegisterPolicy {

    /**
     * 服务注册方法
     *
     * @param serviceName 服务名称
     */
    void registered(String serviceName);

    /**
     * 可以注册为多个方法服务
     *
     * @param serviceNames 服务名称列表
     */
    default void registered(String[] serviceNames) {
        for (String serviceName : serviceNames) {
            registered(serviceName);
        }
    }
}
