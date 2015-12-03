// km and mi are not comparable

package main

type km int
type mi int

func main() {
	x, y := km(1), mi(1)
	if x == y {
		print(y)
	}
}