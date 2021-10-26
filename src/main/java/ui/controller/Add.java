package ui.controller;

import domain.model.Animal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class Add extends RequestHandler {

    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArrayList<String> errors = new ArrayList<>();
        Animal animal = new Animal();
        setName(animal, request, errors);
        setType(animal, request, errors);
        setFood(animal, request, errors);
        if (errors.size() == 0) {
            try {
                service.addAnimal(animal);
                HttpSession session = request.getSession();
                // geef meer ingewikkelde informatie door met sendRedirect via session attribute
                // indien nodig: verwijder attribuut
                // in dit voorbeeld onnodig: we willen op overzichtspagina altijd laatst toegevoegde dier tonen
                session.setAttribute("lastAddedAnimal", animal);
                // creëer 300-status met sendRedirect
                // geef extra parameter door in url om bevestiginszin te tonen op overzichtspagina
                response.sendRedirect("Controller?command=Overview&confirmation=add");
                // return value mag niet null zijn, want anders nullpointerException in Controller
                return "Controller?command=Home";
            }
            catch (IllegalArgumentException exc) {
                request.setAttribute("error", exc.getMessage());
                return "Controller?command=AddForm";
            }
        }
        else {
            request.setAttribute("errors", errors);
            return "Controller?command=AddForm";
        }
    }

    private void setName(Animal animal, HttpServletRequest request, ArrayList<String> errors) {
        String name = request.getParameter("name");
        try {
            animal.setName(name);
            request.setAttribute("nameClass", "has-success");
            request.setAttribute("namePreviousValue", name);
        }
        catch (IllegalArgumentException exc) {
            errors.add(exc.getMessage());
            request.setAttribute("nameClass", "has-error");
        }
    }

    private void setType(Animal animal, HttpServletRequest request, ArrayList<String> errors) {
        String type = request.getParameter("type");
        try {
            animal.setType(type);
            request.setAttribute("typeClass", "has-success");
            request.setAttribute("typePreviousValue", type);
        }
        catch (IllegalArgumentException exc) {
            errors.add(exc.getMessage());
            request.setAttribute("typeClass", "has-error");
        }
    }

    private void setFood(Animal animal, HttpServletRequest request, ArrayList<String> errors) {
        int food;
        if(request.getParameter("food").isBlank()){
            food = -1;
        }else{
            food = Integer.parseInt(request.getParameter("food"));
        }
        try {
            animal.setFood(food);
            request.setAttribute("foodClass", "has-success");
            request.setAttribute("foodPreviousValue", food);
        }
        catch (IllegalArgumentException exc) {
            errors.add(exc.getMessage());
            request.setAttribute("foodClass", "has-error");
        }
    }
}
