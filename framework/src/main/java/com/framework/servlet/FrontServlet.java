package com.framework.servlet;

import com.framework.utils.JavaControllerScanner;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class FrontServlet extends HttpServlet {
    private Set<Class<?>> controllers;

    @Override
    public void init() throws ServletException {
        try {
            controllers = JavaControllerScanner.findControllers();
            System.out.println(controllers);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // Get resource
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        boolean ressourceContext = getServletContext().getResource(path)!=null;
        if(!ressourceContext){
            Method method = JavaControllerScanner.getMethodMappedWithUrl(controllers,path);
            String message="";
            if(method!=null){
                message = "Methode trouve:"+method.getName();
            }
            else{
                message = "Erreur 404:Not Found";
            }
            response.getWriter().println(message);
        }
        else{
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/default_url");
            dispatcher.forward(request, response);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        service(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        service(request, response);
    }

}