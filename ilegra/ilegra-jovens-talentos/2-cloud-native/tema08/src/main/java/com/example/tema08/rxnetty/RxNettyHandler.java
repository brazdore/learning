package com.example.tema08.rxnetty;

import com.example.tema08.entities.*;
import com.example.tema08.exceptions.*;
import com.example.tema08.services.CalculatorService;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.reactivex.netty.protocol.http.server.RequestHandler;
import netflix.karyon.transport.http.health.HealthCheckEndpoint;
import rx.Observable;

import java.time.Instant;

public class RxNettyHandler implements RequestHandler<ByteBuf, ByteBuf> {

    private final String healthCheckURI;
    private final HealthCheckEndpoint healthCheckEndpoint;

    private final HealthCheckResource healthCheckResource;
    private final CalculatorService calculatorService;


    public RxNettyHandler(String healthCheckUri, HealthCheckEndpoint healthCheckEndpoint,
                          HealthCheckResource healthCheckResource, CalculatorService calculatorService) {
        this.healthCheckURI = healthCheckUri;
        this.healthCheckEndpoint = healthCheckEndpoint;
        this.healthCheckResource = healthCheckResource;
        this.calculatorService = calculatorService;
    }

    @Override
    public Observable<Void> handle(HttpServerRequest<ByteBuf> request, HttpServerResponse<ByteBuf> response) {
        if (request.getUri().equals(healthCheckURI) && request.getHttpMethod().equals(HttpMethod.GET)) { // GET -> /health
            response.setStatus(HttpResponseStatus.valueOf(healthCheckResource.getStatus()));
            response.writeStringAndFlush("Healthy!");
            return healthCheckEndpoint.handle(request, response);
        }

        if (request.getUri().equals("/") && request.getHttpMethod().equals(HttpMethod.GET)) { // GET -> /
            response.setStatus(HttpResponseStatus.OK);
            return response.writeStringAndFlush("RxNetty is running.");
        }

        if (request.getUri().equals("/clear") && request.getHttpMethod().equals(HttpMethod.DELETE)) { // DELETE -> /clear
            response.setStatus(HttpResponseStatus.OK);
            calculatorService.getHistory().clear();
            return response.writeStringAndFlush(calculatorService.getHistory().toString());
        }

        if (request.getHttpMethod().equals(HttpMethod.GET)) { // GET
            try {
                Operation operation;
                var path = request.getPath();

                switch (path) {
                    case "/add":
                        operation = new Addition();
                        break;

                    case "/sub":
                        operation = new Subtraction();
                        break;

                    case "/mul":
                        operation = new Multiplication();
                        break;

                    case "/div":
                        operation = new Division();
                        break;

                    case "/exp":
                        operation = new Exponentiation();
                        break;

                    case "/history":
                        response.setStatus(HttpResponseStatus.OK);
                        return response.writeStringAndFlush(calculatorService.getHistory().toString());

                    default:
                        throw new InvalidOperationException("Invalid path! [" + path + "] is not valid. Try: GET " +
                                "/add; /sub; /mul; /div; /exp; /history OR DELETE /clear");
                }

                var parameters = request.getQueryParameters();

                if (!parameters.containsKey("x") || !parameters.containsKey("y")) {
                    throw new ParameterExpectedException("Parameters [x] & [y] expected. Try: /add?x=2&y=3");
                }

                if (!calculatorService.areBothValid(parameters.get("x").get(0), parameters.get("y").get(0))) {
                    throw new InvalidInputException("At least one input is invalid: x=[" + (parameters.get("x").get(0)) + "]" +
                            " OR y=[" + (parameters.get("y").get(0)) + "].");
                }

                var x = Double.parseDouble(parameters.get("x").get(0));
                var y = Double.parseDouble(parameters.get("y").get(0));
                operation.setValues(x, y);
                var result = calculatorService.execute(operation);

                response.setStatus(HttpResponseStatus.OK);
                return response.writeStringAndFlush(operation + " equals to " + result);

            } catch (InvalidOperationException | InvalidInputException | CalculatorDivisionException | ParameterExpectedException e) {
                response.setStatus(HttpResponseStatus.BAD_REQUEST);
                StandardError standardError = new StandardError(Instant.now(), 400, e.getMessage(), request.getPath());
                return response.writeStringAndFlush(standardError.toString());
            }
        } // POST, PUT, DELETE (exc. /clear)
        response.setStatus(HttpResponseStatus.BAD_REQUEST);
        StandardError standardError = new StandardError(Instant.now(), 400, "POST, PUT & DELETE (except /clear)" +
                " are not avaliable!", request.getPath());
        return response.writeStringAndFlush(standardError.toString());
    }
}
