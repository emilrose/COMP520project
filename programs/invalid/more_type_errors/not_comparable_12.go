// cannot compare functions

package main

func f1() {
	print()
}

func f2() {
	print()
}

func main() {
	if f1 == f2 {
	}
}
