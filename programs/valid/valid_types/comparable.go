package main

func main() {
	// bool and bool
	var x1, x2 = true, false
	x := x1==x2
	
	// rune and rune
	var x3, x4 = 'a', 'b'
	x = x3!=x4
	
	// int and int
	var x5, x6 = 23, 0x17
	x = x5==x6
	
	// float64 and float64
	var x7, x8 = 0., 0.0
	x = x7!=x8
	
	// string and string
	var x9, x10 = "string", "string"
	x = x9==x10
	
	// struct and struct
	type t1 struct { v int; s string; }
	var x11, x12 t1
	x11.v, x12.v = 1, 1
	x = x11!=x12
	
	// array and array
	var arr1 [5]string
	var arr2 [5]string
	arr1[2], arr2[2] = "2", "2"
	x = arr1==arr2
	
	// aliased rune and aliased rune
	type r rune
	var x13, x14 r = r('a'), r('b')
	x = x13!=x14
	
	// aliased int and aliased int
	type integer int
	type num integer
	var x15, x16 num = num(4), num(5)
	x = x15==x16
	
	// aliased float64 and aliased float64
	type double float64
	var x17, x18 double = double(2.3), double(3.9)
	x = x17!=x18
	
	// aliased struct and aliased struct
	type t2 t1
	var x19, x20 t2
	x = x19==x20
	
	print(x)
}
