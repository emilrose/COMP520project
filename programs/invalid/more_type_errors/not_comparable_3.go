// operator <= not defined on struct

package main

type p struct {
	x int
}

func main() {
	var p1 p
	var q1 p
	p1.x = 1
	q1.x = 1
	if p1<=q1 {
		print("leq")
	}
}

