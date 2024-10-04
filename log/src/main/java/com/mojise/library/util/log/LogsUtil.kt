package com.mojise.library.util.log

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonIOException
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.google.gson.stream.JsonWriter
import org.json.JSONArray
import org.json.JSONObject
import java.io.StringWriter

object LogsUtil {

    const val JSON_INDENT_WIDTH_DEFAULT = 2

    fun isValidJsonFormat(jsonString: String): Boolean {
        return try {
            when {
                jsonString.trim().startsWith("{") -> {
                    JSONObject(jsonString)  // 객체로 파싱을 시도
                    true
                }
                jsonString.trim().startsWith("[") -> {
                    JSONArray(jsonString)  // 배열로 파싱을 시도
                    true
                }
                else -> false
            }
        } catch (e: Exception) {
            false  // 파싱 오류 발생 시 유효하지 않은 JSON
        }
    }

    fun prettyJsonWithGsonIndentWidth(jsonString: String, indentWidth: Int): String {
        return try {
            // JSON 문자열을 파싱
            val jsonElement: JsonElement = JsonParser.parseString(jsonString)

            // StringWriter를 사용해 JSON 데이터를 담을 버퍼를 준비
            val stringWriter = StringWriter()

            // JsonWriter 생성, 커스텀 indent 설정
            val jsonWriter = JsonWriter(stringWriter)
            jsonWriter.setIndent(" ".repeat(indentWidth))  // indentWidth만큼의 공백 설정

            // Gson을 사용하여 jsonElement를 JsonWriter에 기록
            val gson = Gson()
            gson.toJson(jsonElement, jsonWriter)

            // StringWriter에 기록된 내용을 문자열로 반환
            stringWriter.toString()
        } catch (e: JsonSyntaxException) {
            // JSON 형식이 유효하지 않으면 원본 문자열 반환
            """
                ※ Invalid JSON Format (JsonSyntaxException: ${e.message}) ※
                $jsonString
            """.trimIndent()
        } catch (e: JsonIOException) {
            // I/O 오류 발생 시 원본 문자열 반환
            """
                ※ Invalid JSON Format (JsonIOException: ${e.message}) ※
                $jsonString
            """.trimIndent()
        } catch (e: Exception) {
            // 기타 오류 발생 시 원본 문자열 반환
            """
                ※ Invalid JSON Format (Exception: ${e.message}) ※
                $jsonString
            """.trimIndent()
        }
    }

    fun prettyJsonWithGson(jsonString: String): String {
        return try {
            // JSON 문자열을 파싱하고, 유효하지 않으면 예외를 던짐
            val jsonElement: JsonElement = JsonParser.parseString(jsonString)

            // Gson 인스턴스를 생성하고 prettyPrinting 활성화
            val gson = Gson().newBuilder().setPrettyPrinting().create()

            // Pretty format으로 JSON 문자열을 변환하여 반환
            gson.toJson(jsonElement)
        } catch (e: JsonSyntaxException) {
            // JSON 형식이 유효하지 않으면 원본 문자열 반환
            """
                ※ Invalid JSON Format (JsonSyntaxException: ${e.message}) ※
                $jsonString
            """.trimIndent()
        } catch (e: JsonIOException) {
            // I/O 오류 발생 시 원본 문자열 반환
            """
                ※ Invalid JSON Format (JsonIOException: ${e.message}) ※
                $jsonString
            """.trimIndent()
        } catch (e: Exception) {
            // 기타 오류 발생 시 원본 문자열 반환
            """
                ※ Invalid JSON Format (Exception: ${e.message}) ※
                $jsonString
            """.trimIndent()
        }
    }

    /**
     * No External Library, jsonString to Pretty JSON function.
     */
    fun prettyJsonNoExternalLibrary(jsonString: String): String {
        return try {
            val indentWidth = 4
            val stringBuilder = StringBuilder()
            var indentLevel = 0
            var inQuotes = false

            for (i in jsonString.indices) {
                when (val char = jsonString[i]) {
                    '"' -> {
                        stringBuilder.append(char)
                        if (jsonString.getOrNull(i - 1) != '\\') {
                            inQuotes = !inQuotes
                        }
                    }
                    '{', '[' -> {
                        stringBuilder.append(char)
                        if (!inQuotes) {
                            // 빈 객체/배열 처리
                            val nextChar = jsonString.getOrNull(i + 1)
                            if (nextChar != '}' && nextChar != ']') {
                                stringBuilder.append("\n")
                                indentLevel++
                                stringBuilder.append(" ".repeat(indentLevel * indentWidth))
                            }
                        }
                    }
                    '}', ']' -> {
                        if (!inQuotes) {
                            val prevChar = jsonString.getOrNull(i - 1)
                            if (prevChar != '{' && prevChar != '[') {
                                stringBuilder.append("\n")
                                indentLevel--
                                stringBuilder.append(" ".repeat(indentLevel * indentWidth))
                            }
                        }
                        stringBuilder.append(char)
                    }
                    ',' -> {
                        stringBuilder.append(char)
                        if (!inQuotes) {
                            stringBuilder.append("\n")
                            stringBuilder.append(" ".repeat(indentLevel * indentWidth))
                        }
                    }
                    else -> stringBuilder.append(char)
                }
            }
            stringBuilder.toString()
        } catch (e: Exception) {
            Log.w(Logs.TAG, "Failed to pretty print json string", e)
            jsonString // JSON 파싱 오류 시 원본 문자열을 반환
        }
    }
}