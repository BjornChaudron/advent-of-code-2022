fun main() {
    fun part1(input: List<String>): Int {
        val treeGrid = toGrid(input)
        return countVisibleTrees(treeGrid)
    }

    fun part2(input: List<String>): Int {
        val treeGrid = toGrid(input)
        return findBestScenicScore(treeGrid)
    }

    // test if implementation meets criteria from the description, like:
    val day = "08"
    val testInput = readLines("Day${day}_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readLines("Day$day")
    println(part1(input))
    println(part2(input))
}


fun toGrid(lines: List<String>): Array<Array<Int>> {
    var treeGrid = arrayOf<Array<Int>>()
    for (line in lines) {
        val gridRow = line.toCharArray().map { it.digitToInt() }.toTypedArray()
        treeGrid += gridRow
    }
    return treeGrid
}

fun countVisibleTrees(treeGrid: Array<Array<Int>>): Int {
    var visibleTrees = 0
    for (y in treeGrid.indices) {
        for (x in treeGrid[y].indices) {
            val point = Point(x, y)

            if (isAtEdge(treeGrid, point) ||
                isVisibleFromNorth(treeGrid, point) ||
                isVisibleFromEast(treeGrid, point) ||
                isVisibleFromSouth(treeGrid, point) ||
                isVisibleFromWest(treeGrid, point)
            ) {
                visibleTrees++
            }
        }
    }
    return visibleTrees
}

fun isAtEdge(treeGrid: Array<Array<Int>>, point: Point) =
    point.x == 0 || point.x == treeGrid.first().lastIndex || point.y == 0 || point.y == treeGrid.lastIndex

fun isVisibleFromNorth(treeGrid: Array<Array<Int>>, point: Point) =
    getNorthTrees(treeGrid, point).all { it < treeGrid[point.y][point.x] }

fun getNorthTrees(treeGrid: Array<Array<Int>>, point: Point) =
    treeGrid.map { row -> row[point.x] }
        .filterIndexed { idx, _ -> idx < point.y }
        .reversed()

fun isVisibleFromEast(treeGrid: Array<Array<Int>>, point: Point) =
    getEastTrees(treeGrid, point).all { it < treeGrid[point.y][point.x] }

fun getEastTrees(treeGrid: Array<Array<Int>>, point: Point) =
    treeGrid[point.y].filterIndexed { idx, _ -> idx > point.x }
fun isVisibleFromSouth(treeGrid: Array<Array<Int>>, point: Point) =
    getSouthTrees(treeGrid, point).all { it < treeGrid[point.y][point.x] }

fun getSouthTrees(treeGrid: Array<Array<Int>>, point: Point) =
    treeGrid.map { row -> row[point.x] }
        .filterIndexed { idx, _ -> idx > point.y }

fun isVisibleFromWest(treeGrid: Array<Array<Int>>, point: Point) =
    getWestTrees(treeGrid, point).all { it < treeGrid[point.y][point.x] }

fun getWestTrees(treeGrid: Array<Array<Int>>, point: Point) =
    treeGrid[point.y].filterIndexed { idx, _ -> idx < point.x }
        .reversed()

fun findBestScenicScore(treeGrid: Array<Array<Int>>): Int {
    var maxScenicScore = 0

    for (y in treeGrid.indices) {
        for (x in treeGrid[y].indices) {
            val point = Point(x, y)
            if (isAtEdge(treeGrid, point)) continue

            val score = calcScenicScore(treeGrid, point)
            if (score > maxScenicScore) maxScenicScore = score
        }
    }
    return maxScenicScore
}

fun calcScenicScore(treeGrid: Array<Array<Int>>, point: Point): Int {
    val height = treeGrid[point.y][point.x]
    return listOf(
        getNorthTrees(treeGrid, point),
        getEastTrees(treeGrid, point),
        getSouthTrees(treeGrid, point),
        getWestTrees(treeGrid, point),
    ).map { calcScenicScoreForDirection(height, it) }
        .reduce { acc, i -> acc * i }
}

fun calcScenicScoreForDirection(height: Int, trees: List<Int>) =
    if (trees.all { height > it }) trees.size else trees.takeWhile { height > it }.size + 1