// mismatched types struct and int

package main

type v struct {
	x int
}

func main() {
	var w v
	w.x = 2
	print(w / 2)
}

