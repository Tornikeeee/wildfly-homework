package com.tadamia.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tadamia.Main;
import com.tadamia.Rectangle;
import com.tadamia.conf.ConfManager;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;


@WebServlet(name = "getRectanglesServlet", urlPatterns = "/get-rectangles")
public class getRectanglesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.setPrettyPrinting().create();

        try {
            final double p = Double.parseDouble(req.getParameter("p"));
            if (p < 0)
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);

            InputStream in = this.getClass().getClassLoader().getResourceAsStream("rectangles.json");
            assert in != null;
//            BufferedReader bufferedReader = ConfManager.getConfiguration().getBufferedReader();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            if (bufferedReader != null)
                Main.lgg.info("rectangles.json has loaded");

            Rectangle[] rectangles = gson.fromJson(bufferedReader, Rectangle[].class);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();

            StringJoiner rectanglesJson = new StringJoiner("\n");
            for (Rectangle rectangle : rectangles) {
                if (rectangle.getLength() == p)
                    rectanglesJson.add(gson.toJson(rectangle));
            }

            if (rectanglesJson.length() == 0)
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);

            out.println(rectanglesJson);
            out.close();
        } catch (NumberFormatException | NullPointerException e) {
            try {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
