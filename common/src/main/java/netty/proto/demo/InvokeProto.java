package netty.proto.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 调用命令的
 *
 * @author chen
 * @date 2021/5/3
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class InvokeProto {
	private String serviceName;

	private String[] args;
}
