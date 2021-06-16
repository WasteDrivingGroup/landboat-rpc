package com.wastedrivinggroup.consumer.netty.proto.exception;

/**
 * @author chen
 * @date 2021/5/4
 **/
public class BadProtoFormatException extends RuntimeException {
    public BadProtoFormatException(String message) {
        super(message);
    }
}
