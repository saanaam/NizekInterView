package com.sanam.nizekinterview.common.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.Editable
import java.net.HttpURLConnection
import java.net.URL
import java.util.regex.Pattern


private val persianNumbers =
    arrayOf("۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹")

private val englishNumbers =
    arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")

private var validPrefixPhoneNumbers = arrayOf(
    "0911",
    "0912", "0913", "0914", "0915", "0916", "0917", "0918",
    "0932", "0931", "0934", "0919", "0910", "0901", "0902",
    "0930", "0933", "0935", "0936", "0937", "0938", "0939",
    "0920", "0921", "0922", "09"
)
private var validPrefixPhoneNumbersHome = arrayOf(
    "086",
    "045", "044", "031", "061", "084", "058", "077",
    "076", "056", "041", "021", "066", "013", "054",
    "024", "011", "023", "087", "038", "071", "028",
    "025", "051", "081", "026", "034", "083", "017", "074", "035"
)


var SIZE_NATIONAL_ID = 10
var SIZE_PASSPORT_NUMBER = 9
var SIZE_POSTAL_CODE = 10
var SIZE_SHEBA = 24
const val regexString = "[a-zA-Z]+"
const val persianString = "^[\u0600-\u06FF\uFB8A\u067E\u0686\u06AF\u200C\u200F ]+\$"
const val PASSPORT_REG_EX = "[A-Z]{1}[0-9]{8}"

val String.english: String
    get() {
        if (isBlank()) return ""
        val chars = CharArray(this.length)
        for (i in this.indices) {
            var ch = this[i]
            if (ch.code >= 0x0660 && ch.code <= 0x0669)
                ch -= 0x0660 - '0'.code
            else if (ch.code >= 0x06f0 && ch.code <= 0x06F9)
                ch -= 0x06f0 - '0'.code
            chars[i] = ch
        }
        return String(chars)
    }


val String.plainNumber: String
    get() = replace("[^0-9۰-۹]+".toRegex(), "").trim()

val String.toEditable: Editable
    get() = Editable.Factory.getInstance().newEditable(this)

fun String.isPersianString(): Boolean {
    return this.matches(persianString.toRegex())
}

val String.currencyFormat: String
    get() {
        if (isBlank()) return ""
        return try {
            String.format("%,d", this.plainNumber.toLong())
        } catch (exception: NumberFormatException) {
            this.substring(0, this.length - 1)
        }
    }

fun String.getThPosition(position: Int): String? {
    val getTh: HashMap<Int, String> = HashMap()
    getTh[1] = "اول"
    getTh[2] = "دوم"
    getTh[3] = "سوم"
    getTh[4] = "چهارم"
    getTh[5] = "پنجم"
    getTh[6] = "ششم"
    getTh[7] = "هفتم"
    getTh[8] = "هشتم"
    getTh[9] = "نهم"
    getTh[10] = "دهم"
    return getTh[position]
}

val String.maskEndOfCard: String
    get() {
        return if (this.length > 4) {
            this.substring(this.length - 4)
        } else {
            this
        }
    }

val String.priceRialFormat: String
    get() {
        if (isBlank()) return ""
        return "${this.currencyFormat} ریال"
    }

val String.priceTomanFormat: String
    get() {
        if (isBlank()) return ""
        return "${this.currencyFormat} تومان"
    }

val String.priceEuroFormat: String
    get() {
        if (isBlank()) return ""
        return "${this.currencyFormat} یورو"
    }


val String.dateDayMonthFullYearFormat: String
    get() {
        val preparedString = plainNumber.english
        val result = StringBuilder()
        for (i in preparedString.indices) {
            if (i < 7 && needSeparator(i)) {
                result.append("/")
            }
            result.append(preparedString[i])
        }
        return result.toString()

    }


fun String.format(vararg args: String): String {
    return String.format(this, args)
}

fun String?.isNotNullOrEmpty(): Boolean {
    return !this.isNullOrEmpty()
}

fun String.hexStringToByteArray() =
    ByteArray(this.length / 2) { this.substring(it * 2, it * 2 + 2).toInt(16).toByte() }

fun ByteArray.toHexString() =
    joinToString("") { (0xFF and it.toInt()).toString(16).padStart(2, '0') }

private fun needSeparator(i: Int) = i % 2 == 0 && i != 0

val String.Companion.empty: String
    get() {
        return ""
    }

fun String.Companion.formatTwoDigit(value: Int): String {
    return String.format("%02d", value)
}

fun String?.containsEnglish(withNumber: Boolean = false): Boolean {
    val pattern = if (withNumber) {
        Pattern.compile(".*[a-zA-Z-1234567890]+.*")
    } else {
        Pattern.compile(".*[a-zA-Z]+.*")
    }
    return pattern.matcher(this!!).matches()
}

fun String.isPhoneNumberFromIranMobile(): Boolean {
    if (isBlank()) return false
    if (this.length == 11) {
        for ((index) in validPrefixPhoneNumbers.withIndex()) {
            if (this.startsWith(validPrefixPhoneNumbers[index]))
                return true
        }
    } else {
        return false
    }
    return false
}

fun String?.isEmailValid(): Boolean {
    if (this.isNullOrBlank()) return false
    var isValid = false

    val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
    val inputStr: CharSequence = this

    val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE) //

    val matcher = pattern.matcher(inputStr)
    if (matcher.matches()) {
        isValid = true
    }
    return isValid
}

fun String.isPhoneNumberFromIranHome(): Boolean {
    this.replace(" ", "").replace("+", "").replace("#", "")
    return if (this.length == 11) {
        for ((index) in validPrefixPhoneNumbersHome.withIndex()) {
            if (this.startsWith(
                    validPrefixPhoneNumbersHome[index]
                )
            ) return true
        }
        false
    } else if (this.length == 8) {
        return !this.startsWith("0")
    } else {
        return false
    }
}

fun String.isValidNationalID(): Boolean {
    if (this.isNullOrEmpty()) return false
    return this.length == SIZE_NATIONAL_ID
}

fun String.isValidPassPortNumber(): Boolean {
    if (this.isNullOrEmpty()) return false
    if (this.length == SIZE_PASSPORT_NUMBER) {
        if (this.matches(PASSPORT_REG_EX.toRegex())) {
            return true
        }
        return false
    }
    return false
}


fun String.isValidPostalCode(): Boolean {
    if (this.isNullOrEmpty()) return false
    return this.length == SIZE_POSTAL_CODE
}

fun String.isValidSheba(): Boolean {
    if (this.isNullOrEmpty()) return false
    return this.length == SIZE_SHEBA
}

fun getBitmapFromUrl(imageUrl: String): Bitmap {
    val url = URL(imageUrl)
    val connection =
        url.openConnection() as HttpURLConnection
    connection.doInput = true
    connection.connect()
    val input = connection.inputStream
    return BitmapFactory.decodeStream(input)
}

