package com.simplevoting.menuvoting;

import com.simplevoting.menuvoting.model.User;
import com.simplevoting.menuvoting.utils.json.JsonUtils;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.simplevoting.menuvoting.utils.json.JsonUtils.wrightIgnoreProps;
import static com.simplevoting.menuvoting.utils.json.JsonUtils.wrightValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class TestUtils {

    public static <T> ResultMatcher contentJson(T expected) {
        return content().json(wrightValue(expected));
    }

    public static <T> ResultMatcher contentJsonWithIgnore(T expected, String... props) {
        return content().json(wrightIgnoreProps(expected, props));
    }

    public static <T> ResultMatcher contentJsonWithIgnore(Collection<T> expected, String... props) {
        return content().json(wrightIgnoreProps(expected, props));
    }

    public static String getContent(ResultActions action) throws UnsupportedEncodingException {
        return action.andReturn().getResponse().getContentAsString();
    }

    public static <T> T readFromJson(ResultActions action, Class<T> tClass) throws UnsupportedEncodingException {
        return JsonUtils.readValue(getContent(action), tClass);
    }

    public static <T> T readFromJsonTrimFields(ResultActions action, Class<T> tClass, String... fieldsToTrim) throws UnsupportedEncodingException {
        String jsonObj = Arrays.stream(getContent(action).split(","))
                .map(str -> {
                    if (Arrays.stream(fieldsToTrim).filter(tr -> str.contains(tr)).findAny().isPresent()) {
                        return str.contains("}") ? "}" : "";
                    }
                    return str;
                })
                .collect(Collectors.joining(","));
        jsonObj = jsonObj.replaceAll(",}", "}");
        return JsonUtils.readValue(jsonObj, tClass);
    }

    public static RequestPostProcessor authUser(User user) {
        return SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword());
    }
}
