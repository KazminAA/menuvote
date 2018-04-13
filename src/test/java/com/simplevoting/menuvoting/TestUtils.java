package com.simplevoting.menuvoting;

import com.simplevoting.menuvoting.utils.json.JsonUtils;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.io.UnsupportedEncodingException;
import java.util.Collection;

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
}
