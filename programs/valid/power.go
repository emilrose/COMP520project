package main

func power(base, exponent int) int {
	if exponent < 0 {
		print("only computes non-negative powers")
		return -1
	}
	count, result := 1, 1
	for count <= exponent {
		result *= base
		count += 1
	}
	return result
}

func main() {
	base := 2
	exponent := 5
	k := power(base, exponent)
	println(base, "^", exponent, "=", k)
}
