// mismatched types int and float64

package main

func main() {
	a, b := 4, 1.1
	var x float64 = a-b
	print(x)
}
