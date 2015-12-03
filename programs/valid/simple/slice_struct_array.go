package main

type merch struct {
	name   string
	amount int
	price  int
}

func main() {
	x := false
	// slice
	var s []string
	s = append(s, "a")
	s = append(s, "b")
	if x {
    println(s[0], s[1])
    }

	// struct
	var hat merch
	hat.name = "snapback"
	hat.amount = 2
	hat.price = 20
	if x {
    println(hat.name, hat.amount, hat.price)
    }

	// array
	var arr [2]bool
	arr[0] = true
	arr[1] = false
	if x {
    println(arr[0], arr[1])
    }
}
