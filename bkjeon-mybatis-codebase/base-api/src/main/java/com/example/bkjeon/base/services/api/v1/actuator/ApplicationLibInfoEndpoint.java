package com.example.bkjeon.base.services.api.v1.actuator;

import com.example.bkjeon.dto.actuator.LibarayInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
// @Endpoint(id = "applicationLibInfo")
// @JmxEndpoint(id = "applicationLibInfo")
@WebEndpoint(id = "applicationLibInfo") // web 만 열어준다.
public class ApplicationLibInfoEndpoint {

    @ReadOperation
    public String getPathParameters(@Selector(match = Selector.Match.ALL_REMAINING) String[] paths) {
        return "pathList: " + Arrays.asList(paths);
    }

    @ReadOperation
    public String getPathParameter(@Selector String path) {
        return "path: " + path;
    }

    @WriteOperation
    public void changeSomething(String name, boolean enableSomething) {
        log.info("name: {}, enableSomething: {} ", name, enableSomething);
    }

    @ReadOperation
    public List<LibarayInfo> getLibraryInfo(@Nullable String name, boolean includeVersion) {
        LibarayInfo libarayInfo = LibarayInfo.builder()
                .name("bkjeon")
                .version("1.0.0")
                .build();

        LibarayInfo libarayInfo2 = LibarayInfo.builder()
                .name("bkjeon2")
                .version("2.0.0")
                .build();

        List<LibarayInfo> libarayInfoList =  Arrays.asList(libarayInfo, libarayInfo2);

        if (name != null) {
            libarayInfoList = libarayInfoList.stream()
                    .filter(lib -> lib.getName().equals(name))
                    .collect(Collectors.toList());
        }

        if (!includeVersion) {
            libarayInfoList = libarayInfoList.stream()
                    .map(lib -> LibarayInfo.builder().name(lib.getName()).build())
                    .collect(Collectors.toList());
        }

        return libarayInfoList;
    }

}
