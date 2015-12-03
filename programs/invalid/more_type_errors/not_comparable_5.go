// mismatched structs

package main

func main() {
	type t1 struct {
		x int
	}

	type t2 struct {
		x int
	}
	var a t1
	var b t2
	if a == b {
		println("a equals b")
	}
}

