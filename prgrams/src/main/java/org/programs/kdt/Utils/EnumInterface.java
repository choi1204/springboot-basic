package org.programs.kdt.Utils;

import org.programs.kdt.Exception.EntityNotFoundException;
import org.programs.kdt.Exception.ErrorCode;

import java.util.Arrays;

public interface EnumInterface {
    String getType();

    static <T extends EnumInterface> T find(String type, T[] values) {
        return Arrays.stream(values)
                .filter(value -> value.getType().equals(type))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.INVALID_ENUM_TYPE));
    }
}


