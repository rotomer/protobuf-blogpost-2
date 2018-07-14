package com.rotomer.simplevm;

import java.util.UUID;

class IdGenerator {
    static String nextId() {
        return UUID.randomUUID().toString();
    }
}
