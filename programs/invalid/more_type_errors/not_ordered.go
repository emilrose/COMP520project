// bools are not ordered

package main

func main() {
	t, f := true, false
	if t > f {
		print(f)
	}
}
