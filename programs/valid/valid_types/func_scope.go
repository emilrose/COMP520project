package main

// func name and func body are different scopes
func f() {
	var f int = 2
	print(f)
}

// func name and func params are different scopes
func g(g int) {
	print(g)
}

func h() {
	f, g := 0, 0
	// At least one variable on the left-hand side is not declared in the current scope
	f, g, h := 1, 1, 1
	print(f, g, h)
    s := "str"
    s2 := `str`
    s3 := s + s2
    if (s==s2) {
        print(s3)
    }
}

func main() {
	f()
	g(1)
	h()
}
