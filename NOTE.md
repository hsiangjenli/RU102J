## Java Redis Clients

1. Redisson
2. Lettuce
3. Jedis

## Jedis Pool

1. JedisSentinelPool
2. JedisCluster

| Thread-safe | Deployment Type    | Connection           |
|-------------|--------------------|----------------------|
| No          | Single Redis       | Jedis                |
| No          | Redis Enterprise   | Jedis                |
| Yes         | Single Redis       | JedisPool            |
| Yes         | Redis Enterprise   | JedisPool            |
| Yes         | Redis Sentinel     | JedisSentinelPool    |
| Yes         | RedisCluster       | JedisCluster         |

## Simple Type Mappings

| Redis Type | Java Type            |
|------------|----------------------|
| string     | String               |
| list       | List\<String\>       |
| set        | Set\<String\>        |
| hash       | Map\<String, String\>|
| float      | Double               |
| integer    | Long                 |

## 在 Redis 操作 LREM 的方式

> LREM（List Remove）

1. SMEMBERS - 一次性取得所有成員，並進行處理
1. SSCAN - 分批取得成員，並進行處理

<!--  -->

- https://redis.io/docs/latest/commands/lrem/
- https://www.runoob.com/redis/lists-lrem.html