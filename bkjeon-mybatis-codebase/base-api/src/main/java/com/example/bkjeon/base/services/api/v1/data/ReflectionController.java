package com.example.bkjeon.base.services.api.v1.data;

import com.example.bkjeon.entity.data.reflection.RefUserObject;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;

@Slf4j
@RestController
@RequestMapping("v1/reflection")
public class ReflectionController {

    @ApiOperation("Reflection 테스트")
    @GetMapping("userObjects")
    public String getReflectionUser() {
        try {
            Class<?> clazz = RefUserObject.class;

            Field[] fields = clazz.getDeclaredFields();
            for (Field field: fields) {
                log.info(">>>>>>>>>>>>>>>>>>>>>>>>> Field Name: {}", field.getName());
            }

            Field field = clazz.getDeclaredField("userNo");

            // 컴파일시 cannot access a member of class ... with modifiers "private" 에러를 표시하지 않기 위해 추가
            field.setAccessible(true);

            // 필드값을 다루기 위한, 객체 생성
            RefUserObject refUserObject = new RefUserObject();
            field.set(refUserObject, 3);
            int userNo = (int) field.get(refUserObject);
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>> userNo: {}", userNo);
        } catch (NoSuchFieldException nsfe) {
            log.error("NoSuchFieldException Error: {}", nsfe);
        } catch (SecurityException se) {
            log.error("SecurityException Error: {}", se);
        } catch (Exception e) {
            log.error("Exception Error: {}", e);
        }

        return "INFO LOG OUTPUT !!";
    }

}
