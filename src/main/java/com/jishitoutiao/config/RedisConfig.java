package com.jishitoutiao.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {
    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private Integer port;

    @Value("${redis.dbIndex}")
    private Integer dbIndex;

    @Value("${redis.password}")
    private String password;

    @Value("${redis.pool.minIdle}")
    private Integer minIdle;

    @Value("${redis.pool.maxIdle}")
    private Integer maxIdle;

    @Value("${redis.pool.maxTotal}")
    private Integer maxTotal;

    @Value("${redis.pool.lifo}")
    private boolean lifo;

    @Value("${redis.pool.maxWaitMillis}")
    private Integer maxWaitMillis;

    @Value("${redis.pool.minEvictableIdleTimeMillis}")
    private Integer minEvictableIdleTimeMillis;

    @Value("${reids.pool.blockWhenExhausted}")
    private boolean blockWhenExhausted;

    @Value("${redis.pool.numTestsPerEvictionRun}")
    private Integer numTestsPerEvictionRun;

    @Value("${redis.pool.timeBetweenEvictionRunsMillis}")
    private long timeBetweenEvictionRunsMillis;

    @Value("${redis.pool.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${redis.pool.testOnReturn}")
    private boolean testOnReturn;

    @Value("${redis.pool.testWhileIdle}")
    private boolean testWhileIdle;

    /**
     * JedisPoolConfig 连接池
     * @return
     */
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最小空闲数
        jedisPoolConfig.setMinIdle(minIdle);
        // 最大空闲数
        jedisPoolConfig.setMaxIdle(maxIdle);
        // 连接池的最大数据库连接数
        jedisPoolConfig.setMaxTotal(maxTotal);
        // 是否启用后进先出, 默认true
        jedisPoolConfig.setLifo(lifo);
        // 最大建立连接等待时间
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        // 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
        jedisPoolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        jedisPoolConfig.setBlockWhenExhausted(blockWhenExhausted);
        // 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        jedisPoolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        // 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        // 是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        // 返回连接时检测有效性
        jedisPoolConfig.setTestOnReturn(testOnReturn);
        // 在空闲时检查有效性, 默认false
        jedisPoolConfig.setTestWhileIdle(testWhileIdle);
        return jedisPoolConfig;
    }

    /**
     * RedisPassword对象
     * @return
     */
    @Bean
    public RedisPassword redisPassword() {
        RedisPassword redisPassword = RedisPassword.of(password);

        return redisPassword;
    }

    /**
     * redis单机配置对象
     * @return
     */
    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
        redisStandaloneConfiguration.setPassword(redisPassword());
        redisStandaloneConfiguration.setDatabase(dbIndex);
        return redisStandaloneConfiguration;
    }
    /**
     * 单机版配置
     * @param @param jedisPoolConfig
     * @param @return
     * @return JedisConnectionFactory
     */
    @Bean
    public JedisConnectionFactory JedisConnectionFactory(){
        JedisConnectionFactory JedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration());
        //连接池
        JedisConnectionFactory.setPoolConfig(jedisPoolConfig());

        return JedisConnectionFactory;
    }

    /**
     * 实例化 RedisTemplate 对象
     *
     * @return
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate() {

        RedisTemplate<String, String> redisTemplate = new StringRedisTemplate();
        // 配置key序列化类
        redisTemplate.setStringSerializer(new StringRedisSerializer());
        // 配置value序列化类
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        // 配置hash key序列化类
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // 配置hashValueSerializer
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer(Object.class));
        // 开启事务
        redisTemplate.setEnableTransactionSupport(true);
        // 注入连接工厂
        redisTemplate.setConnectionFactory(JedisConnectionFactory());

        return redisTemplate;
    }
}