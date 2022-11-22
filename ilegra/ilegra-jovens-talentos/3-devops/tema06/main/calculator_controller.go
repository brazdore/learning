package main

import (
	"github.com/gin-gonic/gin"
	"log"
	"net/http"
	"os"
	"strconv"
	"strings"
)

var logger = log.New(os.Stdout, "", log.Ldate|log.Lmicroseconds|log.Lshortfile)

func calcHandler(context *gin.Context) {
	op := strings.ToLower(context.Param("op"))
	x, xErr := strconv.ParseFloat(context.Param("x"), 64)
	y, yErr := strconv.ParseFloat(context.Param("y"), 64)

	var err error

	if xErr != nil {
		err = xErr
	} else {
		err = yErr
	}

	if err != nil {
		context.IndentedJSON(http.StatusBadRequest, buildConversionError(err))
	} else {
		switch op {
		case "sum":
			context.IndentedJSON(http.StatusOK, addition(x, y))

		case "sub":
			context.IndentedJSON(http.StatusOK, subtraction(x, y))

		case "mul":
			context.IndentedJSON(http.StatusOK, multiplication(x, y))

		case "div":
			result, threwError := division(x, y)
			if !threwError {
				context.IndentedJSON(http.StatusOK, result)
			} else {
				context.IndentedJSON(http.StatusBadRequest, buildDivisionByZeroError())
			}

		case "exp":
			context.IndentedJSON(http.StatusOK, exponentiation(x, y))

		default:
			context.IndentedJSON(http.StatusBadRequest, buildOperationPathError(op))
		}
	}
}

func historyHandler(context *gin.Context) {
	context.IndentedJSON(http.StatusOK, getHistory())
}

func clearHandler(context *gin.Context) {
	context.IndentedJSON(http.StatusOK, clear())
}

func headersHandler(context *gin.Context) {
	req := context.Request.Header
	req.Add("Host", context.Request.Host) // Por algum motivo o ´context.Request.Header´ não retorna o "HOST"; adicionado manualmente.
	context.IndentedJSON(http.StatusOK, req)
}

func main() {
	r := gin.Default()
	r.GET("/calc/:op/:x/:y", calcHandler)
	r.GET("/calc/history", historyHandler)
	r.GET("/calc/clear", clearHandler)
	r.GET("/headers", headersHandler)

	err := r.Run(":8080")
	if err != nil {
		logger.Println(err.Error())
	}
}
