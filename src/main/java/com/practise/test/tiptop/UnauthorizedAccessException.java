package com.practise.test.tiptop;

import java.io.IOException;

public class UnauthorizedAccessException extends IOException {
    public UnauthorizedAccessException(String message) {
        super(message);
    }

}
