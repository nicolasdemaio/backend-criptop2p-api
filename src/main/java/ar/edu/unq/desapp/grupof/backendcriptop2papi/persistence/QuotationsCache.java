package ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoQuotation;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.hibernate.FetchNotFoundException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class QuotationsCache {

    CacheManager cacheManager;
    Cache<CryptoCurrency, CryptoQuotation> cache;

    public QuotationsCache() {
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);

        cache =
                cacheManager.createCache(
                        "quotesCache",
                        CacheConfigurationBuilder
                                .newCacheConfigurationBuilder(CryptoCurrency.class, CryptoQuotation.class, ResourcePoolsBuilder.heap(200))
                                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(10)))
                                .build()
                );
    }

    public void put(CryptoCurrency aKey, CryptoQuotation aValue) {
        cache.put(aKey, aValue);
    }

    public CryptoQuotation get(CryptoCurrency aKey) {
        CryptoQuotation retrievedObject = cache.get(aKey);
        if (retrievedObject == null) throw new FetchNotFoundException("CryptoQuotation", aKey);
        return retrievedObject;
    }

    public List<CryptoQuotation> getAll() {
        Set<CryptoCurrency> cryptoCurrencies = Arrays.stream(CryptoCurrency.values()).collect(Collectors.toSet());
        Map<CryptoCurrency, CryptoQuotation> quotationsMap = cache.getAll(cryptoCurrencies);
        return quotationsMap.values().stream().toList();
    }
}
