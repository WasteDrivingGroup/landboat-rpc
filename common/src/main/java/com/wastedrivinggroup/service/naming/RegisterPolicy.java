package com.wastedrivinggroup.service.naming;

/**
 * serviceName : ip:port
 *
 * @author chen
 * @date 2021-04-16
 **/
public interface RegisterPolicy {

    /**
     * 服务注册方法
     *
     * @param serviceName 服务名称
     */
    void registered(String serviceName);

    /**
     * 服务名是否已经注册
     *
     * @param serviceName 服务名称
     * @return registered -> true,unregistered -> false
     */
    boolean isRegistered(String serviceName);

    /**
     * 取消注册
     *
     * @param serviceName 服务名称
     */
    void disRegistered(String serviceName);

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
