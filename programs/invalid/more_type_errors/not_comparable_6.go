// mismatched structs

package main

type t1 struct {
	x int
}
var a t1

func main() {
	type t1 struct {
		x int
	}

	var b t1
	if a == b {
		println("a equals b")
	}
}
