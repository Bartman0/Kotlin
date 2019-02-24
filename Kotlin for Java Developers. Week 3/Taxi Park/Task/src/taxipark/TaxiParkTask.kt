package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
        allDrivers.minus(trips.filter { it.distance > 0 }.map { it.driver })

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
        if (minTrips == 0) allPassengers
        else trips.flatMap { it.passengers }.groupBy { it }.filter { it.value.size >= minTrips }.map { it.key }.toSet()

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
        trips.filter { it.driver == driver }.flatMap { it.passengers }.groupBy { it }.filter { it.value.size > 1 }.map { it.key }.toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> {
    val (with, without) = trips.partition { (it.discount ?: 0.0) > 0.0 }
    val passengerTripsCountWith = with.flatMap { it.passengers }.groupingBy { it }.eachCount().toMap()
    val passengerTripsCountWithout = without.flatMap { it.passengers }.groupingBy { it }.eachCount().toMap()
    return passengerTripsCountWith.map { (passenger, cnt) -> passenger to cnt - passengerTripsCountWithout.getOrDefault(passenger, 0) }.filter { it.second > 0 }.map { it.first }.toSet()
}

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    return trips.map { t -> IntRange(t.duration - (t.duration % 10), t.duration - (t.duration % 10) + 9) }.groupBy { it }.maxBy { it.value.size }?.key
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if (trips.isEmpty()) return false
    var numDrivers = 0
    var sumCost = 0.0
    val totalCost = trips.map { it.cost }.sum()
    val mapDriverCost = trips.map { it.driver to it.cost }.groupingBy { it.first }.aggregate { _, accumulator: Double?, element, first -> if (first) element.second else (accumulator?:0.0) + element.second }.map { it.key to it.value }.sortedByDescending { it.second }.map { it.second }
    for (c in mapDriverCost) {
        sumCost += c!!
        numDrivers++
        if (sumCost >= 0.8 * totalCost) break
    }
    return numDrivers <= 0.2*allDrivers.size
}
