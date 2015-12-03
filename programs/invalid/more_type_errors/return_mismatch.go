// mismatched types int and num

package main

type num int

func f(x int, y num) int {
	return x + y
}

func main() {

}
