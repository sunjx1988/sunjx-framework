package sunjx.framework.proxy;

/**
 * Created by sunjx on 2018/1/11.
 */
public interface Proxy {

    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
