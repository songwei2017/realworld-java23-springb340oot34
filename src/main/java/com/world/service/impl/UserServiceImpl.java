package com.world.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.log.StaticLog;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.world.component.Thumb;
import com.world.constants.CacheKey;
import com.world.entity.User;
import com.world.exception.CustomException;
import com.world.mapper.UserMapper;
import com.world.request.user.CreateRequest;
import com.world.request.user.LoginRequest;
import com.world.request.user.UpdateRequest;
import com.world.response.user.UserResponse;
import com.world.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.world.util.JwtUtil;
import com.world.util.RedisUtil;
import com.world.vo.AuthUser;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private static final String SECRET_PASSWORD = "ergou";
    private final RedisUtil redisUtil;
    private final Thumb thumb ;

    public String register(CreateRequest createRequest) {

        //日志
        StaticLog.info("注册", "INFO");

        User user = new User();
        user.setEmail(createRequest.getUser().getEmail());
        user.setPassword(encodePassword(createRequest.getUser().getPassword()));
        user.setUsername(createRequest.getUser().getUsername());
        user.setBio("这人是懒狗");
        user.setImageUrl(thumb.getOne());
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        if( getUser(User::getUsername,user.getUsername()) != null || getUser(User::getEmail,user.getEmail()) != null ) {
            throw new CustomException("username 或 email 已被占用");
        }
        save(user);
        return JwtUtil.generateToken(user.getId());
    }


    public UserResponse getCurrUser() {
       User user = getById(getUserId());
        if(user == null) {
            throw new CustomException("用户不存在");
        }
        String token = JwtUtil.generateToken(user.getId());
        return UserResponse.ofUser(user, token);
    }


    public UserResponse updateUser( UpdateRequest request) {
        User user = getById(getUserId());
        if(user == null) {
            throw new CustomException("用户不存在");
        }

        if (StrUtil.isNotEmpty(request.user().email())){
            User checkUser = getUser(User::getEmail,request.user().email());
            if(checkUser != null && !checkUser.getId().equals(user.getId()))  {
                throw new CustomException("邮箱已被占用");
            }
            user.setEmail(request.user().email());
        }

        if (StrUtil.isNotEmpty(request.user().username())){
            User checkUser = getUser(User::getUsername,request.user().username());
            if(checkUser != null && !checkUser.getId().equals(user.getId()))  {
                throw new CustomException("用户名已被占用");
            }
            user.setUsername(request.user().username());
        }

        if ( StrUtil.isNotEmpty(request.user().bio())) {
            user.setBio(request.user().bio());
        }
        if ( StrUtil.isNotEmpty(request.user().image())) {
            user.setImageUrl(request.user().image());
        }

        if ( StrUtil.isNotEmpty(request.user().password())) {
            user.setPassword( encodePassword(request.user().password()) );
        }
        user.setUpdatedAt(new Date());
        updateById(user);

        //删除缓存
        redisUtil.delete(CacheKey.USERNAME+user.getUsername());


        String token = JwtUtil.generateToken(user.getId());
        return UserResponse.ofUser(user, token);
    }

    public UserResponse login(LoginRequest loginRequest) {
        User user = getUser(User::getEmail,loginRequest.user().email());
        if(user == null || !checkPassword(loginRequest.user().password(),user.getPassword())) {
            throw new CustomException("账户不存在或密码错误");
        }
        String token = JwtUtil.generateToken(user.getId());
        return UserResponse.ofUser(user,token);
    }

    public User getUser(SFunction<User,?> col , String val) {
        Wrapper<User> wrapper = Wrappers.<User>lambdaQuery().eq(col, val);
        return getOne(wrapper);
    }

    public Long getUserId(){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            AuthUser  user = (AuthUser) authentication.getPrincipal();
            return Long.parseLong(user.getUsername());
        }catch (Exception e){
            return 0L;
        }
    }

    public String encodePassword(String password)
    {
        return  new Digester(DigestAlgorithm.MD5).digestHex(password + SECRET_PASSWORD);
   }

   public boolean checkPassword(String password, String encodedPassword){
        return encodePassword(password).equals(encodedPassword);
   }

    /**
     * 用户名，用缓存
     */
    public User getUserByUserName(String val) {
        String cacheKey = CacheKey.USERNAME + val;
        Object user = getUserOrNull(cacheKey);
        if(user != null) {
            if (user.equals("-1")) {
                return null;
            }else if (user instanceof User) {
                return (User) user;
            }
        }
        Wrapper<User> wrapper = Wrappers.<User>lambdaQuery().eq(User::getUsername, val);
        User u = getOne(wrapper);
        setCache(cacheKey, u);
        return u;
    }


    public User getUserById(Long userId) {
        String cacheKey = CacheKey.USERID + userId;
        Object user = getUserOrNull(cacheKey);
        if(user != null) {
            if (user.equals("-1")) {
                return null;
            }else if (user instanceof User) {
                return (User) user;
            }
        }

        User u = getById(userId);
        setCache(cacheKey, u);
        return u;
    }

    public Object getUserOrNull(String cacheKey){
        Object o = redisUtil.get(cacheKey);
        if (ObjectUtil.isNull(o)) {
            return null;
        }
        if (o.toString().equals("-1")){
            return "-1";
        }else{
            return JSONObject.parseObject(o.toString(),User.class);
        }
    }
    public void setCache(String cacheKey ,User u)
    {
        String cache;
        if (u == null) {
            cache = "-1";
        }else{
            cache = JSONObject.toJSONString(u);
        }
        redisUtil.set(cacheKey,cache,CacheKey.USERNAME_EXP);
    }
}
