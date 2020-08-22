package com.chris.tiantian.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by jianjianhong on 19-12-5
 */
public class GroupUtil {

    public interface GroupBy<T> {
        T groupBy(Object obj);
    }

    /**
     * 对列表分组，每组数据并且排序
     * @param colls
     * @param gb
     * @return
     */
    public static final <T extends Comparable<T>, D extends Comparable<D>> Map<T, List<D>> group(Collection<D> colls, GroupBy<T> gb) {
        if (colls == null || colls.isEmpty()) {
            System.out.println("分組集合不能為空!");
            return null;
        }
        if (gb == null) {
            System.out.println("分組依據接口不能Null!");
            return null;
        }
        Iterator<D> iter = colls.iterator();
        Map<T, List<D>> map = new TreeMap<>();
        while (iter.hasNext()) {
            D d = iter.next();
            T t = gb.groupBy(d);
            if (map.containsKey(t)) {
                map.get(t).add(d);
            } else {
                List<D> list = new ArrayList<D>();
                list.add(d);
                map.put(t, list);
            }
        }

        Iterator iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            Collections.sort(map.get(iterator.next()));
        }
        return map;
    }
}
