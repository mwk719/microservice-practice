package com.cxd.tool.util;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.*;
import java.util.Map.Entry;

/**
 * spring 代理jedis客户端功能与redis server通信, 依赖spring-data-redis-1.0.2.jar包.
 */
@Slf4j
@RequiredArgsConstructor
public class RedisSpringProxy {

	@NonNull
	RedisTemplate redisTemplate;

	@NonNull
	StringRedisTemplate stringRedisTemplate;

	public RedisTemplate getRedisTemplate() {
		return this.redisTemplate;
	}

	/**
	 * 将指定的value 与此映射中的指定key 关联, 保存到redis 中
	 */
	public void save(final String key, final Object value) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.set(serializeKey(key), serializeValue(value));
				return null;
			}
		});
	}

	/**
	 * 将指定的value 与此映射中的指定key 关联, 保存到redis 中, 并设置有效时间
	 *
	 * @param seconds key-value的有效时间(秒)
	 */
	public void saveEx(final String key, final long seconds, final Object value) {
		try {
			redisTemplate.execute((RedisCallback<Object>) connection -> {
				connection.setEx(serializeKey(key), seconds, serializeValue(value));
				return null;
			});
		} catch (Exception e) {
			log.error("redis保存key:{}错误,{}", key, e.getMessage());
		}

	}

	/**
	 * 返回指定key 所映射的值. 如果redis 中不包含该key 的映射关系，则返回 null
	 */
	public Object read(final String key) {
		return redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyBytes = serializeKey(key);
				if (connection.exists(keyBytes)) {
					byte[] valueBytes = connection.get(keyBytes);
					return deserializeValue(valueBytes);
				}
				return null;
			}
		});
	}

	/**
	 * 如果存在一个key 的映射关系，则将其从redis 中移除
	 *
	 * @return 被删除 key 的数量
	 */
	public Long delete(final String key) {
		return (Long) redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.del(serializeKey(key));
			}
		});
	}

	/**
	 * redis情况所有缓存
	 */
	public void flushAll() {
		redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) {
				connection.flushAll();
				return 0L;
			}
		});
	}

	/**
	 * <p>
	 * 将哈希表 key 中的域 field(map键) 的值设为 value
	 * </p>
	 * <p>
	 * 如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作
	 * </p>
	 * <p>
	 * 如果域 field 已经存在于哈希表中，旧值将被覆盖
	 * </p>
	 *
	 * @param key   redis哈希表 key
	 * @param field 与指定值关联的field(map键)
	 * @param value 与指定field(map键)关联的值
	 * @return 如果 field 是哈希表中的一个新建域，并且值设置成功, 返回 true, <br>
	 * 如果哈希表中域 field(map键) 已经存在且旧值已被新值覆盖，返回false , 操作失败返回null 。
	 */
	public Boolean hSet(final String key, final byte[] field, final byte[] value) {
		return (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.hSet(serializeKey(key), field, value);
			}
		});
	}

	/**
	 * 返回redis哈希表 key 中给定域 field(map键) 的值
	 *
	 * @param key   redis哈希表 key
	 * @param field 与指定值关联的field(map键)
	 * @return 返回指定 key 和指定 field(map键) 所映射的值,当给定 field(map键) 不存在或是给定 key
	 * 不存在时，返回 nil 。
	 */
	public Object hGet(final String key, final byte[] field) {
		return redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] value = connection.hGet(serializeKey(key), field);
				return deserializeValue(value);
			}

		});
	}

	/**
	 * redis 存储 map
	 *
	 * @param key       redis哈希表 key
	 * @param mapObject map对象
	 */
	public void hmSet(final String key, final Map<Object, Object> mapObject) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {

				Map<byte[], byte[]> mapByte = new HashMap<byte[], byte[]>(mapObject.size());

				for (final Entry<Object, Object> entry : mapObject.entrySet()) {
					byte[] mapKey = serializeValue(entry.getKey());
					byte[] mapValue = serializeValue(entry.getValue());
					mapByte.put(mapKey, mapValue);
				}

				connection.hMSet(serializeKey(key), mapByte);

				mapByte = null;// release

				return null;
			}

		});
	}

	/**
	 * 返回reids 哈希表 key 中, 一个或多个给定域的值
	 *
	 * @param key
	 *            redis哈希表 key
	 * @param field
	 *            与指定值关联的field(map键)
	 * @return 返回指定 key 和指定 field(map键) 所映射的值,当给定 field(map键) 不存在或是给定 key
	 *         不存在时，返回 nil 。
	 */
