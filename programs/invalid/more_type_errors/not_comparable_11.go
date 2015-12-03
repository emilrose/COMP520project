// mismatched types int alias and float64 alias

package main

type num int
var int_var num = 1

func main() {
	type num float64
	var float_var num = 1.
	if int_var != float_var {
	}
	
}
