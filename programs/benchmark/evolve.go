package main

/*
$ go test evolve_test.go -bench=.
testing: warning: no tests to run
PASS
BenchmarkEvolve	+5.405966e+000 +7.247739e+001
1	43906884406 ns/op
ok  	command-line-arguments	43.974s
*/

func sqrt(x float64) float64 {
	r := x
	for i := 0; i < 10; i++ {
		r = r - (r*r-x)/(2.0*r)
	}
	return r
}

// solves system of ODEs

func evolve(ns int, dt float64) {

	var x, y, g [500]float64
	var x_new, y_new [500]float64

	for i := 0; i < 500; i++ {
		x[i] = float64(i)
		x_new[i] = 0.0
		y_new[i] = 0.0
		if i%2 == 0 {
			y[i] = 0.0
		} else {
			y[i] = 0.5
		}
		if i < 250 {
			g[i] = -1.0
		} else {
			g[i] = 1.0
		}
	}

	for t := 0; t < ns; t++ {

		for i := 0; i < 500; i++ {
			vx := 0.0
			vy := 0.0

			for j := 0; j < 500; j++ {
				if i != j {
					x_diff := x[i] - x[j]
					y_diff := y[i] - y[j]
					dij := sqrt(x_diff*x_diff + y_diff*y_diff)
					vx = (vx + g[j]*y_diff) / dij
					vy = (vy - g[j]*x_diff) / dij
				}
			}

			x_new[i] = x[i] + vx*dt
			y_new[i] = y[i] + vy*dt

		}

		for i := 0; i < 500; i++ {
			x[i] = x_new[i]
			y[i] = y_new[i]
		}

        if t==(ns-1) {
            println(x[0], y[0]);
        }

	}

}

func main() {
	// number of time steps
	ns := 1500
	// step size
	dt := 0.05
	evolve(ns, dt)
}

