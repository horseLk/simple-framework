package org.horse.simple.rpc.center.cache;

import org.horse.simple.rpc.center.entity.RpcServiceMsg;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注册中心缓存， 用来保存已经注册成功的服务
 *
 * @author horse
 * @date 2021/7/26
 */
public class ServiceMsgCache {
    /**
     * 已经注册的服务缓存
     */
    private static final Map<String, RpcServiceMsg> RPC_SERVICE_CACHE = new ConcurrentHashMap<>();

    private ServiceMsgCache(){
    }

    /**
     * 获取服务
     *
     * @param interfaceName interfaceName
     * @return RpcServiceMsg
     */
    public static RpcServiceMsg getService(String interfaceName) {
        return RPC_SERVICE_CACHE.get(interfaceName);
    }

    /**
     * 新增服务
     *
     * @param rpcServiceMsg RpcServiceMsg
     */
    public static void putService(RpcServiceMsg rpcServiceMsg) {
        RPC_SERVICE_CACHE.put(rpcServiceMsg.getInterfaceName(), rpcServiceMsg);
    }

    /**
     * 删除服务
     *
     * @param interfaceName interfaceName
     */
    public static void deleteService(String interfaceName) {
        RPC_SERVICE_CACHE.remove(interfaceName);
    }
}
