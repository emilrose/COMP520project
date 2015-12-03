// operator > not defined on array

package main

func main() {
	var a [2]string
	var b [2]string
	a[0] = "1"
	b[0] = "0"
	if a>b {
		print(a[0])
	}
}
