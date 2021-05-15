package demo.mountainForm.service.serviceimpl

import demo.mountainForm.service.MountainDTO
import demo.mountainForm.service.MountainService
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.net.URISyntaxException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.ArrayList
import java.util.function.Consumer
import java.util.function.Function
import java.util.function.Predicate
import java.util.function.Supplier

class MountainServiceImpl() : MountainService {
        val FILE_NAME = "./src/main/resources/mountains.csv"
        val DELIMITER = ";"
        val NUMBER_OF_COLUMNS = 12
        val ID_COLUMN = 0

        override fun get(id: Long): MountainDTO {
            val idAsString = java.lang.Long.toString(id)
            try {
                getReader(FILE_NAME).use { reader ->
                    val x = reader.lines()
                        .skip(1)
                        .map<List<String>>(Function<String, List<String>> {
                                line: String -> splitLine(line)
                        })
                        .filter(Predicate { data: List<String> -> data[ID_COLUMN] == idAsString })

                    println(x.toString())

                    val y = x.map<Any>(Function<List<String>, Any> { MountainDTO(it) })

                    println(y.toString())
                        return y
                            .findAny()
                            .orElseThrow<NoSuchElementException>(Supplier { NoSuchElementException() }) as MountainDTO

                }
            } catch (e: IOException) {
                e.printStackTrace()
                throw IllegalStateException("Service failed")
            }
        }

    override fun save(dto: MountainDTO) {
            val idStr = java.lang.Long.toString(dto.getId())
            val linesToSave: MutableList<String> = ArrayList()
            try {
                getReader(FILE_NAME).use { reader ->
                    reader.lines()
                            .forEach(Consumer { line: String ->
                                val data: List<String> = splitLine(line)
                                if (data[ID_COLUMN] == idStr) {
                                    linesToSave.add(dto.toLine(DELIMITER))
                                } else {
                                    linesToSave.add(line)
                                }
                            })
                }
            } catch (e: IOException) {
                throw IllegalStateException("reload failed")
            }
            try {
                Files.write(getPath(FILE_NAME), linesToSave)
            } catch (e: IOException) {
                throw IllegalStateException("save failed")
            }
        }

        override fun getTotalCount(): Long {
            try {
                getReader(FILE_NAME).use { reader ->
                    return reader.lines()
                            .skip(1)
                            .count()
                }
            } catch (e: IOException) {
                throw IllegalStateException("failed")
            }
        }

        open fun getReader(fileName: String): BufferedReader {
//            return BufferedReader(InputStreamReader(javaClass.getResourceAsStream(fileName), StandardCharsets.UTF_8))
            return File(fileName).bufferedReader()
        }


        open fun splitLine(line: String): List<String> {
            val x = line.split(DELIMITER)
            println(x)
            return x
        }

        open fun getPath(fileName: String): Path? {
            return try {
                Paths.get(javaClass.getResource(fileName).toURI())
            } catch (e: URISyntaxException) {
                throw IllegalArgumentException(e)
            }
        }
}