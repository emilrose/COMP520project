package main

/*
$ go test dice_test.go -bench=.
testing: warning: no tests to run
PASS
BenchmarkDice	108104
1	45072091882 ns/op
ok  	command-line-arguments	45.150s
*/


// if 3 n-sided dice are tossed, how many of the possible outcomes contain a subset that sums to s?

func count(n, s int) int {
	count := 0
	for i := 1; i <= n; i++ {
		for j := 1; j <= n; j++ {
			for k := 1; k <= n; k++ {
				subset1 := (i == s) || (j == s) || (k == s)
				subset2 := ((i + j) == s) || ((i + k) == s) || ((j + k) == s)
				subset3 := ((i + j + k) == s)
				if subset1 || subset2 || subset3 {
					count++
				}

			}

		}

	}
	return count
}

func main() {
	c := 0
	for i := 1; i <= 130; i++ {
		for j := 1; j <= 130; j++ {
			c = count(i, j)
		}
	}
	println(c)
}


