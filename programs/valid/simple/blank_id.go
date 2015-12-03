package main

func _() {
	print()
}

func main() {
	type _ int
	var _ int
	x, _, z := "x", "y", "z"
	if _, err := 0, 1; err == 0 {
		print(x, z)
	}
	var y int
	y, _ = 1, 2
	if false {
		print(y)
	}
}
