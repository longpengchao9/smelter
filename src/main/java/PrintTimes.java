import com.sun.btrace.annotations.*;

import static com.sun.btrace.BTraceUtils.*;

@BTrace
public class PrintTimes {
	@TLS
	private static long startTime = 0;

	@OnMethod(clazz = "/.+/", method = "/visitWeb/")
	public static void startMethod() {
		startTime = timeMillis();
	}

	@SuppressWarnings("deprecation")
	@OnMethod(clazz = "/.+/", method = "/visitWeb/", location = @Location(Kind.RETURN))
	public static void endMethod() {
		long time = timeMillis() - startTime;
		if(time > 10) {
			print(strcat(strcat(name(probeClass()), "."), probeMethod()));
			print("[");
			print(strcat("Time taken: ", str(timeMillis() - startTime)));
			println("]");
		}
	}
}