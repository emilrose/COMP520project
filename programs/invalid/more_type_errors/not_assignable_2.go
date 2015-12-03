// mismatched types num and int

package main

type num int

func main() {
	var x = 2
	var y num = x + 1
	print(y)
}