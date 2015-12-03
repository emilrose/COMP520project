// mismatched types int alias and rune alias

package main

type integer int
var x integer

func main() {
	type integer rune
	var y integer
	if x==y {}
}
