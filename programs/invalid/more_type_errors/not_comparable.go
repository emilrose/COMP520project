// mismatched types integer and int

package main

func main() {
	type integer int
	var x = integer(2)
	var y = 2
	if x == y {
	}
}