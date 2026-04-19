import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.context.Context;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ThymeleafConfig {
    private static final TemplateEngine engine;

    static {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML");
        resolver.setCharacterEncoding("UTF-8");

        engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);
    }

    public static void render(String template, Context context, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        engine.process(template, context, resp.getWriter());
    }
}

