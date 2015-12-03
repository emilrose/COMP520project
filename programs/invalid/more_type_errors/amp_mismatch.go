// mismatched types int and bool

package main

func main() {
	x := 1
	y := x==x
	print(x&y)
}

