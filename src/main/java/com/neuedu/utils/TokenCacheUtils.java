package com.neuedu.utils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class TokenCacheUtils {

    //    声明一个静态的内存块,guava里面的本地缓存
    private static LoadingCache<String, String> localcache =
            //构建本地缓存，调用链的方式 ,1000是设置缓存的初始化容量，maximumSize是设置缓存最大容量，当超过了最大容量，guava将使用LRU算法（最少使用算法），来移除缓存项
            //expireAfterAccess(12,TimeUnit.HOURS)设置缓存有效期为12个小时
            CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(10000).expireAfterAccess(12, TimeUnit.HOURS)
                    //build里面要实现一个匿名抽象类
                    .build(new CacheLoader<String, String>() {
//                   这个方法是默认的数据加载实现,get的时候，如果key没有对应的值，就调用这个方法进行加载

                        @Override
                        public String load(String s) throws Exception {
//                        为什么要把return的null值写成字符串，因为到时候用null去.equal的时候，会报空指针异常
                            return "null";
                        }
                    });

    /*
     * 添加本地缓存
     * */
    public static void setKey(String key, String value) {
        localcache.put(key, value);
    }

    /*
     * 得到本地缓存
     * */
    public static String getKey(String key) {
        String value = null;
        try {
            value= localcache.get(key);
            if ("null".equals(value)) {
                return  null;
            }
            return value;
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


}
