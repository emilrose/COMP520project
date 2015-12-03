package main

// return an index such that all entries to the left
// are smaller and all entries to the right are larger
// than the entry at the index
func partition(a []int, start, stop int) int {
	pivot := a[stop]
	left := start
	right := stop - 1
	for left <= right {
		for (left <= right) && (a[left] <= pivot) {
			left++
		} 
		for (left <= right) && (a[right] >= pivot) {
			right--
		}
		if (left < right) {
			temp := a[left]
			a[left] = a[right]
			a[right] = temp
		}
	}
	temp := a[left]
	a[left] = a[stop]
	a[stop] = temp
	return left
}

func quicksort(a []int, start int, stop int) []int {
	if start >= stop {
		return a
	}
	index := partition(a, start, stop)
	quicksort(a, start, index-1)
	quicksort(a, index+1, stop)
	return a
}

// print array
func printArray(a []int, length int) {
	for i := 0; i < length; i++ {
		print(a[i], " ")
	}
	println()
}

func main() {
	var a []int
	var length int = 9
	a = append(a, 3)
	a = append(a, 4)
	a = append(a, 1)
	a = append(a, 5)
	a = append(a, 7)
	a = append(a, 8)
	a = append(a, 6)
	a = append(a, 2)
	a = append(a, 0)
	print("original array: ")
	printArray(a, length)
	b := quicksort(a, 0, 8)
	print("sorted array: ")
	printArray(b, length)
}
