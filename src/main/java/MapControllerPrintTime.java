import com.sun.btrace.annotations.*;

import static com.sun.btrace.BTraceUtils.*;

@BTrace
public class MapControllerPrintTime {
	@TLS
	private static long startTime = 0;

	@OnMethod(clazz = "com.bj58.wf.api.wap58.com.controllers.MapController", method = "appZBNEW")
	public static void startMethod() {
		startTime = timeMillis();
	}

	@SuppressWarnings("deprecation")
	@OnMethod(clazz = "com.bj58.wf.api.wap58.com.controllers.MapController", method = "appZBNEW", location = @Location(Kind.RETURN))
	public static void endMethod() {
		long time = timeMillis() - startTime;
		if(time > 500) {
			print(strcat(strcat(name(probeClass()), "."), probeMethod()));
			print("[");
			println(strcat("Time taken: ", str(time)));
		}
	}
}