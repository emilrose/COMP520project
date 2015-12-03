package main

func main() {
	// hexadecimal and octal ints
	x, y, z := 0x12FA, 0xff, 035
	// floats
	a, b, c := 135., .141, 123.123
	w := float64(x + y + z)
	var w2 float64 = float64(x)
	type num int
	var m int = y
	var n num = num(m)
	// runes
	r, r2, r3, r4, r5 := '\r', '\a', '\\', '\f', '\n'
	r = '\v'
	r2 = '\''
	r3 = '\b'
	r4 = '\t'
	// strings
	s := `str\n`
	s2 := "sdlf" + "sdflkj"
	if false {
		print(a, b, c, n, w, w2, r, r2, r3, r4, r5, s, s2)
	}
}
