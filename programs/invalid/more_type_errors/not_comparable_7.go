// struct containing []int cannot be compared

package main

func main() {
	type t1 struct {
		x []int
	}
	var a, b t1
	if a == b {
		println("a equals b")
	}
}