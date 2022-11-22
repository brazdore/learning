package main

import (
	"math"
)

func addition(x, y float64) float64 {
	save("ADDITION", x, y)
	return x + y
}

func subtraction(x, y float64) float64 {
	save("SUBTRACTION", x, y)
	return x - y
}

func multiplication(x, y float64) float64 {
	save("MULTIPLICATION", x, y)
	return x * y
}

func division(x, y float64) (float64, bool) {
	if y == 0 {
		return 0, true
	} else {
		save("DIVISION", x, y)
		return x / y, false
	}
}

func exponentiation(x, y float64) float64 {
	save("EXPONENTIATION", x, y)
	return math.Pow(x, y)
}
