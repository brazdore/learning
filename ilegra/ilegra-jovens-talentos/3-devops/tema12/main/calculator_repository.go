package main

import "strconv"

type operationJSON struct {
	Operation string
	X         float64
	Y         float64
}

type clearedJSON struct {
	Status  string
	Deleted int
	Message string
}

var history []operationJSON

func getHistory() []operationJSON {
	return history
}


func save(op string, x, y float64) {
	newJSON := operationJSON{
		Operation: op,
		X:         x,
		Y:         y}

	history = append(history, newJSON)
}

func clear() clearedJSON {
	size := len(history)
	history = nil

	newJson := clearedJSON{
		Status:  "SUCCESS",
		Deleted: size,
		Message: "History cleared: [" + strconv.Itoa(size) + "] operation(s) deleted",
	}
	return newJson
}

