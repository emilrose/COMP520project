package main

func main() {
	if x := 5; (x<0) {
		println(0)
	} else if x:= 6; (x<4) {
		println(4)
	} else if (x<10) {
		println(10)
	} else {
		println()
	}

}