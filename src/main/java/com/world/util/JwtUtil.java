package com.world.util;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTHeader;
import cn.hutool.jwt.JWTUtil;

import java.util.Date;

public class JwtUtil {
    static byte[] key = "V50-KFC".getBytes();
    public static String generateToken(Long userId) {
      return JWT.create()
                .setPayload("id", userId)
                .setExpiresAt( new Date(  System.currentTimeMillis() + 1000 * 600000 ))
                .setKey(key)
                .sign();
    }
    static public Long getUserIdFromToken(String token) {
        if(!JWTUtil.verify(token, key)){
            return null;
        };
        JWT jwt = JWTUtil.parseToken(token);
        jwt.getHeader(JWTHeader.TYPE);
        Object timestamp = jwt.getPayload("exp");
        if( timestamp == null ){
            return null;
        }
        Date currentTime = new Date();
        // 创建一个Date对象来表示提供的时间戳
        Date providedTime = new Date( Long.parseLong( timestamp.toString()) * 1000 );
        if (providedTime.before(currentTime)) {
            return null;
        }
        Object id = jwt.getPayload("id");
        if (id == null) {
            return null;
        }
        return Long.parseLong(id.toString());
    }
}
