package ui.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddForm extends RequestHandler{
    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        // om sneller te testen
        request.setAttribute("namePreviousValue","Blaf");
        request.setAttribute("typePreviousValue","hond");
        request.setAttribute("foodPreviousValue","4");

        return "add.jsp";
    }
}
