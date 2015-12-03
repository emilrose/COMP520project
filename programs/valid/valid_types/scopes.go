package main

var x int

type str string

type point struct {
	// struct body starts new scope
	// x shadows line 3 x
	x, y float64
	// point shadows line 7 point
	point float64
}

// func parameters start new scope (same scope as func body)
// y not yet declared in toplevel
// x shadows line 3 x
func y(x int) {
	// y shadows line 18 y, str shadows line 5 str
	var y bool = true
	var str bool = true
	if y {
		// if body starts new scope
		// y shadows line 19 y
		var y string = "false"
		// str shadows line 21 str
		var str int = 2
		println("y:", y)
		println("str:", str)
	}
	println("str:", str)
}

// func parameters start new scope (same scope as func body)
func main() {
	// x shadows line 3 x
	// y shadows line 18 y
	x, y := "xs", "ys"
	println("x:", x, "\ny:", y)
	var p point
	// x is also a field name of point struct
	p.x = 2.0
	// str shadows line 5 str
	var str int = 341
	println("p.y:", p.y, "\nstr:", str)
	// if init statement starts new scope
	// x shadows line 38 x
	if x := true; x {
		// if body starts new scope
		// x shadows line 48 x
		// y shadows line 38 y
		x, y := 3, 45
		for {
			// for body starts new scope
			// x shadows line 52 x
			var x point
			// x, y are also field names of point struct
			x.x, x.y = .2, .8
			break
		}
		println("x:", x, "\ny:", y)
	}
	p.y = 0.1
	if true {
		println("p.y:", p.y)
		// if body starts new scope
		// x shadows line 38 x
		type x rune
		// y shadows line 38 y
		var y x = x('a')
		if false {
			println("y:", y)
		}
		// x shadows line 68 x
		for x := 0; x < 1; x++ {
			// for body starts new scope
			// x shadows line 75 x
			var x = 10
			// switch init starts new scope
			// x shadows line 78 x
			switch x := 2.5; true {
			case x < 3.0:
				// case clause starts new scope
				// x shadows line 81 x
				x := "x"
				println("x:", x)
			default:
				println("x:", x)
			}
			println("x:", x)
		}
		// block starts new scope
		{
			// x shadows line 68 x
			// y shadows line 70 y
			x, y := true, true
			println("x:", x && y)
		}
	}
}
