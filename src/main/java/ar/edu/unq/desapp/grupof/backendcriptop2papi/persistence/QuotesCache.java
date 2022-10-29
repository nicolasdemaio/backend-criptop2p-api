package ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Expirations;

import java.time.Duration;

public class QuotesCache {

    CacheManager cacheManager;
    Cache<Long, String> cache;

    public QuotesCache() {
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);

        cacheManager.init();

        cache =
                cacheManager.createCache(
                        "quotesCache",
                        CacheConfigurationBuilder
                                .newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(200))
                                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(10)))
                                .build()
                );

        cacheManager.close();
    }

    public void put(Object aKey, Object aValue) {
        cacheManager.init();
        cache.put(aKey, aValue);
        cacheManager.close();
    }

    public Object get(Object aKey) {
        cacheManager.init();
        Object retrievedObject = cache.get(aKey);
        if (retrievedObject == null) throw new RuntimeException("Invalid object key: " + aKey);
        return retrievedObject;
        cacheManager.close();
    }

}
