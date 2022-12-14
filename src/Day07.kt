
fun main() {
    class Directory(directoryName: String, parentDirectory: Directory?) {
        private val directoryName: String = directoryName
        private val parentDirectory: Directory? = parentDirectory
        private val innerDirectories = arrayListOf<Directory>()
        private val files = arrayListOf<Pair<String,Int>>()
        fun addDirectory(innerDirectoryName: String) {
            innerDirectories.add(Directory(innerDirectoryName, this))
        }
        fun addFile(fileName: String, fileSize: Int) {
            files.add(Pair(fileName, fileSize))
        }
        fun getDirectoryName() : String { return directoryName }
        fun getParentDirectory() : Directory? { return parentDirectory }
        fun getDirectory(directoryName: String): Directory? {
            return innerDirectories.find { dir ->
                dir.directoryName.equals(directoryName)
            }
        }
        fun getAllDirectories(): ArrayList<Directory> { return innerDirectories }
        fun getAllFiles(): ArrayList<Pair<String, Int>> { return files }
    }

    var rootDirectory: Directory = Directory("/", null)
    var currDirectory: Directory? = null

    fun changeDirectory(directoryName: String) {
        // Special case when "cd .."
        if (directoryName.equals("..")) {
            currDirectory = currDirectory?.getParentDirectory()
            return
        }
        val directory = currDirectory?.getDirectory(directoryName)
        directory?.let {
            currDirectory = directory
        } ?: run {
//            println("ERROR: Directory '${directoryName}' not found in '${currDirectory?.getDirectoryName()}', ignoring changing directory")
        }
    }

    fun addInnerDirectory(currDirectory: Directory?, innerDirectoryName: String) {
        currDirectory?.addDirectory(innerDirectoryName)
    }

    fun addFileToDirectory(currDirectory: Directory?, fileName: String, fileSize: Int) {
        currDirectory?.addFile(fileName, fileSize)
    }

    fun processCommands(input: List<String>) {
        input.forEach { command ->
            val arguments = command.split(" ")
            when (arguments[0]) {
                "$" -> {
                    when (arguments[1]) {
                        "cd" -> {
                            changeDirectory(arguments[2])       //NOTE: $ cd directoryName
                        }
                        "ls" -> {
                            // List command -> Any "dir" or fileSize as arguments[0] should be added to the current directory
                        }
                    }
                }
                "dir" -> {
                    currDirectory?.addDirectory(arguments[1])
                }
                else -> {
                    arguments[0].toIntOrNull()?.let { fileSize ->
                        currDirectory?.addFile(arguments[1], fileSize)       // arguments[1] contains the fileName
                    } ?: run {
                        println("ERROR: ${arguments[0]} could not be converted to Int! | command = ${command}")
                    }
                }
            }
        }
    }

    var totalSizeOfSelectedDirectories = 0
    fun getDirectorySizePart1(directory: Directory?): Int {
        directory?.let {
            val totalFileSize = directory.getAllFiles().sumOf { file -> file.second }
            val totalDirectoriesSize = directory.getAllDirectories().sumOf { innerDirectory -> getDirectorySizePart1(innerDirectory) }
            val totalSize = totalFileSize + totalDirectoriesSize
            if (totalSize <= 100000) {
                totalSizeOfSelectedDirectories += totalSize
            }
            return totalSize
        }
        return 0
    }

    val directorySizes = arrayListOf<Pair<String,Int>>()
    fun getDirectorySizePart2(directory: Directory?): Int {
        directory?.let {
            val totalFileSize = directory.getAllFiles().sumOf { file -> file.second }
            val totalDirectoriesSize = directory.getAllDirectories().sumOf { innerDirectory -> getDirectorySizePart2(innerDirectory) }
            val totalSize = totalFileSize + totalDirectoriesSize
            directorySizes.add(Pair(directory.getDirectoryName(), totalSize))
            return totalSize
        }
        return 0
    }

    fun part1(input: List<String>): Int {
        rootDirectory = Directory("/", null)
        currDirectory = rootDirectory
        processCommands(input)
        getDirectorySizePart1(rootDirectory)
        return totalSizeOfSelectedDirectories
    }

    val TOTAL_FILESYSTEM_SPACE = 70000000
    val REQUIRED_UNUSED_SPACE = 30000000

    fun part2(input: List<String>): Int {
        rootDirectory = Directory("/", null)
        currDirectory = rootDirectory
        processCommands(input)
        var directorySizeToBeRemoved = 0
        val currentFreeSpace = TOTAL_FILESYSTEM_SPACE - getDirectorySizePart2(rootDirectory)
        val sortedDirectorySizes = directorySizes.sortedWith(compareBy { it.second })
        for (directorySize in sortedDirectorySizes) {
            var unusedSpaceAcquired = directorySize.second + currentFreeSpace
            if (unusedSpaceAcquired > REQUIRED_UNUSED_SPACE) {
//                println("DirectoryName = ${directorySize.first} | Size Removed = ${directorySize.second} | Unused Space Acquired = ${unusedSpaceAcquired}")
                directorySizeToBeRemoved = directorySize.second
                break
            }
        }
        return directorySizeToBeRemoved
    }

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}