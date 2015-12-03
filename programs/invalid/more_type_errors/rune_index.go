// index must have type int
// valid in Go

package main

func main() {
	var a [100]int
	x := 'a'
	a[x] = 0
	println(a[0], x)
    println("valid in Go")
}
