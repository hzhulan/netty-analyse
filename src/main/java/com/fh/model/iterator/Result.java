package com.fh.model.iterator;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.function.Consumer;

public class Result<T> implements Iterable<T> {

    private String name;

    private T[] result;

    public Result(String name, T... data) {
        this.name = name;
        result = data;
    }

    @Override
    //todo 继续
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < result.length;
            }

            @Override
            public T next() {
                return result[index++];
            }

            @Override
            public void remove() {
                throw new RuntimeException("不支持删除");
            }

            @Override
            public void forEachRemaining(Consumer<? super T> action) {
                if (action == null) {
                    throw new NullPointerException();
                }
                if (result == null) {
                    throw new ConcurrentModificationException("结果不能为空");
                }
                for (; index < result.length; index++) {
                    action.accept(result[index]);
                }
            }
        };
    }

    public static void main(String[] args) {
        Result<String> result = new Result<>("张三", "123", "163", "kids-want");
        for(Iterator<String> it = result.iterator(); it.hasNext();) {
            System.out.println(it.next());
        }
//        result.iterator().forEachRemaining(e -> System.out.println(e));
//        result.forEach(System.out::println);
    }
}
