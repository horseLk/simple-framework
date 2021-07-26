package org.horse.simple.rpc.center.operator;

import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.horse.simple.common.util.LogUtils;
import org.horse.simple.rpc.center.cache.ServiceMsgCache;
import org.horse.simple.rpc.center.entity.RpcServiceMsg;

/**
 * 操作注册服务的实现类
 *
 * @author horse
 * @date 2021/7/26
 */
public class OperateCacheImpl implements OperateCache {
    /**
     * LOG
     */
    private static final Logger LOG = LogManager.getLogger(OperateCacheImpl.class);

    @Override
    public RpcServiceMsg get(RpcServiceMsg rpcServiceMsg) {
        LogUtils.info(LOG, "received rpc request to get service message named {}", rpcServiceMsg.getInterfaceName());
        return ServiceMsgCache.getService(rpcServiceMsg.getInterfaceName());
    }

    @Override
    public void put(RpcServiceMsg rpcServiceMsg) {
        LogUtils.info(LOG, "received rpc request to put service message {}", JSON.toJSONString(rpcServiceMsg));
        ServiceMsgCache.putService(rpcServiceMsg);
    }

    @Override
    public void delete(RpcServiceMsg rpcServiceMsg) {
        LogUtils.info(LOG, "received rpc request to delete service message named {}", rpcServiceMsg.getInterfaceName());
        ServiceMsgCache.deleteService(rpcServiceMsg.getInterfaceName());
    }
}
