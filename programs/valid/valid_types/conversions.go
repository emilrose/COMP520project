package main

type km float64
type mi float64

type celsius int
type fahrenheit int

func km2mi(x km) mi {
	return mi(x*km(0.621371))
}

func mi2km(x mi) km {
	return km(x/mi(0.621371))
}

func cel2fah(x celsius) fahrenheit {
	var float_result float64 = float64(x)*1.8+32.0
	return fahrenheit(float_result)
}

func fah2cel(x fahrenheit) celsius {
	var float_result float64 = (float64(x)-32.0)/1.8
	return celsius(float_result)
}

func main() {
	var distance_in_km km = km(50)
	var distance_in_mi mi = km2mi(distance_in_km)
	println(mi2km(distance_in_mi), "km equals", distance_in_mi, "mi")
	var temp_in_cel celsius = celsius(20)
	var temp_in_fah fahrenheit = cel2fah(temp_in_cel)
	println(fah2cel(temp_in_fah), "degrees celsius equals", temp_in_fah, "degrees fahrenheit")
}