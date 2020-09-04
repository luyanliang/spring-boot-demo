package com.fhmou;

import com.fhmou.model.User;
import com.fhmou.service.UserService;
import com.fhmou.utils.CacheUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * functional describe:
 *
 * @author luyanliang [765673481@qq.com]
 * @version 1.0 2017/9/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserService userService;

    @Test
    public void test() throws Exception {
        // 保存字符串
        stringRedisTemplate.opsForValue().set("aaa", "111");
        Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));
    }

    @Test
    public void testEntity() throws Exception {
        // 保存对象
        User user = new User(1, "王伟");
        redisTemplate.opsForValue().set(String.valueOf(user.getId()), user);

        user = new User(2, "蝙蝠侠");
        redisTemplate.opsForValue().set(String.valueOf(user.getId()), user);

        user = new User(3, "蜘蛛侠");
        redisTemplate.opsForValue().set(String.valueOf(user.getId()), user);

//        Assert.assertEquals("王伟", redisTemplate.opsForValue().get("1").getUserName());
//        Assert.assertEquals("蝙蝠侠", redisTemplate.opsForValue().get("2").getUserName());
//        Assert.assertEquals("蜘蛛侠", redisTemplate.opsForValue().get("3").getUserName());

    }

    @Test
    public void testGetUserById() {
        User user = userService.getById();
        Assert.assertNotNull(user);
        userService.getById();
    }

    @Test
    public void testUtils() {
        CacheUtils.set("aa", "name", 3333333);
        String name = CacheUtils.getStr("aa");
        Assert.assertEquals("name", name);

        User user = new User();
        user.setUserName("asdf");
        CacheUtils.set("obj", user, 2323423);
        User search = CacheUtils.get("obj", User.class);
        Assert.assertNotNull(search);
    }
}
