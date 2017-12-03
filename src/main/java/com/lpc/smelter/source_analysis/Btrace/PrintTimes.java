package com.lpc.smelter.source_analysis.Btrace;

import com.sun.btrace.annotations.*;

import static com.sun.btrace.BTraceUtils.*;

@BTrace
public class PrintTimes {
	@TLS
	private static long startTime = 0;

	@OnMethod(clazz = "/.+/", // 所有类
			method = "/visitWeb/" // 被监控的方法
	)
	public static void startMethod() {
		startTime = timeMillis();
	}

	@SuppressWarnings("deprecation")
	@OnMethod(clazz = "/.+/", method = "/visitWeb/", location = @Location(Kind.RETURN))
	public static void endMethod() {
		print(strcat(strcat(name(probeClass()), "."), probeMethod()));
		print("[");
		print(strcat("Time taken: ", str(timeMillis() - startTime)));
		println("]");

	}
}