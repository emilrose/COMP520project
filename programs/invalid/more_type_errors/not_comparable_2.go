// slice values are not comparable

package main

func main() {
	var a,b []int
	a = append(a,0)
	b = append(b,0)
	if a!=b {
		print(a[0])
	}
}
