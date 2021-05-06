package netty.server.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author chen
 * @date 2021/4/16
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ServerConfig {

    /**
     * 主机地址
     */
    private String host = "localhost";

    /**
     * 服务端口号
     */
    private Integer port = 8888;

    /**
     * IO线程数目
     */
    private Integer workerCnt;


    /**
     * 心跳间隔,单位ms
     */
    private Long hbInterval = 3000L;
}
