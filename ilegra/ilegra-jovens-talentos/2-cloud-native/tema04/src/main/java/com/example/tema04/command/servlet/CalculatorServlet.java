package com.example.tema04.command.servlet;

import com.example.tema04.command.configs.CommandConfig;
import com.example.tema04.command.entities.*;
import com.example.tema04.command.exceptions.InvalidInputException;
import com.example.tema04.command.exceptions.InvalidOperationException;
import com.example.tema04.command.interfaces.Operation;
import com.example.tema04.command.services.CalculatorService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/calc")
public class CalculatorServlet extends HttpServlet {

    private final ApplicationContext context = new AnnotationConfigApplicationContext(CommandConfig.class);
    private final CalculatorService calculatorService = context.getBean("calculatorService", CalculatorService.class);
    private Operation operation = null;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter printWriter = response.getWriter();

        String operationType = request.getParameter("op");


        switch (operationType) {
            case "add":
                this.operation = context.getBean("addition", Addition.class);
                break;

            case "sub":
                this.operation = context.getBean("subtraction", Subtraction.class);
                break;

            case "mul":
                this.operation = context.getBean("multiplication", Multiplication.class);
                break;

            case "div":
                this.operation = context.getBean("division", Division.class);
                break;

            case "exp":
                this.operation = context.getBean("exponentiation", Exponentiation.class);
                break;

            case "history":
                printWriter.println(calculatorService.getCalculatorHistory().getHistory());
                break;

            default:
                throw new InvalidOperationException("Invalid operation! [" + operationType + "] is not valid. Try: " +
                        "add; sub; mul; div; exp; history.");
        }

        if (!operationType.equals("history")) {

            if (!calculatorService.areBothValid((request.getParameter("x")), (request.getParameter("y")))) {
                throw new InvalidInputException("At least one input is invalid: x=[" + (request.getParameter("x")) + "]" +
                        " OR y=[" + (request.getParameter("y")) + "].");
            }

            var x = Double.parseDouble(request.getParameter("x"));
            var y = Double.parseDouble(request.getParameter("y"));
            this.operation.setValues(x, y);
            var result = calculatorService.execute(this.operation);
            printWriter.println(result);
        }
    }
}