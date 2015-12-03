package main

// type declarations
type nat int
type (
	double float64
	pair   struct{
    x, y int
    }
)
type list struct {
	size int
	tp   string
}

// variable declarations
var x bool
var y, x2 = `hello\n`, 3
var (
	a, b, c rune
	m, n           = 0, 1
	u, v, w string = "u", "v", "w"
)

// function declarations
func f(x int, y int) int {
	return x + y
}
func f2(x, y, z int, c, d bool) int {
	if c {
		return x
	}
	if d {
		return y
	}
	return z
}
func f3(p pair, x, y double) {
	print()
}

func main() {
	// variable declarations
	x = false
	if x {
		print(y, x2)
	}
	a, b, c = 'a', 'b', 'c'
	if n == m {
		print(a, b, c, u, v, w)
	}
	var k nat = nat(1)
	var (
		l list
		d, d2 double = double(2.), double(3.51)
		p pair
	)
	l.size, l.tp, p.x, p.y = 0, "int", 1, 1
	if x {
		print(k, l.tp, p.y, d)
	}
	f3(p, d, d2)
	type (
		strPair struct{
        x, y string
        }
	)

}

