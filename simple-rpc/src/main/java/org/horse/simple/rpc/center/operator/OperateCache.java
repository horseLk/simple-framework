package org.horse.simple.rpc.center.operator;

import org.horse.simple.rpc.center.entity.RpcServiceMsg;

/**
 * 操作服务信息的接口
 *
 * @author horse
 * @date 2021/7/26
 */
public interface OperateCache {
    /**
     * 获取注册的服务信息
     *
     * @param rpcServiceMsg RpcServiceMsg
     * @return RpcServiceMsg
     */
    RpcServiceMsg get(RpcServiceMsg rpcServiceMsg);

    /**
     * 注册服务信息
     *
     * @param rpcServiceMsg RpcServiceMsg
     */
    void put(RpcServiceMsg rpcServiceMsg);

    /**
     * 删除服务信息
     *
     * @param rpcServiceMsg RpcServiceMsg
     */
    void delete(RpcServiceMsg rpcServiceMsg);
}