//	public List<Object> hmGet(final String key, final Object... field) {
//		return redisTemplate.execute(new RedisCallback<List<Object>>() {
//			@Override
//			public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
//
//				List<byte[]> redisRet = null;
//				List<Object> ret = new ArrayList<Object>();
//				if (field.length > 1) {
//					byte[][] fields = new byte[field.length][];
//					int i = 0;
//					for (Object object : field) {
//						fields[i] = serializeValue(object);
//						i++;
//					}
//					redisRet = connection.hMGet(serializeKey(key), fields);
//				}
//
//				else {
//					redisRet = connection.hMGet(serializeKey(key), serializeValue(field[0]));
//				}
//
//				if (null != redisRet) {
//					for (byte[] value : redisRet) {
//						if (value != null) {
//							ret.add(deserializeValue(value));
//						}
//					}
//				}
//
//				return ret.size() > 0 ? ret : null;
//			}
//		});
//	}

	/**
	 * 返回redis 哈希表 key 中,所有的域和值
	 *
	 * @param key redis哈希表 key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<Object, Object> hGetAll(final String key) {
		return (Map<Object, Object>) redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				Map<byte[], byte[]> mapByte = connection.hGetAll(serializeKey(key));
				Map<Object, Object> mapObject = new HashMap<Object, Object>(mapByte.size());

				for (final Entry<byte[], byte[]> entry : mapByte.entrySet()) {
					Object mapKey = deserializeValue(entry.getKey());
					Object mapValue = deserializeValue(entry.getValue());
					mapObject.put(mapKey, mapValue);
				}

				mapByte = null;// release

				return mapObject;
			}

		});
	}

	/**
	 * 以<code>List</code> 数据结构存储到redis 中, 允许添加重复值
	 *
	 * @param key    redis哈希表 key
	 * @param values 存储一个值或多个值
	 * @return 返回列表长度
	 */
	public Long lPush(final String key, final Object... values) {
		return (Long) redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				Long count = new Long(0);
				for (Object value : values) {
					byte[] keyByte = serializeKey(key);
					byte[] val = serializeValue(value);
					count = connection.lPush(keyByte, val);
				}
				return count;
			}

		});
	}

	/**
	 * 从redis 中取出 <code>List</code>结构数据, 参数中指定取值范围, 假如你有一个包含一百个元素的列表, 对该列表执行
	 * LRANGE list 0 10, 结果是一个包含11个元素的列表.如果指定0 -1那么返回全部元素.
	 *
	 * @param key   redis哈希表 key
	 * @param begin 起始位置
	 * @param end   结束位置
	 * @return List结果集或null
	 */
	public List<Object> lRange(final String key, final long begin, final long end) {
		return Collections.singletonList(redisTemplate.execute(new RedisCallback<List<Object>>() {

			@Override
			public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
				List<byte[]> retByteLst = connection.lRange(serializeKey(key), begin, end);
				if (null != retByteLst) {
					List<Object> retValLst = new ArrayList<Object>(retByteLst.size());
					for (byte[] retValByte : retByteLst) {
						Object val = deserializeValue(retValByte);
						retValLst.add(val);
					}
					return retValLst;
				}
				return null;
			}

		}));
	}

	/**
	 * 移除并返回列表 key 的头元素
	 *
	 * @param key redis哈希表 key
	 * @return value
	 */
	public Object lPop(final String key) {
		return redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] value = connection.lPop(serializeKey(key));
				return deserializeValue(value);
			}

		});
	}

	/**
	 * 移除并返回列表 key 的尾元素
	 *
	 * @param key redis哈希表 key
	 * @return value
	 */
	public Object rPop(final String key) {
		return redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] value = connection.rPop(serializeKey(key));
				return deserializeValue(value);
			}

		});
	}

	protected byte[] serializeKey(final String key) {
		return redisTemplate.getStringSerializer().serialize(key);
	}

	@SuppressWarnings("unchecked")
	protected byte[] serializeValue(final Object value) {
		RedisSerializer<Object> reidsSerializer = (RedisSerializer<Object>) redisTemplate.getValueSerializer();
		return reidsSerializer.serialize(value);
	}

	protected Object deserializeValue(final byte[] value) {
		return redisTemplate.getValueSerializer().deserialize(value);
	}

	/**
	 * 通配删除key
	 *
	 * @param pattern
	 */
	public void deletePattern(String pattern) {
		Set<String> keys = stringRedisTemplate.keys(pattern);
		// log.info("清空缓存keys.isEmpty()：" + keys.isEmpty() + ",pattern：" + pattern);
		if (!keys.isEmpty()) {
			stringRedisTemplate.delete(keys);
		}
	}

	/**
	 * 获取所有key
	 *
	 * @param pattern
	 */
	public Set<String> getAllKeys(String pattern) {
		Set<String> keys = stringRedisTemplate.keys(pattern);
		return keys;
	}

}