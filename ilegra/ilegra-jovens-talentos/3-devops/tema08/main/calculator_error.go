package main

import (
	"net/http"
	"time"
)

type errorObject struct {
	Code    int
	Type    string
	Message string
	Time    time.Time
}

func buildDivisionByZeroError() errorObject {
	mathError := errorObject{
		Code:    http.StatusBadRequest,
		Type:    "MATH",
		Message: "Error: Cannot Divide by [ZERO]",
		Time:    time.Now()}

	return mathError
}

func buildConversionError(errorMsg error) errorObject {
	conversionError := errorObject{
		Code:    http.StatusBadRequest,
		Type:    "CONVERSION",
		Message: "Error: [" + errorMsg.Error() + "]",
		Time:    time.Now()}

	return conversionError
}

func buildOperationPathError(invalidOperation string) errorObject {
	operationError := errorObject{
		Code:    http.StatusBadRequest,
		Type:    "PATH",
		Message: "Error: [" + invalidOperation + "] is not a valid operation. Try: sum; sub; mul; div; exp",
		Time:    time.Now(),
	}

	return operationError
}
