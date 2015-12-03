package main

type bin int
type dec int

// computes base^exponent
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

// converts binary representation to decimal
func bin2dec(b bin) dec {
	var a []int
	n := 0
	b2 := b
	// find number of digits
	for b2 > bin(0) {
		b2 /= bin(10)
		n++
	}
	// extract each digit
	n2 := n
	for i := 0; i < n; i++ {
		sum_prev := 0
		for j := 0; j < i; j++ {
			sum_prev += a[j] * power(10, i-j)
		}
		a = append(a, int(b)/power(10, n2-1)-sum_prev)
		n2--
	}
	// multiply digits by appropriate powers of 2 and sum them up
	sum := 0
	for i := 0; i < n; i++ {
		sum += a[i] * power(2, n-i-1)
	}
	return dec(sum)
}

func main() {
	var b bin = bin(1011101)
	println("the decimal representation of the binary number", b, "is", bin2dec(b))
}
