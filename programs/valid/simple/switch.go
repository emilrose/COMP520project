package main

func f1() {
	print()
}

func f2() {
	print()
}

func f3() {
	print()
}

func main() {
	y := 10
	switch x := 10; true {
	case y < x:
		y++
		f1()
	case y == x:
		f2()
	case y > x:
		y--
		f3()

	}
	switch z := 10; true {
	case y < z:
		y++
		f1()
	default:
		y--
		f3()
	case y == z:
		f2()
	}
	a := 10
	switch true {
	case y < a:
		y++
		f1()
	case y == a:
		f2()
	case y > a:
		y--
		f3()

	}
	switch {
	case y < a:
		f1()
	case y == a:
		f2()
	case y > a:
		f3()
	}
	switch true {
	case y < a:
		y++
		f1()
	case y == a:
		f2()
	default:
		y--
		f3()
	}
	switch {
	case y < a:
		f1()
	case y > a:
		f2()
	}
	c := '?'
	switch c {
	case '?', '!', '.':
		f1()
		if y == 10 {
			f2()
		}
	}
	switch {
	case y >= 0 && y <= 10:
		f1()
	case y >= 11 && y <= 20:
		f2()
	}
	var lo, hi, limit int = 2, 5, 4
	for i := 0; i < limit && i < hi; i++ {
		switch {
		case y > i && y > lo:
			f1()
		case y < i:
			f2()
		}
	}
}
