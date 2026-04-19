import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.Context;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String timezoneParam = req.getParameter("timezone");
        String timezoneToUse = "UTC";

        if (timezoneParam != null && !timezoneParam.isEmpty()) {
            // 1. Priority: Query Parameter
            timezoneToUse = timezoneParam.replace(" ", "+");

            Cookie cookie = new Cookie("lastTimezone", timezoneToUse);
            cookie.setMaxAge(60 * 60 * 24); // 1 day
            resp.addCookie(cookie);
        } else {
            // 2. Secondary: Check Cookies
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                timezoneToUse = Arrays.stream(cookies)
                        .filter(c -> "lastTimezone".equals(c.getName()))
                        .map(Cookie::getValue)
                        .findFirst()
                        .orElse("UTC");
            }
        }

        ZoneId zoneId = ZoneId.of(timezoneToUse);
        ZonedDateTime now = ZonedDateTime.now(zoneId);
        String formattedTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));


        Context context = new Context();
        context.setVariable("time", formattedTime);
        context.setVariable("timezone", timezoneToUse);

        ThymeleafConfig.render("time", context, resp);
    }
}
