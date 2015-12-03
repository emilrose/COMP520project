// mismatched types float and int
// Note: this compiles in Go

package main

func main() {
	x := 2.
	var y float64 = x / 2
	println(y)
    println("valid in Go")
}