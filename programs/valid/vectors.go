package main

/* vectors and vector operations */

type vector3D struct {
	x, y, z float64
}

// returns absolute value of float
func abs(x float64) float64 {
	if x < 0. {
		return -x
	} else {
		return x
	}
}

// approximates square root of float
func sqrt(x float64) float64 {
	approx := x
	var last_approx float64
	tol := .000001
	for i := 0; i < 10; i++ {
		last_approx = approx
		approx -= (approx*approx - x) / (2. * approx)
		change := abs(approx - last_approx)
		if change <= tol {
			break
		}
	}
	return approx
}

// computes Euclidean norm of vector
func norm(v vector3D) float64 {
	result := sqrt(v.x*v.x + v.y*v.y + v.z*v.z)
	return result
}

// returns unit vector in direction of v
func normalize(v vector3D) vector3D {
	var w vector3D
	length := norm(v)
	w.x, w.y, w.z = v.x/length, v.y/length, v.z/length
	return w
}

// compute dot product of two vectors
func dotProduct(u, v vector3D) float64 {
	result := u.x*v.x + u.y*v.y + u.z*v.z
	return result
}

// compute cross product of two vectors
func crossProduct(u, v vector3D) vector3D {
	var w vector3D
	w.x = u.y*v.z - u.z*v.y
	w.y = u.z*v.x - u.x*v.z
	w.z = u.x*v.y - u.y*v.x
	return w
}

func printVector(v vector3D) {
	print("(", v.x, ",", v.y, ",", v.z, ")")
}

func printResult(u, v vector3D, c string) {
	switch c {
	case "norm":
		println("norm of u:", norm(u))
		print("norm of v: ", norm(v))
	case "normalize":
		var w1, w2 vector3D = normalize(u), normalize(v)
		println("normalized vectors of u and v:")
		printVector(w1)
		println("")
		printVector(w2)
	case "u dot v":
		print("dot product of u and v:\n", dotProduct(u, v))
	case "u cross v":
		var w vector3D = crossProduct(u, v)
		println("cross product of u and v: ")
		printVector(w)
	}
	println()
}

func main() {
	var (
		u, v                   vector3D
		c1, c2, c3, c4, choice string
	)
	u.x, u.y, u.z = 1., 0., 0.
	v.x, v.y, v.z = 0., 1., 0.
	print("u: ")
	printVector(u)
	println()
	print("v: ")
	printVector(v)
	println()
	c1, c2, c3, c4 = "norm", "normalize", "u dot v", "u cross v"
	choice = "all"
	switch choice {
	case c1:
		printResult(u, v, c1)
	case c2:
		printResult(u, v, c2)
	case c3:
		printResult(u, v, c3)
	case c4:
		printResult(u, v, c4)
	default:
		printResult(u, v, c1)
		printResult(u, v, c2)
		printResult(u, v, c3)
		printResult(u, v, c4)
	}
}
