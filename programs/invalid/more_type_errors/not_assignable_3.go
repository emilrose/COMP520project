// mismatched types num and int
// Note: this typechecks in Go

package main

type num int

func main() {
	var x num = 2
	print(x)
}
