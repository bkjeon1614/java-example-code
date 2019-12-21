package com.example.bkjeon.base.utils.auth;

import com.example.bkjeon.base.exception.auth.AuthException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import javax.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class JwtUtil {

    private JwtUtil() {
        throw new IllegalStateException("Utility class");
    }

    private static final String SECRET_KEY = "olive";

    public static String createJWT(Object obj) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
        JwtBuilder builder = Jwts.builder().signWith(signatureAlgorithm, SECRET_KEY);

        try {
            Class clazz = obj.getClass();
            BeanInfo info = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] props = info.getPropertyDescriptors();

            for (PropertyDescriptor pd : props) {
                if (Objects.nonNull(pd.getReadMethod())) {
                    Object value = pd.getReadMethod().invoke(obj);
                    if (Objects.isNull(value)) continue;
                    builder.claim(pd.getDisplayName(), value);
                }
            }
        } catch (Exception e) {
            // throw new AuthException("An error occurs to create a token", 1002);
        }

        return builder.compact();
    }

    public static <T> T parseToken(String token, Class<T> clazz) {
        T object = null;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                    .parseClaimsJws(token).getBody();
            object = clazz.getDeclaredConstructor().newInstance();
            BeanInfo info = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] props = info.getPropertyDescriptors();
            T finalObject = object;
            claims.entrySet().forEach(entry -> {
                Arrays.stream(props).forEach(prop -> {
                    if (Objects.nonNull(prop.getWriteMethod()) && StringUtils
                            .equals(prop.getDisplayName(), entry.getKey())) {
                        try {
                            Method writeMethod = prop.getWriteMethod();
                            writeMethod.invoke(finalObject, writeMethod.getParameterTypes()[0].cast(entry.getValue()));
                        } catch (Exception e) {
                            // log.debug("{}", e);
                        }
                    }
                });
            });
        } catch (Exception e) {
            // log.info("{}", e);
            // throw new AuthException("Auth Token is not valid", 1001);
        }
        return object;
    }

}
