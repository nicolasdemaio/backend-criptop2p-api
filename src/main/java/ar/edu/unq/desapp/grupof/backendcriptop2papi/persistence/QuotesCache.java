package ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoQuotation;
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
    Cache<CryptoCurrency, CryptoQuotation> cache;

    public QuotesCache() {
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);

        cacheManager.init();

        cache =
                cacheManager.createCache(
                        "quotesCache",
                        CacheConfigurationBuilder
                                .newCacheConfigurationBuilder(CryptoCurrency.class, CryptoQuotation.class, ResourcePoolsBuilder.heap(200))
                                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(10)))
                                .build()
                );

        cacheManager.close();
    }

    public void put(CryptoCurrency aKey, CryptoQuotation aValue) {
        cacheManager.init();
        cache.put(aKey, aValue);
        cacheManager.close();
    }

    public Object get(CryptoCurrency aKey) {
        cacheManager.init();
        Object retrievedObject = cache.get(aKey);
        if (retrievedObject == null) throw new RuntimeException("Invalid object key: " + aKey);
        cacheManager.close();
        return retrievedObject;
    }

}
