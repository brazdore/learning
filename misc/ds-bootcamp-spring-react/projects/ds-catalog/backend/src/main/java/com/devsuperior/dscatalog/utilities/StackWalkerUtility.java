package com.devsuperior.dscatalog.utilities;

import java.util.concurrent.atomic.AtomicReference;

public class StackWalkerUtility {

    /**
     *
     * @see <a href=https://www.baeldung.com/java-name-of-executing-method>StackWalker @ Baeldung</a>
     */

    public static String getMethodName() {
        StackWalker walker = StackWalker.getInstance();
        AtomicReference<String> methodName = new AtomicReference<>("UNKNOWN");
        walker.walk(frames -> frames
                        .skip(1L)
                        .findFirst()
                        .map(StackWalker.StackFrame::getMethodName))
                .ifPresent(methodName::set);
        return methodName.get();
    }
}
