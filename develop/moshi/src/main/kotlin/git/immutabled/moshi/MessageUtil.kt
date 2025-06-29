package git.immutabled.moshi

import git.immutabled.moshi.font.DefaultFont

object MessageUtil {

    @JvmStatic
    val CENTER_PX = 154

    fun center(message: String, px: Int = CENTER_PX): String {
        val strippedMessage = stripFormatting(message)
        var messagePixels = 0
        var bold = false

        for (char in strippedMessage.toCharArray()) {
            val font = DefaultFont.getDefaultFontInfo(char)
            messagePixels += if (bold) font.getBoldLength() else font.length
            messagePixels++
        }

        val halvedMessageSize = messagePixels / 2
        val toCompensate = px - halvedMessageSize
        val spaceLength = DefaultFont.SPACE.length + 1

        val stringBuilder = StringBuilder()
        for (i in 0..toCompensate step spaceLength) {
            stringBuilder.append(" ")
        }

        return "$stringBuilder$message"
    }

    private fun stripFormatting(text: String): String {
        return text
            .replace(Regex("&[0-9a-fk-orA-FK-OR]"), "")
            .replace(Regex("§[0-9a-fk-orA-FK-OR]"), "")
            .replace(Regex("<[^>]*>"), "")
    }
}