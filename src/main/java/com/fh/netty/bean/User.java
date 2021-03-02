package com.fh.netty.bean;

import java.util.HashSet;
import java.util.Iterator;

/**
 * 用户信息
 */
public class User {

    private String id;

    public User() {
    }

    public User(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof User) {
            return this.id == ((User) obj).id;
        }
        return false;
    }

    public static void main(String[] args) {
        HashSet<User> set = new HashSet<>();
        set.add(new User("1"));
        set.add(new User("2"));
        set.add(new User("3"));
        set.add(new User("1"));
        set.add(new User("5"));

        System.out.println(set.size());

        for(Iterator<User> it = set.iterator(); it.hasNext();) {
            System.out.println(it.next());
        }

    }
}
