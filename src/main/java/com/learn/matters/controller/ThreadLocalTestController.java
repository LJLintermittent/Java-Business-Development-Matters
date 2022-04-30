package com.learn.matters.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * date: 2022/4/30 20:02
 * Package: com.learn.matters.controller
 *
 * @author 李佳乐
 * @email 18066550996@163.com
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/api/L1")
public class ThreadLocalTestController {

    private static final ThreadLocal<Integer> currentUser = ThreadLocal.withInitial(() -> null);

    @GetMapping("wrong")
    public Map<String, String> wrong(@RequestParam("userId") Integer userId) {
        String before = Thread.currentThread().getName() + "---" + currentUser.get();
        currentUser.set(userId);
        String after = Thread.currentThread().getName() + "---" + currentUser.get();
        Map<String, String> result = new HashMap<>(16);
        result.put("before", before);
        result.put("after", after);
        return result;
    }

    @GetMapping("right")
    public Map<String, String> right(@RequestParam("userId") Integer userId) {
        String before = Thread.currentThread().getName() + "---" + currentUser.get();
        currentUser.set(userId);
        try {
            String after = Thread.currentThread().getName() + "---" + currentUser.get();
            Map<String, String> result = new HashMap<>(16);
            result.put("before", before);
            result.put("after", after);
            return result;
        } finally {
            currentUser.remove();
        }
    }

}
