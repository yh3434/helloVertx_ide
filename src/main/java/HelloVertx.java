import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.function.Consumer;


/**
 * Created by wbb on 16/9/29.
 */
public class HelloVertx extends AbstractVerticle {
    public static void main(String[] args) {
        String verticID = HelloVertx.class.getName();
        runExample(verticID);
    }

    @Override
    public void start() throws Exception {
        final Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());
        router.get("/hello").handler(
                event
                        -> event.response().putHeader("content-type", "text/html").end("{\"error_code\":\"1\"}")
        );
        // 传递方法引用，监听端口
        vertx.createHttpServer()
                .requestHandler(
                        router::accept
                        //(HttpServerRequest event) -> router.accept(event)
                )
                .listen(8080);// 监听端口号
    }

    public static void runExample(String verticleID) {
        VertxOptions vertxOptions = new VertxOptions();

        Consumer<Vertx> runner = vertx -> {
            vertx.deployVerticle(verticleID);
        };

        Vertx vertx = Vertx.vertx(vertxOptions);
        runner.accept(vertx);
    }
}
