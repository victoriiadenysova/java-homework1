import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;


@WebFilter("/time")
public class TimezoneValidateFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        String timezoneParam = req.getParameter("timezone");


        if (timezoneParam != null && !timezoneParam.isEmpty()) {
            String formattedTz = timezoneParam.replace(" ", "+");

            try {

                ZoneId.of(formattedTz);
            } catch (java.time.DateTimeException e) {

                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Код 400
                resp.setContentType("text/html; charset=UTF-8");
                resp.getWriter().write("Invalid timezone");
                return;
            }
        }


        chain.doFilter(req, resp);
    }
}