import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@WebServlet("/time")

public class TimeServlet extends HttpServlet {

    @Override

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        String timezoneParam = req.getParameter("timezone");


        ZoneId zoneId;

        if (timezoneParam == null || timezoneParam.isEmpty()) {

            zoneId = ZoneId.of("UTC");

        } else {


            String formattedTz = timezoneParam.replace(" ", "+");

            zoneId = ZoneId.of(formattedTz);

        }


        ZonedDateTime now = ZonedDateTime.now(zoneId);

        String formattedTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String displayTz = zoneId.getId();


        resp.setContentType("text/html; charset=UTF-8");

        resp.getWriter().write(String.format(

                "<html><body><h1>%s %s</h1></body></html>",

                formattedTime, displayTz

        ));

    }

}